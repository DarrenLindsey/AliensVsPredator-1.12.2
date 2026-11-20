package org.avp.entity.joe;

import javax.annotation.Nullable;

import org.avp.apollo.ApolloCoreBlockEntity;
import org.avp.entity.joe.WorkingJoeEntity.Task;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * "courier": takes one delivery job from its owner's APOLLO core, collects the items there and walks
 * them to the player who asked. "store": takes everything it carries to APOLLO.
 */
final class Courier implements Task
{
    private final WorkingJoeEntity joe;
    private final ApolloCoreBlockEntity core;
    private final ApolloCoreBlockEntity.Job job;
    private final WorkingJoeEntity.Walker walker;
    private boolean loaded;
    private int timeout = 20 * 60 * 5;

    private Courier(WorkingJoeEntity joe, ApolloCoreBlockEntity core, ApolloCoreBlockEntity.Job job)
    {
        this.joe = joe;
        this.core = core;
        this.job = job;
        this.walker = joe.new Walker();
    }

    @Nullable
    private static ApolloCoreBlockEntity core(WorkingJoeEntity joe)
    {
        if (joe.owner() == null || !(joe.level() instanceof ServerLevel level))
        {
            return null;
        }
        ApolloCoreBlockEntity core = ApolloCoreBlockEntity.of(level.getServer(), joe.owner());
        return core != null && core.getLevel() == joe.level() && core.getBlockPos().closerThan(joe.blockPosition(), 256) ? core : null;
    }

    @Nullable
    static Task start(WorkingJoeEntity joe)
    {
        ApolloCoreBlockEntity core = core(joe);
        if (core == null)
        {
            joe.log("Courier: no APOLLO core in reach.");
            return joe.new WaitTask(100);
        }
        ApolloCoreBlockEntity.Job job = core.nextJob();
        if (job == null)
        {
            return joe.new WaitTask(40); // nothing to do: check again shortly
        }
        joe.log("Courier: " + job.count() + " x " + job.item().getHoverName().getString());
        return new Courier(joe, core, job);
    }

    @Nullable
    static Task storeAll(WorkingJoeEntity joe)
    {
        ApolloCoreBlockEntity core = core(joe);
        if (core == null)
        {
            joe.log("Store: no APOLLO core in reach.");
            return null;
        }
        WorkingJoeEntity.Walker walker = joe.new Walker();
        return new Task()
        {
            @Override
            public boolean tick()
            {
                if (!walker.reached(core.getBlockPos(), 2.5))
                {
                    return false;
                }
                for (int i = 0; i < joe.inventory.getContainerSize(); i++)
                {
                    ItemStack s = joe.inventory.getItem(i);
                    if (!s.isEmpty())
                    {
                        joe.inventory.setItem(i, core.store(s, false));
                    }
                }
                joe.swing(InteractionHand.MAIN_HAND);
                return true;
            }

            @Override
            public String describe()
            {
                return "storing at APOLLO";
            }
        };
    }

    @Override
    public boolean tick()
    {
        if (--timeout <= 0)
        {
            joe.report("Delivery abandoned: recipient unreachable.");
            giveBack();
            return true;
        }
        if (!loaded)
        {
            if (!walker.reached(core.getBlockPos(), 2.5))
            {
                return false;
            }
            int left = job.count();
            while (left > 0 && joe.hasRoom())
            {
                ItemStack got = core.take(job.item(), left, false);
                if (got.isEmpty())
                {
                    break;
                }
                left -= got.getCount();
                ItemStack over = joe.store(got);
                if (!over.isEmpty())
                {
                    core.store(over, false);
                    break;
                }
            }
            loaded = true;
            joe.swing(InteractionHand.MAIN_HAND);
            if (left == job.count())
            {
                joe.log("Courier: APOLLO had none.");
                return true;
            }
        }
        Player player = joe.level().getPlayerByUUID(job.player());
        if (player == null)
        {
            return false; // wait for them to come back (or the timeout)
        }
        BlockPos at = player.blockPosition();
        if (!walker.reached(at, 2.5))
        {
            if (walker.failed)
            {
                walker.failed = false;
            }
            return false;
        }
        int count = job.count();
        for (int i = 0; i < joe.inventory.getContainerSize() && count > 0; i++)
        {
            ItemStack s = joe.inventory.getItem(i);
            if (ItemStack.isSameItemSameComponents(s, job.item()))
            {
                int n = Math.min(count, s.getCount());
                ItemStack give = s.split(n);
                count -= n;
                if (!player.getInventory().add(give))
                {
                    player.drop(give, false);
                }
            }
        }
        joe.swing(InteractionHand.MAIN_HAND);
        joe.say("Your delivery, " + player.getName().getString() + ".");
        return true;
    }

    private void giveBack()
    {
        for (int i = 0; i < joe.inventory.getContainerSize(); i++)
        {
            ItemStack s = joe.inventory.getItem(i);
            if (ItemStack.isSameItemSameComponents(s, job.item()))
            {
                joe.inventory.setItem(i, core.store(s, false));
            }
        }
    }

    @Override
    public String describe()
    {
        return loaded ? "delivering " + job.item().getHoverName().getString() : "collecting from APOLLO";
    }
}
