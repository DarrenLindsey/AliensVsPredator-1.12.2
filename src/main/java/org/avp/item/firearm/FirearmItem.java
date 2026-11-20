package org.avp.item.firearm;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.avp.block.NaturalResinBlock;
import org.avp.block.entity.ResinBlockEntity;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModDataComponents;
import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * A firearm (1.12.2 ItemFirearm). Hold use to fire - automatic weapons keep firing at their rate
 * of fire; sneak-use to reload (it also reloads by itself when the magazine runs dry, if you carry
 * a magazine of the right kind). Rounds are hitscan, kick the view up, shatter glass, tear hive
 * resin off the walls, and don't lose damage to the target's hurt-invulnerability (an automatic
 * rifle would otherwise waste 2 rounds in 3). The M56 Smartgun steers its rounds onto threats near
 * the crosshair, as in Aliens.
 */
public class FirearmItem extends Item
{
    private final Firearm firearm;
    /** Last time each shooter's firing sound started (fast guns play it once per burst). */
    private static final Map<LivingEntity, Long> LAST_SOUND = new WeakHashMap<>();

    public FirearmItem(Firearm firearm, Properties properties)
    {
        super(properties.stacksTo(1));
        this.firearm = firearm;
    }

    public Firearm firearm()
    {
        return firearm;
    }

