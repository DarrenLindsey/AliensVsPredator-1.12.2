package org.avp.refining;

import org.avp.AliensVsPredator;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModItems;
import org.avp.registry.ModTags;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * Xenomorph acid for the Acid Leaching Vat: use an empty phial on a Xenomorph (alive - so it'll fight
 * back - or a stunned/cocooned one) to draw off some of its blood. It splashes: without acid-proof
 * armour you get burned.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class AcidHarvest
{
    private AcidHarvest()
    {
    }

    @SubscribeEvent
    public static void onUse(PlayerInteractEvent.EntityInteract event)
    {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (!stack.is(ModItems.PHIAL_EMPTY.get()) || !(event.getTarget() instanceof LivingEntity xeno) || !xeno.getType().is(ModTags.XENOMORPHS))
        {
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        if (!(player.level() instanceof ServerLevel level))
        {
            return;
        }
        if (player.getCooldowns().isOnCooldown(stack.getItem()))
        {
            return;
        }
        player.getCooldowns().addCooldown(stack.getItem(), 40);
        xeno.hurt(player.damageSources().playerAttack(player), 2.0F);
        stack.consume(1, player);
        ItemStack acid = new ItemStack(Refining.ACID_VIAL.get());
        if (!player.getInventory().add(acid))
        {
            player.drop(acid, false);
        }
        level.playSound(null, xeno.blockPosition(), SoundEvents.LAVA_EXTINGUISH, xeno.getSoundSource(), 0.8F, 1.6F);
        level.sendParticles(ParticleTypes.SNEEZE, xeno.getX(), xeno.getY() + xeno.getBbHeight() * 0.6, xeno.getZ(), 12, 0.3, 0.3, 0.3, 0.05);
        if (level.random.nextInt(3) > 0) // chitin armour stops the burn (ArmorEffects)
        {
            player.hurt(ModDamageTypes.of(level, ModDamageTypes.ACID), 3.0F);
        }
    }
}
