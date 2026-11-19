package org.avp.registry;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Sound events. The .ogg files and their names are in assets/avp/sounds.json. */
public final class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, AliensVsPredator.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_OPEN = sound("blocks.blastdoor.open");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_CLOSE = sound("blocks.blastdoor.close");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_LOCK = sound("blocks.blastdoor.lock");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_UNLOCK = sound("blocks.blastdoor.unlock");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_PASS_DENY = sound("blocks.blastdoor.pass_deny");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_SEVASTOPOL_OPEN = sound("blocks.blastdoor.sevastopol.open");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLASTDOOR_SEVASTOPOL_CLOSE = sound("blocks.blastdoor.sevastopol.close");

    public static final DeferredHolder<SoundEvent, SoundEvent> FACEHUGGER_LIVING = sound("mobs.facehugger.living");
    public static final DeferredHolder<SoundEvent, SoundEvent> FACEHUGGER_HURT = sound("mobs.facehugger.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> FACEHUGGER_DEATH = sound("mobs.facehugger.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> FACEHUGGER_IMPLANT = sound("mobs.facehugger.implant");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHESTBURSTER_BURST = sound("mobs.chestburster.burst");
    public static final DeferredHolder<SoundEvent, SoundEvent> TURRET_FIRE = sound("blocks.turret.fire");

    private static DeferredHolder<SoundEvent, SoundEvent> sound(String name)
    {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, name)));
    }

    private ModSounds()
    {
    }
}
