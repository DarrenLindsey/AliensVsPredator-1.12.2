package org.avp.item.firearm;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;

/**
 * A gun's characteristics (1.12.2 FirearmProfiles). Magazine size, fire rate, recoil and reload
 * time are the 1.12.2 values; damage per round is new (1.12.2 dealt 1-2 per round, far too little).
 *
 * @param model       converted model class (org.avp.client.model.geometry), for the item renderer
 * @param texture     model texture under textures/item/models/
 * @param soundLength seconds one firing sound covers (fast guns play it once per burst, not per round)
 * @param twoHanded   held with both hands (rifles); pistols are one-handed
 */
public record Firearm(String id, AmmoType ammo, int magazine, double roundsPerMinute, float damage, float recoil, float spread,
        int reloadTicks, Supplier<SoundEvent> sound, double soundLength, double range, String model, String texture, boolean twoHanded)
{
    /** Rounds fired by the time the trigger has been held for this many ticks (the first fires at once). */
    public int roundsBy(int ticksHeld)
    {
        return (int) Math.floor(ticksHeld * roundsPerMinute / 1200.0) + 1;
    }
}
