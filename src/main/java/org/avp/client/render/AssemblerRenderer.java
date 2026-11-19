package org.avp.client.render;

import java.util.List;

import org.avp.block.entity.AssemblerBlockEntity;
import org.avp.crafting.AssemblyRecipe;
import org.avp.registry.ModRecipes;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

/**
 * The Assembler's hologram (1.12.2 RenderAssembler): a red beam of light with a spinning
 * picture of the last thing it made - or, until then, a different schematic every few seconds.
 */
public class AssemblerRenderer implements BlockEntityRenderer<AssemblerBlockEntity>
{
    public AssemblerRenderer(BlockEntityRendererProvider.Context context)
    {
    }

    @Override
    public void render(AssemblerBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        if (be.getLevel() == null)
        {
            return;
        }
        long time = be.getLevel().getGameTime();
        ItemStack shown = be.lastMade();
        if (shown.isEmpty())
        {
            List<RecipeHolder<AssemblyRecipe>> recipes = be.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.ASSEMBLY.get());
            if (!recipes.isEmpty())
            {
                shown = recipes.get((int) ((time / 60) % recipes.size())).value().result();
            }
        }
        float spin = (time + partialTick) * 3F;

        // the beam: a faint red cone from the top of the block
        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer beam = buffers.getBuffer(RenderType.lightning());
        int sides = 12;
        for (int i = 0; i < sides; i++)
        {
            float a1 = (float) (i * Math.PI * 2 / sides), a2 = (float) ((i + 1) * Math.PI * 2 / sides);
            float r = 0.32F;
            beam.addVertex(matrix, 0.5F, 1.0F, 0.5F).setColor(1F, 0.1F, 0.1F, 0.35F);
            beam.addVertex(matrix, 0.5F, 1.0F, 0.5F).setColor(1F, 0.1F, 0.1F, 0.35F);
            beam.addVertex(matrix, 0.5F + Mth.cos(a2) * r, 1.45F, 0.5F + Mth.sin(a2) * r).setColor(1F, 0.1F, 0.1F, 0.0F);
            beam.addVertex(matrix, 0.5F + Mth.cos(a1) * r, 1.45F, 0.5F + Mth.sin(a1) * r).setColor(1F, 0.1F, 0.1F, 0.0F);
        }

        if (!shown.isEmpty())
        {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.3F + Mth.sin((time + partialTick) / 10F) * 0.03F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(spin));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            Minecraft.getInstance().getItemRenderer().renderStatic(shown, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, overlay,
                    poseStack, buffers, be.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
