package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Server -> client: the player's biomask vision mode changed. */
public record VisionModePayload(int mode) implements CustomPacketPayload
{
    public static final Type<VisionModePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "vision_mode"));
    public static final StreamCodec<ByteBuf, VisionModePayload> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, VisionModePayload::mode,
            VisionModePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
