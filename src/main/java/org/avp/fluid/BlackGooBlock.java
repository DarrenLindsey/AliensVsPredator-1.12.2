package org.avp.fluid;

import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

/**
 * Black goo (Prometheus): it eats away at whatever is in it, and mutates living things -
 * villagers and illagers into goo mutants, silverfish and endermites into Hammerpedes, Yautja into
 * mutant Yautja, livestock into "babyheads". Plants it touches wither.
 */
public class BlackGooBlock extends LiquidBlock
{
    private static final Map<EntityType<?>, String> MUTATIONS = Map.ofEntries(
            Map.entry(EntityType.VILLAGER, "goo_mutant"), Map.entry(EntityType.WANDERING_TRADER, "goo_mutant"),
            Map.entry(EntityType.PILLAGER, "goo_mutant"), Map.entry(EntityType.VINDICATOR, "goo_mutant"),
            Map.entry(EntityType.WITCH, "goo_mutant"), Map.entry(EntityType.ZOMBIE_VILLAGER, "goo_mutant"),
            Map.entry(EntityType.SILVERFISH, "hammerpede"), Map.entry(EntityType.ENDERMITE, "hammerpede"),
            Map.entry(EntityType.COW, "babyhead"), Map.entry(EntityType.PIG, "babyhead"), Map.entry(EntityType.SHEEP, "babyhead"));

    public BlackGooBlock(FlowingFluid fluid, Properties properties)
    {
        super(fluid, properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (!(level instanceof ServerLevel server) || !(entity instanceof LivingEntity living) || entity.tickCount % 20 != 0)
        {
            return;
        }
        living.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 0));
        if (living instanceof Player)
        {
            living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0));
            return;
        }
        if (AvpConfig.server(AvpConfig.SERVER.blackGooMutation) && living instanceof Mob && server.random.nextFloat() < 0.15F)
        {
            mutate(server, living);
        }
    }

    private static void mutate(ServerLevel level, LivingEntity victim)
    {
        String into = MUTATIONS.get(victim.getType());
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(victim.getType()).getPath();
        if (into == null && (id.equals("yautja_warrior") || id.equals("yautja_berserker")))
        {
            into = "yautja_mutant";
        }
        if (into == null && (id.equals("marine") || id.equals("engineer")))
        {
            into = "goo_mutant";
        }
        if (into == null)
        {
            return;
        }
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, into));
        Entity mutant = type.create(level, null, victim.blockPosition(), MobSpawnType.CONVERSION, false, false);
        if (mutant == null)
        {
            return;
        }
        mutant.moveTo(victim.getX(), victim.getY(), victim.getZ(), victim.getYRot(), victim.getXRot());
        level.addFreshEntity(mutant);
        level.sendParticles(ParticleTypes.SQUID_INK, victim.getX(), victim.getY() + 0.8, victim.getZ(), 30, 0.4, 0.6, 0.4, 0.05);
        level.playSound(null, victim.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.HOSTILE, 1.0F, 0.6F);
        victim.discard();
    }

    /** Plants next to it wither. */
    @Override
    protected boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random)
    {
        BlockPos near = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
        BlockState s = level.getBlockState(near);
        if (s.is(Blocks.GRASS_BLOCK))
        {
            level.setBlock(near, Blocks.COARSE_DIRT.defaultBlockState(), 3);
        }
        else if (s.is(net.minecraft.tags.BlockTags.REPLACEABLE_BY_TREES) || s.is(net.minecraft.tags.BlockTags.FLOWERS)
                || s.is(net.minecraft.tags.BlockTags.SAPLINGS))
        {
            level.setBlock(near, Blocks.DEAD_BUSH.defaultBlockState().canSurvive(level, near)
                    ? Blocks.DEAD_BUSH.defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
        }
    }
}
