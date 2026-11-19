package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Client -> server: "assemble this schematic this many times" from the Assembler screen. */
public record AssemblePayload(BlockPos pos, ResourceLocation recipe, int count) implements CustomPacketPayload
{
    public static final Type<AssemblePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "assemble"));

    public static final StreamCodec<ByteBuf, AssemblePayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, AssemblePayload::pos,
            ResourceLocation.STREAM_CODEC, AssemblePayload::recipe,
            ByteBufCodecs.VAR_INT, AssemblePayload::count,
            AssemblePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
