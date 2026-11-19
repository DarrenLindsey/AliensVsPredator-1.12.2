package org.avp.entity.parasite;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModSounds;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * Grows embryos inside their hosts (1.12.2 Organism.onTick + Embryo.grow).
 * Near the end the host feels sick; at the end the Chestburster comes out and the host dies.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class EmbryoEvents
{
    /** What comes out of a "standard" embryo. Registered with the Chestburster (next creature). */
    public static final ResourceLocation CHESTBURSTER = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "chestburster");

    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event)
    {
        if (!(event.getEntity() instanceof LivingEntity host) || host.level().isClientSide || !host.hasData(ModAttachments.EMBRYO))
        {
            return;
        }
        if (host instanceof Player player && (player.isCreative() || player.isSpectator()))
        {
            return; // paused in creative
        }
        Embryo embryo = host.getData(ModAttachments.EMBRYO).older();
        host.setData(ModAttachments.EMBRYO, embryo);

        if (embryo.isLate() && AvpConfig.server(AvpConfig.SERVER.embryoSymptoms) && host.tickCount % 40 == 0)
        {
            host.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false));
            host.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 1, false, false));
            host.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 1, false, false));
        }
        if (embryo.age() >= embryo.gestation())
        {
            burst(host);
        }
    }

    /** The end: it comes out of the host's chest. */
    public static void burst(LivingEntity host)
    {
        host.removeData(ModAttachments.EMBRYO);
        if (!(host.level() instanceof ServerLevel level))
        {
            return;
        }
        level.playSound(null, host.blockPosition(), ModSounds.CHESTBURSTER_BURST.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
                host.getX(), host.getY() + host.getBbHeight() * 0.6, host.getZ(), 60, 0.25, 0.25, 0.25, 0.2);

        // the Chestburster arrives with the next creature; until then nothing climbs out
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(CHESTBURSTER).orElse(null);
        if (type != null)
        {
            Entity burster = type.create(level, null, host.blockPosition(), MobSpawnType.BREEDING, false, false);
            if (burster != null)
            {
                burster.moveTo(host.getX(), host.getY() + host.getBbHeight() * 0.5, host.getZ(), host.getYRot(), 0);
                level.addFreshEntity(burster);
            }
        }
        host.hurt(ModDamageTypes.of(level, ModDamageTypes.CHESTBURSTER), Float.MAX_VALUE);
    }

    private EmbryoEvents()
    {
    }
}
