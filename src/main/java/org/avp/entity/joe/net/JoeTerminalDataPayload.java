package org.avp.entity.joe.net;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Server -> client: what a Seegson Terminal screen shows (opens the screen if "Open" is set). */
public record JoeTerminalDataPayload(CompoundTag data) implements CustomPacketPayload
{
    public static final Type<JoeTerminalDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "joe_terminal_data"));
    public static final StreamCodec<ByteBuf, JoeTerminalDataPayload> CODEC = StreamCodec.composite(ByteBufCodecs.COMPOUND_TAG,
            JoeTerminalDataPayload::data, JoeTerminalDataPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
