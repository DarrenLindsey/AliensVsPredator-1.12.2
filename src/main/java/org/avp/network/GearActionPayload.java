package org.avp.network;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/** Client -> server: a gear key was pressed (see org.avp.item.gear.GearActions). */
public record GearActionPayload(int action) implements CustomPacketPayload
{
    public static final int GRENADE_LAUNCHER = 0, VISION_MODE = 1, CLOAK = 2, SELF_DESTRUCT = 3;

    public static final Type<GearActionPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "gear_action"));
    public static final StreamCodec<ByteBuf, GearActionPayload> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, GearActionPayload::action,
            GearActionPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
