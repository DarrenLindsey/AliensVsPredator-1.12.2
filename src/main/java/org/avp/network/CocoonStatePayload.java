package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Server -> client: whether to render resin wrapped around this host. */
public record CocoonStatePayload(int entityId, boolean wrapped) implements CustomPacketPayload
{
    public static final Type<CocoonStatePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "cocoon_state"));
    public static final StreamCodec<ByteBuf, CocoonStatePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CocoonStatePayload::entityId, ByteBufCodecs.BOOL, CocoonStatePayload::wrapped, CocoonStatePayload::new);

    @Override
    public Type<CocoonStatePayload> type()
    {
        return TYPE;
    }
}
