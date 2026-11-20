package org.avp.entity.joe;

import net.minecraft.world.item.ItemStack;

/** Reading a program out of a book and quill or written book. */
public final class JoeBooks
{
    private JoeBooks()
    {
    }

    public static String text(ItemStack stack)
    {
        return WorkingJoeEntity.bookText(stack);
    }
}
