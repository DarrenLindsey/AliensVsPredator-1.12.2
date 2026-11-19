package org.avp.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.block.entity.ShapeBlockEntity;
import org.avp.config.AvpConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;

/**
 * Wraps the OBJ model of a slope/corner/ridge/pyramid. When the block has copied
 * another block (ShapeBlockEntity.MIMIC), every face is re-pointed at that
 * block's texture instead of the plain "reflection" texture.
 *
 * Texture coordinates (UVs) of a baked quad point into the big block texture
 * atlas, inside the area of its sprite. Re-texturing means moving each UV from
 * the old sprite's area to the same relative spot in the new sprite's area.
 */
public class MimicBakedModel extends BakedModelWrapper<BakedModel>
{
    /** Ints per vertex in the block vertex format, and where U and V are. */
    private static final int STRIDE = 8;
    private static final int U = 4;
    private static final int V = 5;

    public MimicBakedModel(BakedModel original)
    {
        super(original);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType)
    {
        List<BakedQuad> quads = super.getQuads(state, side, rand, data, renderType);
        BlockState mimic = data.get(ShapeBlockEntity.MIMIC);
        if (mimic == null || !AvpConfig.client(AvpConfig.CLIENT.showCopiedTextures))
        {
            return quads;
        }
        TextureAtlasSprite target = Minecraft.getInstance().getBlockRenderer().getBlockModel(mimic).getParticleIcon(ModelData.EMPTY);
        List<BakedQuad> out = new ArrayList<>(quads.size());
        for (BakedQuad quad : quads)
        {
            out.add(retexture(quad, target));
        }
        return out;
    }

    private static BakedQuad retexture(BakedQuad quad, TextureAtlasSprite target)
    {
        TextureAtlasSprite source = quad.getSprite();
        int[] data = quad.getVertices().clone();
        for (int v = 0; v < 4; v++)
        {
            int i = v * STRIDE;
            float u = Float.intBitsToFloat(data[i + U]);
            float vv = Float.intBitsToFloat(data[i + V]);
            float relU = (u - source.getU0()) / (source.getU1() - source.getU0());
            float relV = (vv - source.getV0()) / (source.getV1() - source.getV0());
            data[i + U] = Float.floatToRawIntBits(target.getU0() + relU * (target.getU1() - target.getU0()));
            data[i + V] = Float.floatToRawIntBits(target.getV0() + relV * (target.getV1() - target.getV0()));
        }
        return new BakedQuad(data, quad.getTintIndex(), quad.getDirection(), target, quad.isShade());
    }
}
