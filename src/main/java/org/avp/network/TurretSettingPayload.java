package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Client -> server: switch one of a sentry turret's target categories on or off. */
public record TurretSettingPayload(BlockPos pos, int category) implements CustomPacketPayload
{
    public static final Type<TurretSettingPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "turret_setting"));

    public static final StreamCodec<ByteBuf, TurretSettingPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, TurretSettingPayload::pos,
            ByteBufCodecs.VAR_INT, TurretSettingPayload::category,
            TurretSettingPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
