package org.avp.block.entity;

import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Only here so the powerline can be drawn with its 1.12.2 model; it holds no energy. */
public class PowerlineBlockEntity extends BlockEntity
{
    public PowerlineBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.POWERLINE.get(), pos, state);
    }
}
