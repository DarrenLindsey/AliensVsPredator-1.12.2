package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Client -> server: an automation setting on the Assembler screen. */
public record AssemblerSettingPayload(BlockPos pos, int action, String recipe) implements CustomPacketPayload
{
    /** Lock to recipe (or unlock if it's already the locked one). */
    public static final int LOCK = 0;
    public static final int TOGGLE_AUTO_OUTPUT = 1;
    public static final int EMPTY_BUFFERS = 2;

    public static final Type<AssemblerSettingPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "assembler_setting"));

    public static final StreamCodec<ByteBuf, AssemblerSettingPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, AssemblerSettingPayload::pos,
            ByteBufCodecs.VAR_INT, AssemblerSettingPayload::action,
            ByteBufCodecs.stringUtf8(256), AssemblerSettingPayload::recipe,
            AssemblerSettingPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
