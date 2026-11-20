package org.avp.item.firearm;

import java.util.LinkedHashMap;
import java.util.Map;

import org.avp.registry.ModSounds;

/** Every gun (1.12.2 FirearmProfiles + ItemHandler). */
public final class Firearms
{
    public static final Map<String, Firearm> ALL = new LinkedHashMap<>();

    public static final Firearm PISTOL = add(new Firearm("gun_pistol", AmmoType.PISTOL, 12, 80, 6F, 2.0F, 1.0F, 120,
            ModSounds.PISTOL, 0, 64, "Gun88MOD4Model", "88mod4.png", false));
    public static final Firearm M4 = add(new Firearm("gun_m4", AmmoType.ASSAULT_RIFLE, 24, 800, 4F, 0.5F, 2.0F, 120,
            ModSounds.M4, 0.75, 96, "M4Model", "m4.png", true));
    public static final Firearm AK47 = add(new Firearm("gun_ak47", AmmoType.ASSAULT_RIFLE, 32, 600, 5F, 0.6F, 3.0F, 100,
            ModSounds.AK47, 0, 96, "AK47Model", "ak-47.png", true));
    public static final Firearm M41A = add(new Firearm("gun_m41a", AmmoType.ASSAULT_RIFLE, 99, 900, 4.5F, 0.5F, 2.0F, 120,
            ModSounds.PULSE_RIFLE, 0.9, 96, "M41AModel", "pulserifle.png", true));
    public static final Firearm M56SG = add(new Firearm("gun_m56sg", AmmoType.SMARTGUN, 500, 1200, 4F, 0.2F, 1.5F, 120,
            ModSounds.SMARTGUN, 0, 96, "M56SGModel", "m56sg.png", true));
    public static final Firearm SNIPER = add(new Firearm("gun_sniper", AmmoType.SNIPER, 6, 30, 22F, 1.5F, 0.0F, 150,
            ModSounds.SNIPER, 0, 192, "SniperModel", "sniper.png", true));

    private static Firearm add(Firearm firearm)
    {
        ALL.put(firearm.id(), firearm);
        return firearm;
    }

    private Firearms()
    {
    }
}
