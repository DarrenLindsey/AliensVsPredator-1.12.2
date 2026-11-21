package org.avp.client.render;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

/** Applies a pale shell palette at texture load, keeping the drone model's texture coordinates and alpha. */
final class WeaverTexture extends SimpleTexture
{
    private final ResourceLocation output;
    private final int shadow;
    private final int highlight;

    private WeaverTexture(ResourceLocation output, ResourceLocation source, int shadow, int highlight)
    {
        super(source);
        this.output = output;
        this.shadow = shadow;
        this.highlight = highlight;
    }

    static ResourceLocation get(ResourceLocation output, ResourceLocation source, int shadow, int highlight)
    {
        var manager = Minecraft.getInstance().getTextureManager();
        if (manager.getTexture(output, null) == null)
        {
            manager.register(output, new WeaverTexture(output, source, shadow, highlight));
        }
        return output;
    }

    @Override
    protected TextureImage getTextureImage(ResourceManager resources)
    {
        // An alternate art pack can supply a finished Weaver texture directly.
        if (resources.getResource(output).isPresent())
        {
            return TextureImage.load(resources, output);
        }
        TextureImage atlas = super.getTextureImage(resources);
        try
        {
            var image = atlas.getImage();
            for (int y = 0; y < image.getHeight(); y++)
            {
                for (int x = 0; x < image.getWidth(); x++)
                {
                    int pixel = image.getPixelRGBA(x, y);
                    int alpha = FastColor.ABGR32.alpha(pixel);
                    if (alpha != 0)
                    {
                        float luma = (0.2126F * FastColor.ABGR32.red(pixel) + 0.7152F * FastColor.ABGR32.green(pixel)
                                + 0.0722F * FastColor.ABGR32.blue(pixel)) / 255F;
                        float tone = Mth.sqrt(luma);
                        int red = Mth.lerpInt(tone, shadow >> 16 & 255, highlight >> 16 & 255);
                        int green = Mth.lerpInt(tone, shadow >> 8 & 255, highlight >> 8 & 255);
                        int blue = Mth.lerpInt(tone, shadow & 255, highlight & 255);
                        image.setPixelRGBA(x, y, FastColor.ABGR32.color(alpha, blue, green, red));
                    }
                }
            }
        }
        catch (IOException ignored)
        {
            // SimpleTexture handles the original read error and the missing-texture fallback.
        }
        return atlas;
    }
}
