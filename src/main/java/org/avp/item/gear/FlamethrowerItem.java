package org.avp.item.gear;

import java.util.List;

import org.avp.block.NaturalResinBlock;
import org.avp.block.entity.ResinBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.item.ModelledItem;
import org.avp.registry.ModGear;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Flamethrowers (1.12.2 ItemFlamethrower: the M240 incinerator unit and the Sevastopol one from
 * Alien: Isolation). Hold use to spray a cone of fire 8 blocks out: it sets creatures alight,
 * burns hive resin off the walls and (config) starts fires. Runs on propane tanks.
 */
public class FlamethrowerItem extends Item implements ModelledItem
{
    private final String model, texture;
    private final double range;

    public FlamethrowerItem(String model, String texture, double range, Properties properties)
    {
        super(properties.stacksTo(1));
        this.model = model;
        this.texture = texture;
        this.range = range;
    }

    @Override
    public String model()
    {
        return model;
    }

    @Override
    public String texture()
    {
        return texture;
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
        if (!player.getAbilities().instabuild && fuel(player).isEmpty())
        {
            if (!level.isClientSide)
            {
                player.displayClientMessage(Component.translatable("message.avp.no_fuel").withStyle(ChatFormatting.RED), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    private static ItemStack fuel(Player player)
    {
        for (ItemStack s : player.getInventory().items)
        {
            if (s.is(ModGear.PROPANE_TANK.get()))
            {
                return s;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remaining)
    {
        if (user.isUnderWater())
        {
            user.releaseUsingItem();
            return;
        }
        if (!(level instanceof ServerLevel server))
        {
            return;
        }
        if (user instanceof Player player && !player.getAbilities().instabuild)
        {
            ItemStack tank = fuel(player);
            if (tank.isEmpty())
            {
                player.releaseUsingItem();
                return;
            }
            tank.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
        }
        int held = getUseDuration(stack, user) - remaining;
        if (held % 16 == 0)
        {
            server.playSound(null, user.blockPosition(), ModGear.FLAMETHROWER_SOUND.get(), SoundSource.PLAYERS, 0.8F, 1.0F);
        }
        Vec3 eye = user.getEyePosition();
        Vec3 dir = user.getViewVector(1F);
        // the jet of fire
        for (int i = 0; i < 6; i++)
        {
            double d = 1.0 + user.getRandom().nextDouble() * range;
            Vec3 p = eye.add(dir.scale(d)).add(user.getRandom().triangle(0, d * 0.08), user.getRandom().triangle(0, d * 0.08) - 0.2,
                    user.getRandom().triangle(0, d * 0.08));
            server.sendParticles(i % 3 == 0 ? ParticleTypes.LARGE_SMOKE : ParticleTypes.FLAME, p.x, p.y, p.z, 1, 0.05, 0.05, 0.05, 0.02);
        }
        // creatures in the cone (15 degrees)
        for (LivingEntity e : server.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(range), e -> e != user && e.isAlive()))
        {
            Vec3 to = e.getBoundingBox().getCenter().subtract(eye);
            if (to.length() <= range && Math.acos(Mth.clamp(to.normalize().dot(dir), -1, 1)) < Math.toRadians(15) && user.hasLineOfSight(e))
            {
                e.igniteForSeconds(8);
                if (held % 4 == 0)
                {
                    e.hurt(server.damageSources().source(DamageTypes.IN_FIRE, user, user), 3.0F);
                }
            }
        }
        // where it splashes: burn resin off, start fires
        BlockHitResult hit = level.clip(new ClipContext(eye, eye.add(dir.scale(range)), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, user));
        if (hit.getType() == HitResult.Type.BLOCK && held % 3 == 0)
        {
            burn(server, hit.getBlockPos(), hit.getDirection());
        }
    }

    private static void burn(ServerLevel level, BlockPos pos, Direction face)
    {
        for (BlockPos p : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)))
        {
            BlockState s = level.getBlockState(p);
            if (s.getBlock() instanceof NaturalResinBlock && level.random.nextInt(3) == 0)
            {
                BlockState under = level.getBlockEntity(p) instanceof ResinBlockEntity resin ? resin.covered() : null;
                level.setBlock(p, under != null ? under : net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
                level.sendParticles(ParticleTypes.LARGE_SMOKE, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, 3, 0.3, 0.3, 0.3, 0.02);
            }
        }
        BlockPos fire = pos.relative(face);
        if (AvpConfig.server(AvpConfig.SERVER.flamethrowerIgnites) && level.getBlockState(fire).isAir() && BaseFireBlock.canBePlacedAt(level, fire, face))
        {
            level.setBlock(fire, BaseFireBlock.getState(level, fire), 11);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.translatable("tooltip.avp.flamethrower").withStyle(ChatFormatting.GRAY));
    }
}
