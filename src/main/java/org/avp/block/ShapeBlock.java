package org.avp.block;

import org.avp.block.entity.ShapeBlockEntity;
import org.avp.config.AvpConfig;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Slopes, corners, ridges and pyramids ("reflective" blocks in 1.12.2).
 *
 * - Look up while placing to put it upside down, sneak while placing to put it on its side.
 * - Right-click it with any normal block to give it that block's texture.
 * - Sneak + right-click with an empty hand to go back to the plain texture.
 */
public class ShapeBlock extends BaseEntityBlock
{
    public enum Alignment implements StringRepresentable
    {
        BOTTOM, TOP, SIDE;

        @Override
        public String getSerializedName()
        {
            return name().toLowerCase();
        }
    }

    public static final MapCodec<ShapeBlock> CODEC = simpleCodec(ShapeBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Alignment> ALIGNMENT = EnumProperty.create("alignment", Alignment.class);

    public ShapeBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ALIGNMENT, Alignment.BOTTOM));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, ALIGNMENT);
    }

    /** Same rules as the 1.12.2 version (BlockReflective.onBlockPlacedBy). */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Player player = context.getPlayer();
        boolean sneaking = player != null && player.isShiftKeyDown();
        boolean lookingUp = player != null && player.getXRot() < 0;
        Alignment alignment = sneaking ? Alignment.SIDE : lookingUp ? Alignment.TOP : Alignment.BOTTOM;
        Direction facing = (!sneaking && lookingUp) ? context.getHorizontalDirection() : context.getHorizontalDirection().getOpposite();
        return defaultBlockState().setValue(FACING, facing).setValue(ALIGNMENT, alignment);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (AvpConfig.SERVER.shapesCanCopyBlocks.get()
                && stack.getItem() instanceof BlockItem blockItem
                && !(blockItem.getBlock() instanceof EntityBlock)
                && level.getBlockEntity(pos) instanceof ShapeBlockEntity shape)
        {
            if (!level.isClientSide)
            {
                shape.setMimic(blockItem.getBlock().defaultBlockState());
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (player.isShiftKeyDown() && level.getBlockEntity(pos) instanceof ShapeBlockEntity shape && shape.getMimic() != null)
        {
            if (!level.isClientSide)
            {
                shape.setMimic(null);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    /** BaseEntityBlock is invisible by default; these blocks use a normal (OBJ) model. */
    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ShapeBlockEntity(pos, state);
    }
}