    public static int ammo(ItemStack stack)
    {
        return stack.getOrDefault(ModDataComponents.AMMO.get(), 0);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return 72_000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && ammo(stack) < firearm.magazine())
        {
            return reload(level, player, stack) ? InteractionResultHolder.consume(stack) : InteractionResultHolder.fail(stack);
        }
        if (ammo(stack) <= 0 && !player.getAbilities().instabuild)
        {
            if (!reload(level, player, stack))
            {
                level.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 0.6F, 1.6F);
                return InteractionResultHolder.fail(stack);
            }
            return InteractionResultHolder.consume(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    /** Swaps in a full magazine of the right kind from the inventory (creative: free). */
    public boolean reload(Level level, Player player, ItemStack stack)
    {
        if (player.getCooldowns().isOnCooldown(this))
        {
            return false;
        }
        if (!player.getAbilities().instabuild)
        {
            ItemStack mag = findMagazine(player);
            if (mag.isEmpty())
            {
                if (!level.isClientSide)
                {
                    player.displayClientMessage(Component.translatable("message.avp.no_ammo", Component.translatable("item.avp." + firearm.ammo().magazine))
                            .withStyle(ChatFormatting.RED), true);
                }
                return false;
            }
            mag.shrink(1);
        }
        stack.set(ModDataComponents.AMMO.get(), firearm.magazine());
        player.getCooldowns().addCooldown(this, firearm.reloadTicks());
        player.stopUsingItem();
        level.playSound(null, player.blockPosition(), ModSounds.GUN_RELOAD.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return true;
    }

    private ItemStack findMagazine(Player player)
    {
        for (ItemStack s : player.getInventory().items)
        {
            if (s.getItem() instanceof AmmoItem ammo && ammo.type() == firearm.ammo())
            {
                return s;
            }
        }
        for (ItemStack s : player.getInventory().offhand)
        {
            if (s.getItem() instanceof AmmoItem ammo && ammo.type() == firearm.ammo())
            {
                return s;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onUseTick(Level level, LivingEntity shooter, ItemStack stack, int remaining)
    {
        int held = getUseDuration(stack, shooter) - remaining;
        int rounds = firearm.roundsBy(held) - (held == 0 ? 0 : firearm.roundsBy(held - 1));
        boolean free = shooter instanceof Player p && p.getAbilities().instabuild;
        for (int i = 0; i < rounds; i++)
        {
            if (ammo(stack) <= 0 && !free)
            {
                shooter.releaseUsingItem();
                if (shooter instanceof Player player)
                {
                    reload(level, player, stack); // reloads by itself when it runs dry
                }
                return;
            }
            if (level.isClientSide)
            {
                kick(shooter);
            }
            else
            {
                fire((ServerLevel) level, shooter);
                if (!free)
                {
                    stack.set(ModDataComponents.AMMO.get(), ammo(stack) - 1);
                }
            }
        }
    }

    /** Recoil: the view kicks up (and a little sideways). Client side, for the player holding the gun. */
    private void kick(LivingEntity shooter)
    {
        shooter.setXRot(shooter.getXRot() - firearm.recoil() * 1.2F);
        shooter.setYRot(shooter.getYRot() + (shooter.getRandom().nextFloat() - 0.5F) * firearm.recoil() * 0.6F);
    }

    /** One round: hitscan from the shooter's eyes. */
    public void fire(ServerLevel level, LivingEntity shooter)
    {
        Vec3 eye = shooter.getEyePosition();
        Vec3 dir = shooter.getViewVector(1F);
        float spread = firearm.spread() * 0.01F;
        dir = dir.add(shooter.getRandom().triangle(0, spread), shooter.getRandom().triangle(0, spread), shooter.getRandom().triangle(0, spread)).normalize();
        if (firearm == Firearms.M56SG)
        {
            dir = smartAim(level, shooter, eye, dir);
        }
        Vec3 end = eye.add(dir.scale(firearm.range()));
        BlockHitResult block = level.clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, shooter));
        if (block.getType() != HitResult.Type.MISS)
        {
            end = block.getLocation();
        }
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(level, shooter, eye, end, new AABB(eye, end).inflate(1.0),
                e -> e.isPickable() && !e.isSpectator() && e != shooter.getVehicle() && !e.isPassengerOfSameVehicle(shooter), 0.1F);
        sound(level, shooter);
        Vec3 impact;
        if (hit != null)
        {
            impact = hit.getLocation();
            Entity target = hit.getEntity();
            target.invulnerableTime = 0;
            target.hurt(level.damageSources().source(ModDamageTypes.BULLET, shooter, shooter), firearm.damage());
        }
        else
        {
            impact = end;
            if (block.getType() == HitResult.Type.BLOCK)
            {
                hitBlock(level, block.getBlockPos(), impact);
            }
        }
        // a faint tracer, and the muzzle flash
        Vec3 path = impact.subtract(eye);
        double length = path.length();
        for (double d = 1.5; d < length; d += 3.0)
        {
            Vec3 p = eye.add(path.scale(d / length));
            level.sendParticles(ParticleTypes.CRIT, p.x, p.y - 0.1, p.z, 1, 0, 0, 0, 0);
        }
        Vec3 muzzle = eye.add(dir.scale(1.0)).add(0, -0.25, 0);
        level.sendParticles(ParticleTypes.SMALL_FLAME, muzzle.x, muzzle.y, muzzle.z, 1, 0.02, 0.02, 0.02, 0.0);
    }

    /** The Smartgun: if a threat is close to where it's pointed (8 degrees), the round goes to it. */
    private Vec3 smartAim(ServerLevel level, LivingEntity shooter, Vec3 eye, Vec3 dir)
    {
        LivingEntity best = null;
        double bestAngle = Math.toRadians(8);
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, shooter.getBoundingBox().inflate(firearm.range()),
                e -> e != shooter && e.isAlive() && (e instanceof Enemy || e.getType().is(ModTags.XENOMORPHS))))
        {
            Vec3 to = e.getBoundingBox().getCenter().subtract(eye);
            double angle = Math.acos(Mth.clamp(to.normalize().dot(dir), -1, 1));
            if (angle < bestAngle && shooter.hasLineOfSight(e))
            {
                best = e;
                bestAngle = angle;
            }
        }
        return best == null ? dir : best.getBoundingBox().getCenter().subtract(eye).normalize();
    }

    private void sound(ServerLevel level, LivingEntity shooter)
    {
        long now = level.getGameTime();
        Long last = LAST_SOUND.get(shooter);
        if (firearm.soundLength() > 0 && last != null && now - last < firearm.soundLength() * 20)
        {
            return;
        }
        LAST_SOUND.put(shooter, now);
        level.playSound(null, shooter.getX(), shooter.getEyeY(), shooter.getZ(), firearm.sound().get(), SoundSource.PLAYERS,
                1.0F, 0.95F + shooter.getRandom().nextFloat() * 0.1F);
    }

    /** Rounds shatter glass and tear resin off the hive walls; anything else just sparks. */
    private void hitBlock(ServerLevel level, BlockPos pos, Vec3 at)
    {
        BlockState state = level.getBlockState(pos);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), at.x, at.y, at.z, 4, 0.05, 0.05, 0.05, 0.1);
        if (state.is(ModTags.XENO_BREAKABLE))
        {
            level.destroyBlock(pos, false);
        }
        else if (state.getBlock() instanceof NaturalResinBlock && level.random.nextInt(4) == 0)
        {
            BlockState under = level.getBlockEntity(pos) instanceof ResinBlockEntity resin ? resin.covered() : null;
            level.levelEvent(2001, pos, net.minecraft.world.level.block.Block.getId(state));
            level.setBlock(pos, under != null ? under : net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack)
    {
        return true;
    }

    /** The bar under the item shows the rounds left. */
    @Override
    public int getBarWidth(ItemStack stack)
    {
        return Math.round(13F * ammo(stack) / firearm.magazine());
    }

    @Override
    public int getBarColor(ItemStack stack)
    {
        return 0xE0C040;
    }

    /** The ammo count changes every round: don't play the "put away and take out" bob each time. */
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        return true; // no arm swing when left-clicking with a gun
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.translatable("tooltip.avp.firearm.ammo", ammo(stack), firearm.magazine()).withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable("tooltip.avp.firearm.stats", firearm.damage(), (int) firearm.roundsPerMinute(),
                Component.translatable("item.avp." + firearm.ammo().magazine)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.avp.firearm.controls").withStyle(ChatFormatting.DARK_GRAY));
    }
}
