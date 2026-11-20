package org.avp.entity.joe;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import org.avp.registry.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

/** A Working Joe upgrade module: use it on your Joe to fit it (4 bays; sneak-use shears on the Joe to take them all out). */
public class JoeUpgradeItem extends Item
{
    public static final int SLOTS = 4;

    public enum Kind
    {
        CARGO("cargo", 2, "+9 inventory slots"),
        BATTERY("battery", 2, "+100% battery"),
        DRILL("drill", 1, "digs 5x faster; mines anything with full drops"),
        FABRICATOR("fabricator", 1, "lets it craft (craft ITEM COUNT)"),
        SENSOR("sensor", 1, "scan/find/pickup range 12 -> 48"),
        PLATING("plating", 1, "+10 armour, +20 health"),
        SERVOS("servos", 1, "+35% speed"),
        ANCHOR("anchor", 1, "keeps its chunk loaded: works anywhere, any distance");

        public final String id;
        public final int max;
        public final String effect;

        Kind(String id, int max, String effect)
        {
            this.id = id;
            this.max = max;
            this.effect = effect;
        }

        public Item item()
        {
            return ModItems.JOE_UPGRADES.get(this).get();
        }

        @Nullable
        public static Kind byId(String id)
        {
            for (Kind k : values())
            {
                if (k.id.equals(id))
                {
                    return k;
                }
            }
            return null;
        }

        public String itemName()
        {
            return "joe_module_" + name().toLowerCase(Locale.ROOT);
        }
    }

    public final Kind kind;

    public JoeUpgradeItem(Kind kind, Properties properties)
    {
        super(properties);
        this.kind = kind;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.literal(kind.effect).withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.literal("Use on a Working Joe to install (max " + kind.max + ")").withStyle(ChatFormatting.GRAY));
    }
}
