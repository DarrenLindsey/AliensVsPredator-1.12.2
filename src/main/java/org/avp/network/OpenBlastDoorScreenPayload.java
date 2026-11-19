package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Server -> client: open the blast door password screen (admin = the Security Tuner admin screen). */
public record OpenBlastDoorScreenPayload(BlockPos pos, boolean admin, String identifier, boolean autolock, boolean hasPassword,
                                         boolean autoOpen, boolean threatDetection)
        implements CustomPacketPayload
{
    public static final Type<OpenBlastDoorScreenPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "open_blast_door_screen"));

    /** Written by hand: StreamCodec.composite only takes up to six fields. */
    public static final StreamCodec<ByteBuf, OpenBlastDoorScreenPayload> CODEC = StreamCodec.of(
            (buf, p) ->
            {
                BlockPos.STREAM_CODEC.encode(buf, p.pos());
                ByteBufCodecs.stringUtf8(16).encode(buf, p.identifier());
                int flags = (p.admin() ? 1 : 0) | (p.autolock() ? 2 : 0) | (p.hasPassword() ? 4 : 0)
                        | (p.autoOpen() ? 8 : 0) | (p.threatDetection() ? 16 : 0);
                ByteBufCodecs.VAR_INT.encode(buf, flags);
            },
            buf ->
            {
                BlockPos pos = BlockPos.STREAM_CODEC.decode(buf);
                String identifier = ByteBufCodecs.stringUtf8(16).decode(buf);
                int flags = ByteBufCodecs.VAR_INT.decode(buf);
                return new OpenBlastDoorScreenPayload(pos, (flags & 1) != 0, identifier, (flags & 2) != 0, (flags & 4) != 0,
                        (flags & 8) != 0, (flags & 16) != 0);
            });

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
