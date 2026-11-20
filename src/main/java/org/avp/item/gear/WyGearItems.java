package org.avp.item.gear;

import java.util.List;

import org.avp.entity.projectile.LaserMineEntity;
import org.avp.entity.projectile.SupplyDropEntity;
import org.avp.item.ModelledItem;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

/** Batch F4 items: stun baton, laser mine, supply chute. */
public final class WyGearItems
{
    /** W-Y stun baton (1.12.2 ItemStunBaton): a jolt that slows and weakens - good for "bringing one back alive". */
    public static class StunBaton extends Item implements ModelledItem
    {
        public StunBaton(Properties properties)
        {
            super(properties.stacksTo(1).durability(250));
        }

        @Override
        public String model()
        {
            return "StunBatonModel";
        }

        @Override
        public String texture()
        {
            return "stunbaton.png";
        }

        @Override
        public boolean twoHanded()
        {
            return false;
        }

        @Override
        public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
        {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 2), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160), attacker);
            target.hurt(target.damageSources().source(org.avp.registry.ModDamageTypes.ELECTRICITY, attacker), 3.0F);
            if (target.level() instanceof ServerLevel level)
            {
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY() + target.getBbHeight() * 0.6, target.getZ(),
                        12, 0.3, 0.4, 0.3, 0.1);
                level.playSound(null, target.blockPosition(), org.avp.registry.ModGear.STUN_BATON_HIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            return true;
        }

        @Override
        public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
        {
            stack.hurtAndBreak(1, attacker, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
        }
    }

    /** A Yautja laser mine: use it on a wall. */
    public static class LaserMine extends Item
    {
        public LaserMine(Properties properties)
        {
            super(properties.stacksTo(16));
        }

        @Override
        public InteractionResult useOn(UseOnContext context)
        {
            Level level = context.getLevel();
            if (!level.isClientSide)
            {
                level.addFreshEntity(LaserMineEntity.place(level, context.getClickedPos(), context.getClickedFace(), context.getPlayer()));
                level.playSound(null, context.getClickedPos(), SoundEvents.TRIPWIRE_ATTACH, SoundSource.PLAYERS, 1.0F, 1.2F);
                if (context.getPlayer() == null || !context.getPlayer().getAbilities().instabuild)
                {
                    context.getItemInHand().shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
        {
            tooltip.add(Component.translatable("tooltip.avp.mine_laser").withStyle(ChatFormatting.GRAY));
        }
    }

    /** A supply chute beacon: calls a supply drop down on your position (it needs open sky). */
    public static class SupplyChute extends Item
    {
        private final int kind;

        public SupplyChute(int kind, Properties properties)
        {
            super(properties.stacksTo(4));
            this.kind = kind;
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
        {
            ItemStack stack = player.getItemInHand(hand);
            if (level instanceof ServerLevel server)
            {
                BlockPos top = server.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, player.blockPosition());
                if (top.getY() > player.getY() + 2)
                {
                    player.displayClientMessage(Component.translatable("message.avp.supply_no_sky").withStyle(ChatFormatting.RED), true);
                    return InteractionResultHolder.fail(stack);
                }
                SupplyDropEntity.drop(server, player.getX(), player.getZ(), kind, Math.min(server.getMaxBuildHeight() - 2, (int) player.getY() + 60));
                player.displayClientMessage(Component.translatable("message.avp.supply_inbound").withStyle(ChatFormatting.GREEN), true);
                server.playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.PLAYERS, 1.0F, 1.5F);
            }
            if (!player.getAbilities().instabuild)
            {
                stack.shrink(1);
            }
            player.getCooldowns().addCooldown(this, 100);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
    }

    private WyGearItems()
    {
    }
}
