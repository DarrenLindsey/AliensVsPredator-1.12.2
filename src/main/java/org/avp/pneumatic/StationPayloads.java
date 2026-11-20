package org.avp.pneumatic;

import org.avp.AliensVsPredator;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

/** The PneuPost station screen's messages. */
public final class StationPayloads
{
    public static final int REFRESH = 0, SEND = 1, RENAME = 2, AUTO = 3;

    public record Action(BlockPos pos, int action, String text) implements CustomPacketPayload
    {
        public static final Type<Action> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "station_action"));
        public static final StreamCodec<ByteBuf, Action> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, Action::pos, ByteBufCodecs.VAR_INT,
                Action::action, ByteBufCodecs.stringUtf8(64), Action::text, Action::new);

        @Override
        public Type<? extends CustomPacketPayload> type()
        {
            return TYPE;
        }
    }

    public record Info(CompoundTag data) implements CustomPacketPayload
    {
        public static final Type<Info> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "station_info"));
        public static final StreamCodec<ByteBuf, Info> CODEC = StreamCodec.composite(ByteBufCodecs.COMPOUND_TAG, Info::data, Info::new);

        @Override
        public Type<? extends CustomPacketPayload> type()
        {
            return TYPE;
        }
    }

    private StationPayloads()
    {
    }

    public static void handle(ServerPlayer player, Action a)
    {
        if (player.distanceToSqr(a.pos().getCenter()) > 64 || !(player.level().getBlockEntity(a.pos()) instanceof PneumaticStationBlockEntity st))
        {
            return;
        }
        String message = null;
        switch (a.action())
        {
            case SEND -> message = st.send(a.text().trim().toUpperCase(java.util.Locale.ROOT));
            case RENAME -> st.setName(a.text());
            case AUTO -> st.setAutoTarget(a.text());
            default ->
            {
            }
        }
        TubeNetwork.Survey survey = TubeNetwork.survey(player.level(), a.pos());
        CompoundTag data = new CompoundTag();
        data.putString("Name", st.name());
        data.putString("Auto", st.autoTarget());
        data.putInt("Compressors", survey.compressors());
        data.putInt("Pressurised", survey.pressurised());
        ListTag list = new ListTag();
        survey.stations().keySet().forEach(n -> list.add(StringTag.valueOf(n)));
        data.put("Stations", list);
        if (message != null)
        {
            data.putString("Message", message);
        }
        PacketDistributor.sendToPlayer(player, new Info(data));
    }
}
