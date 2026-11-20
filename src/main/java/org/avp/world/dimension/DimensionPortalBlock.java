package org.avp.world.dimension;

import java.util.List;

import org.avp.registry.ModBlocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A portal to Varda or Acheron (1.12.2 BlockPortal). Step into it: from any other world it takes you
 * to its planet, from its planet back to the Overworld. You arrive on the surface above the same
 * spot, with a return portal built beside you.
 */
public class DimensionPortalBlock extends Block
{
    private final ResourceKey<Level> destination;

    public DimensionPortalBlock(ResourceKey<Level> destination, Properties properties)
    {
        super(properties);
        this.destination = destination;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return Shapes.empty();
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (!(level instanceof ServerLevel server) || entity.isPassenger() || entity.isVehicle() || !entity.canUsePortal(false)
                || entity.isOnPortalCooldown())
        {
            return;
        }
        ResourceKey<Level> to = level.dimension() == destination ? Level.OVERWORLD : destination;
        ServerLevel target = server.getServer().getLevel(to);
        if (target == null)
        {
            return;
        }
        BlockPos arrival = arrival(target, pos);
        entity.setPortalCooldown(100);
        entity.changeDimension(new DimensionTransition(target, Vec3.atBottomCenterOf(arrival), Vec3.ZERO, entity.getYRot(), entity.getXRot(),
                DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)));
        if (entity instanceof Player player)
        {
            player.displayClientMessage(Component.translatable("message.avp.portal_arrived",
                    Component.translatable("biome.avp." + destination.location().getPath())), true);
        }
    }

    /** The surface above the same spot in the other world, with a floor under you and a portal back beside you. */
    private BlockPos arrival(ServerLevel target, BlockPos from)
    {
        // you arrive 2 blocks west of the portal's column; the portal back stands in the column itself
        // (so a round trip lands next to the portal you left by, not on top of it)
        BlockPos column = from.west(2);
        target.getChunk(column.getX() >> 4, column.getZ() >> 4); // make sure it exists
        int y = target.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column.getX(), column.getZ());
        if (y <= target.getMinBuildHeight() + 1)
        {
            y = 80;
        }
        BlockPos at = new BlockPos(column.getX(), y, column.getZ());
        if (!target.getBlockState(at.below()).isSolid())
        {
            for (BlockPos p : BlockPos.betweenClosed(at.offset(-1, -1, -1), at.offset(1, -1, 1)))
            {
                target.setBlock(p, ModBlocks.UNISTONE.get().defaultBlockState(), 3);
            }
        }
        target.setBlock(at, Blocks.AIR.defaultBlockState(), 3);
        target.setBlock(at.above(), Blocks.AIR.defaultBlockState(), 3);
        BlockPos back = at.east(2);
        if (!target.getBlockState(back).is(this) && !target.getBlockState(new BlockPos(back.getX(), from.getY(), back.getZ())).is(this))
        {
            target.setBlock(back.below(), ModBlocks.UNISTONE.get().defaultBlockState(), 3);
            target.setBlock(back, defaultBlockState(), 3);
            target.setBlock(back.above(), Blocks.AIR.defaultBlockState(), 3);
        }
        return at;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (random.nextInt(3) == 0)
        {
            level.addParticle(ParticleTypes.PORTAL, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(),
                    (random.nextDouble() - 0.5) * 0.5, random.nextDouble() * 0.3, (random.nextDouble() - 0.5) * 0.5);
        }
    }

    public static class PortalItem extends net.minecraft.world.item.BlockItem
    {
        public PortalItem(Block block, Item.Properties properties)
        {
            super(block, properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
        {
            tooltip.add(Component.translatable("tooltip.avp." + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(this).getPath())
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
