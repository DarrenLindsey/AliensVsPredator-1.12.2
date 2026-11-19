package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Server -> client: open the blast door password screen (admin = the Security Tuner admin screen). */
public record OpenBlastDoorScreenPayload(BlockPos pos, boolean admin, String identifier, boolean autolock, boolean hasPassword)
        implements CustomPacketPayload
{
    public static final Type<OpenBlastDoorScreenPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "open_blast_door_screen"));

    public static final StreamCodec<ByteBuf, OpenBlastDoorScreenPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, OpenBlastDoorScreenPayload::pos,
            ByteBufCodecs.BOOL, OpenBlastDoorScreenPayload::admin,
            ByteBufCodecs.stringUtf8(16), OpenBlastDoorScreenPayload::identifier,
            ByteBufCodecs.BOOL, OpenBlastDoorScreenPayload::autolock,
            ByteBufCodecs.BOOL, OpenBlastDoorScreenPayload::hasPassword,
            OpenBlastDoorScreenPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
