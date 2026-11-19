package org.avp.block;

import java.util.function.IntSupplier;

import javax.annotation.Nullable;

import org.avp.block.entity.PoweredBlockEntity;
import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Blocks that run on power (see PoweredBlockEntity): light panel, CCFL tube, cryostasis tube.
 * They face the player who placed them and are drawn by their own renderers.
 */
public class PoweredBlock extends BaseEntityBlock
{
    /** How a CCFL tube is mounted (from where the player was looking when placing it, like 1.12.2). */
    public enum Tilt implements StringRepresentable
    {
        LEVEL, UP, DOWN;

        @Override
        public String getSerializedName()
        {
            return name().toLowerCase();
        }
    }

    public static final MapCodec<PoweredBlock> CODEC = simpleCodec(p -> new PoweredBlock(p, () -> 1));
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Tilt> TILT = EnumProperty.create("tilt", Tilt.class);
    /** Cryostasis tube glass: 0 = intact, 1 = cracked, 2 = shattered. */
    public static final IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 2);

    private final IntSupplier energyPerTick;

    /** @param energyPerTick FE used every tick while on (read from the settings) */
    public PoweredBlock(Properties properties, IntSupplier energyPerTick)
    {
        super(properties);
        this.energyPerTick = energyPerTick;
        registerDefaultState(stateDefinition.any().setValue(LIT, false).setValue(FACING, Direction.NORTH)
                .setValue(TILT, Tilt.LEVEL).setValue(DAMAGE, 0));
    }

    public int energyPerTick()
    {
        return energyPerTick.getAsInt();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(LIT, FACING, TILT, DAMAGE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Tilt tilt = Tilt.LEVEL;
        if (context.getPlayer() != null)
        {
            // same rule as 1.12.2 Entities.getEntityFacingRotX: look up -> UP, look down -> DOWN
            int dir = Mth.floor(context.getPlayer().getXRot() / 90F + 0.5F) & 3;
            tilt = dir == 3 ? Tilt.UP : dir == 1 ? Tilt.DOWN : Tilt.LEVEL;
        }
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TILT, tilt);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PoweredBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide
                ? createTickerHelper(type, ModBlockEntities.POWERED.get(), PoweredBlockEntity::clientTick)
                : createTickerHelper(type, ModBlockEntities.POWERED.get(), PoweredBlockEntity::serverTick);
    }

    /** Extra work every server tick, after the power check (e.g. the medpod healing). */
    public void poweredTick(Level level, BlockPos pos, BlockState state, PoweredBlockEntity be)
    {
    }

    /**
     * Where an opening animation is heading on the client: 1 = fully open, 0 = closed.
     * The block entity moves its animation value towards this a little every tick.
     */
    public float animationTarget(BlockState state)
    {
        return 0;
    }
}
