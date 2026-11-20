package org.avp.entity.xenomorph.lab;

import org.avp.AliensVsPredator;
import org.avp.entity.xenomorph.XenomorphEntity;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModTags;

import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/** Dead Xenomorphs sometimes leave a usable DNA sample; hyper-acid pets' bites burn. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class XenoLabEvents
{
    private XenoLabEvents()
    {
    }

    @SubscribeEvent
    public static void drops(LivingDropsEvent event)
    {
        var dead = event.getEntity();
        if (dead.getType().is(ModTags.XENOMORPHS) && !(dead instanceof XenomorphEntity x && x.isBonded()) && dead.getRandom().nextInt(100) < 15)
        {
            event.getDrops().add(new ItemEntity(dead.level(), dead.getX(), dead.getY(), dead.getZ(), XenoLab.sample(dead.getType())));
        }
    }

    @SubscribeEvent
    public static void acidBite(LivingIncomingDamageEvent event)
    {
        if (event.getSource().getEntity() instanceof XenomorphEntity x && x.bond() != null && x.bond().has(XenoTrait.ACID)
                && !event.getSource().is(ModDamageTypes.ACID))
        {
            var target = event.getEntity();
            target.invulnerableTime = 0;
            target.hurt(ModDamageTypes.of(target.level(), ModDamageTypes.ACID), 3.0F);
        }
    }
}
