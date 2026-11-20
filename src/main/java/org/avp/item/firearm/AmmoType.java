package org.avp.item.firearm;

/** Which magazines fit which guns (1.12.2 ItemFirearm.Classification). */
public enum AmmoType
{
    PISTOL("ammo_pistol"),
    ASSAULT_RIFLE("ammo_ar"),
    SMARTGUN("ammo_smg"),
    SNIPER("ammo_sniper");

    /** Item id of the magazine. */
    public final String magazine;

    AmmoType(String magazine)
    {
        this.magazine = magazine;
    }
}
