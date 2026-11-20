package org.avp.entity.joe.net;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Client -> server: something done on a Seegson Terminal (or tablet, when tablet is true). */
public record JoeTerminalActionPayload(int action, String joe, String text, BlockPos pos, boolean tablet) implements CustomPacketPayload
{
    public static final int REFRESH = 0, UPLOAD = 1, CHECK = 2, RECALL = 3, STOP = 4, RESUME = 5, SECURITY = 6, SAVE_FILE = 7,
            DELETE_FILE = 8, LOAD_FILE = 9, UPLOAD_ALL = 10, DRIVE = 11, SEND = 12, CLEAR_ALARM = 13, FETCH_SOURCE = 14, RENAME = 15,
            REMOTE = 16, CLOSE = 17;

    public static final Type<JoeTerminalActionPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "joe_terminal_action"));
    public static final StreamCodec<ByteBuf, JoeTerminalActionPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, JoeTerminalActionPayload::action,
            ByteBufCodecs.stringUtf8(64), JoeTerminalActionPayload::joe,
            ByteBufCodecs.stringUtf8(20_000), JoeTerminalActionPayload::text,
            BlockPos.STREAM_CODEC, JoeTerminalActionPayload::pos,
            ByteBufCodecs.BOOL, JoeTerminalActionPayload::tablet,
            JoeTerminalActionPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
