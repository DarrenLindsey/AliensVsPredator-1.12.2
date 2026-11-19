package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Client -> server: something the player did on a blast door screen.
 * The server checks everything again (distance, power, the held tuner) before acting.
 */
public record BlastDoorActionPayload(BlockPos pos, int action, String text) implements CustomPacketPayload
{
    public static final int TRY_PASSWORD = 0;
    public static final int SET_PASSWORD = 1;
    public static final int TOGGLE_AUTOLOCK = 2;
    public static final int BIND_TUNER = 3;
    public static final int TOGGLE_AUTO_OPEN = 4;
    public static final int TOGGLE_THREAT_DETECTION = 5;
    public static final int MAX_PASSWORD_LENGTH = 32;

    public static final Type<BlastDoorActionPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "blast_door_action"));

    public static final StreamCodec<ByteBuf, BlastDoorActionPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, BlastDoorActionPayload::pos,
            ByteBufCodecs.VAR_INT, BlastDoorActionPayload::action,
            ByteBufCodecs.stringUtf8(MAX_PASSWORD_LENGTH), BlastDoorActionPayload::text,
            BlastDoorActionPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
