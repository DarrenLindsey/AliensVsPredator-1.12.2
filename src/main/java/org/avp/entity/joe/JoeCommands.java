package org.avp.entity.joe;

import java.util.ArrayList;
import java.util.List;

import org.avp.AliensVsPredator;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * /joes - your Seegson network, open to every player (for their own Joes):
 * list, recall, lockdown, resume, stop, clearalarm, run PROGRAM [JOE|all], manual, example PROGRAM.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class JoeCommands
{
    private JoeCommands()
    {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> d = event.getDispatcher();
        d.register(Commands.literal("joes")
                .executes(c -> list(c))
                .then(Commands.literal("list").executes(JoeCommands::list))
                .then(Commands.literal("recall").executes(c -> each(c, "all", WorkingJoeEntity::recall, "Recalling all units.")))
                .then(Commands.literal("lockdown").executes(c -> each(c, "all", joe ->
                {
                    joe.setSecurity(true);
                    joe.recall();
                }, "LOCKDOWN: all units to security mode.")))
                .then(Commands.literal("resume").executes(c -> each(c, "all", joe ->
                {
                    joe.setSecurity(false);
                    joe.resume();
                }, "Resuming normal operation.")))
                .then(Commands.literal("stop").executes(c -> each(c, "all", WorkingJoeEntity::clearProgram, "All programs cleared.")))
                .then(Commands.literal("clearalarm").executes(c ->
                {
                    JoeNetwork.clearAlarm(c.getSource().getPlayerOrException().getUUID());
                    c.getSource().sendSuccess(() -> Component.literal("Alarm cleared."), false);
                    return 1;
                }))
                .then(Commands.literal("run")
                        .then(Commands.argument("program", ResourceLocationArgument.id()).suggests((c, b) ->
                                SharedSuggestionProvider.suggestResource(JoeNetwork.datapackPrograms(c.getSource().getLevel()), b))
                                .executes(c -> run(c, "all"))
                                .then(Commands.argument("joe", StringArgumentType.greedyString()).executes(c -> run(c, StringArgumentType.getString(c, "joe"))))))
                .then(Commands.literal("manual").executes(JoeCommands::manual))
                .then(Commands.literal("example")
                        .then(Commands.argument("program", ResourceLocationArgument.id()).suggests((c, b) ->
                                SharedSuggestionProvider.suggestResource(JoeNetwork.datapackPrograms(c.getSource().getLevel()), b))
                                .executes(JoeCommands::example))));
    }

    private static int list(CommandContext<CommandSourceStack> c) throws com.mojang.brigadier.exceptions.CommandSyntaxException
    {
        ServerPlayer player = c.getSource().getPlayerOrException();
        List<WorkingJoeEntity> joes = JoeNetwork.of(player.getUUID());
        player.sendSystemMessage(Component.literal("== Seegson network: " + joes.size() + " unit(s) online ==").withStyle(ChatFormatting.DARK_AQUA));
        for (WorkingJoeEntity joe : joes)
        {
            ChatFormatting colour = joe.isRogue() ? ChatFormatting.RED : !joe.isActive() ? ChatFormatting.DARK_GRAY
                    : joe.chargePercent() < 20 ? ChatFormatting.GOLD : ChatFormatting.GRAY;
            player.sendSystemMessage(Component.literal(" " + joe.joeName() + " [" + joe.chargePercent() + "%] " + joe.currentActivity() + " @ "
                    + WorkingJoeEntity.where(joe.blockPosition())).withStyle(colour));
        }
        return joes.size();
    }

    private static int each(CommandContext<CommandSourceStack> c, String who, java.util.function.Consumer<WorkingJoeEntity> action, String said)
            throws com.mojang.brigadier.exceptions.CommandSyntaxException
    {
        ServerPlayer player = c.getSource().getPlayerOrException();
        int n = 0;
        for (WorkingJoeEntity joe : JoeNetwork.of(player.getUUID()))
        {
            if ((who.equals("all") || joe.joeName().equalsIgnoreCase(who)) && !joe.isRogue())
            {
                action.accept(joe);
                n++;
            }
        }
        int count = n;
        c.getSource().sendSuccess(() -> Component.literal("[Seegson] " + said + " (" + count + " units)").withStyle(ChatFormatting.DARK_AQUA), false);
        return n;
    }

    private static int run(CommandContext<CommandSourceStack> c, String who) throws com.mojang.brigadier.exceptions.CommandSyntaxException
    {
        ResourceLocation id = ResourceLocationArgument.getId(c, "program");
        String text = JoeNetwork.readDatapackProgram(c.getSource().getLevel(), id);
        if (text == null)
        {
            c.getSource().sendFailure(Component.literal("No Joe program called " + id));
            return 0;
        }
        ServerPlayer player = c.getSource().getPlayerOrException();
        List<String> errors = new ArrayList<>();
        int n = each(c, who, joe -> errors.addAll(joe.loadProgram(text, player)), "Loading " + id);
        errors.stream().distinct().limit(6).forEach(e -> player.sendSystemMessage(Component.literal("  " + e).withStyle(ChatFormatting.RED)));
        return n;
    }

    private static int example(CommandContext<CommandSourceStack> c) throws com.mojang.brigadier.exceptions.CommandSyntaxException
    {
        ResourceLocation id = ResourceLocationArgument.getId(c, "program");
        String text = JoeNetwork.readDatapackProgram(c.getSource().getLevel(), id);
        if (text == null)
        {
            c.getSource().sendFailure(Component.literal("No Joe program called " + id));
            return 0;
        }
        // a book and quill you can edit, one page per ~12 lines
        List<Filterable<String>> pages = new ArrayList<>();
        StringBuilder page = new StringBuilder();
        int lines = 0;
        for (String line : text.split("\n"))
        {
            if (lines >= 12 || page.length() + line.length() > 900)
            {
                pages.add(Filterable.passThrough(page.toString()));
                page = new StringBuilder();
                lines = 0;
            }
            page.append(line).append('\n');
            lines++;
        }
        pages.add(Filterable.passThrough(page.toString()));
        ItemStack book = new ItemStack(Items.WRITABLE_BOOK);
        book.set(DataComponents.WRITABLE_BOOK_CONTENT, new WritableBookContent(pages));
        book.set(DataComponents.CUSTOM_NAME, Component.literal("Joe program: " + id.getPath()));
        c.getSource().getPlayerOrException().getInventory().placeItemBackInInventory(book);
        return 1;
    }

    private static int manual(CommandContext<CommandSourceStack> c) throws com.mojang.brigadier.exceptions.CommandSyntaxException
    {
        c.getSource().getPlayerOrException().getInventory().placeItemBackInInventory(manualBook());
        return 1;
    }

    /** The Seegson Working Joe programming manual (also crafted: book + processor). */
    public static ItemStack manualBook()
    {
        String[] pages = {
                "SEEGSON\nWORKING JOE\nJOE-SCRIPT v2\n\nWrite a program in a book and quill (or a Seegson Terminal), one statement per line. Use the book on your Joe.\n\nPrograms loop from the top.",
                "PLACES\nX Y Z, or ~ = where the Joe was when loaded: ~ ~ ~5\n\nname Joe-Lab\nhome ~ ~ ~\ngoto X Y Z\ngoto home|alarm|owner\npatrol A ; B ; C\nface X Y Z\nfollow [secs]",
                "VARIABLES\nset n = 5\nset n = n * 2 + 1\nset s = \"Hi \" + name\n$n puts a value in any line:\ndig $fx $fy $fz\nsay \"I have $n\"\n\nmaths: + - * / %\n== != < > <= >=\nand or not",
                "BLOCKS\nif count(wheat) > 9\n  ...\nelif night\n  ...\nelse\n  ...\nend\nwhile not full\nrepeat 4\nfor i = 1 to 10 step 2\nforever\nbreak  continue",
                "FUNCTIONS\nfunc restock\n  take ~2 ~ ~ iron_ingot 16\n  return\nend\ncall restock\n\nOne-liners:\nif night: say \"Hi\"\nunless full: wait 5\nlabel / jump / stop",
                "HANDS\ndig X Y Z\nplace ITEM X Y Z\nuse X Y Z [ITEM]\ntake X Y Z [ITEM] [N]\nput X Y Z [ITEM] [N]\ndrop [ITEM] [N]\npickup [radius]\nequip ITEM|nothing\ncraft ITEM [N]\nattack [radius]",
                "SENSES\nscan iron_ore 16\nfind hostile 12\n(sets found, fx fy fz)\ninspect X Y Z\n(sets block)\n\nValues: x y z charge health time night day full empty items alarm hostile player msg sender",
                "BUILT-IN\ncount(item) items(X,Y,Z,item)\nblock(X,Y,Z) ripe(X,Y,Z)\npowered(X,Y,Z) dist(X,Y,Z)\nisair light random(n)\nchance(%) min max abs\nfloor round sqrt len\ncontains word upgrade(id)",
                "WORK\nhaul A -> B [item] [n]\nfarm A B [-> store]\nmaintain A B [<- store]\nlever X Y Z on|off\npress open close\nguard X Y Z [r] [secs]\ncharge\nsecurity on|off",
                "TALK\nsay \"...\"  report \"...\"\nprint \"...\" (terminal log)\nalarm\nsend CHANNEL \"text\"\n(to all your Joes)\n\nRESPONSES\nwhen EVENT: statement\nwhen message ops: call f",
                "EVENTS\nnight day alarm hurt lowpower hostile player\nredstone X Y Z\nmessage [channel]\n\nMODULES (4 bays)\ncargo battery drill fabricator sensor plating servos anchor\nShears + sneak: remove",
                "NETWORK\nSeegson Terminal: edit, upload, watch, drive.\nNetwork Server: library, control at any distance, any dimension.\nTablet: the terminal in your pocket.\n/joes list recall lockdown resume stop run example" };
        List<Filterable<Component>> list = new ArrayList<>();
        for (String p : pages)
        {
            list.add(Filterable.passThrough(Component.literal(p)));
        }
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        book.set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(Filterable.passThrough("Working Joe Manual"), "Seegson", 0, list, true));
        return book;
    }
}
