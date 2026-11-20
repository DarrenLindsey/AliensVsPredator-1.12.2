package org.avp.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.block.entity.ShapeBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;

/**
 * Hive resin: draws the block underneath (from the resin's block entity) and the resin coating
 * over it, so a resin-covered wall still reads as stone, deepslate, dirt...
 */
public class ResinBakedModel extends BakedModelWrapper<BakedModel>
{
    private static final ChunkRenderTypeSet CUTOUT = ChunkRenderTypeSet.of(RenderType.cutoutMipped());

    public ResinBakedModel(BakedModel original)
    {
        super(original);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data)
    {
        return CUTOUT; // both layers in one pass, the coating drawn after (on top of) the block
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType)
    {
        BlockState under = data.get(ShapeBlockEntity.MIMIC);
        List<BakedQuad> coating = super.getQuads(state, side, rand, data, renderType);
        if (under == null)
        {
            return coating;
        }
        BakedModel underModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(under);
        List<BakedQuad> out = new ArrayList<>();
        for (RenderType type : underModel.getRenderTypes(under, rand, ModelData.EMPTY))
        {
            out.addAll(underModel.getQuads(under, side, rand, ModelData.EMPTY, type));
        }
        out.addAll(coating);
        return out;
    }
}
