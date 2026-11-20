package org.avp.block.conveyor;

import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.entity.joe.JoeExpr;

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

/** The Logic Belt script editor's messages. */
public final class ConveyorPayloads
{
    /** Client -> server: save (text) or refresh (text empty, refresh true). */
    public record Save(BlockPos pos, String text, boolean refresh) implements CustomPacketPayload
    {
        public static final Type<Save> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "belt_script_save"));
        public static final StreamCodec<ByteBuf, Save> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, Save::pos, ByteBufCodecs.stringUtf8(8000),
                Save::text, ByteBufCodecs.BOOL, Save::refresh, Save::new);

        @Override
        public Type<? extends CustomPacketPayload> type()
        {
            return TYPE;
        }
    }

    /** Server -> client: the script, its variables and problems (Open: show the editor). */
    public record Info(CompoundTag data) implements CustomPacketPayload
    {
        public static final Type<Info> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "belt_script_info"));
        public static final StreamCodec<ByteBuf, Info> CODEC = StreamCodec.composite(ByteBufCodecs.COMPOUND_TAG, Info::data, Info::new);

        @Override
        public Type<? extends CustomPacketPayload> type()
        {
            return TYPE;
        }
    }

    private ConveyorPayloads()
    {
    }

    public static void open(ServerPlayer player, ConveyorBlockEntity be)
    {
        CompoundTag data = info(be, List.of());
        data.putBoolean("Open", true);
        data.putString("Script", be.script());
        PacketDistributor.sendToPlayer(player, new Info(data));
    }

    private static CompoundTag info(ConveyorBlockEntity be, List<String> errors)
    {
        CompoundTag data = new CompoundTag();
        data.putLong("Pos", be.getBlockPos().asLong());
        ListTag vars = new ListTag();
        be.vars().forEach((k, v) -> vars.add(StringTag.valueOf(k + " = " + JoeExpr.text(v))));
        data.put("Vars", vars);
        ListTag errs = new ListTag();
        errors.forEach(e -> errs.add(StringTag.valueOf(e)));
        if (errors.isEmpty() && !be.lastError().isEmpty())
        {
            errs.add(StringTag.valueOf("Last run: " + be.lastError()));
        }
        data.put("Errors", errs);
        data.putInt("Signal", be.signal());
        return data;
    }

    public static void handle(ServerPlayer player, Save save)
    {
        if (player.distanceToSqr(save.pos().getCenter()) > 64 || !(player.level().getBlockEntity(save.pos()) instanceof ConveyorBlockEntity be))
        {
            return;
        }
        List<String> errors = save.refresh() ? List.of() : be.setScript(save.text());
        CompoundTag data = info(be, errors);
        if (!save.refresh())
        {
            data.putString("Message", errors.isEmpty() ? "Script saved." : "Not saved: fix the errors.");
        }
        PacketDistributor.sendToPlayer(player, new Info(data));
    }
}
