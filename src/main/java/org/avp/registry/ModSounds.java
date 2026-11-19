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
    public static final DeferredHolder<SoundEvent, SoundEvent> CHESTBURSTER_HURT = sound("mobs.chestburster.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHESTBURSTER_DEATH = sound("mobs.chestburster.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHESTBURSTER_ATTACK = sound("mobs.chestburster.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHESTBURSTER_GROW = sound("mobs.chestburster.grow");
    public static final DeferredHolder<SoundEvent, SoundEvent> OVAMORPH_LIVING = sound("mobs.ovamorph.living");
    public static final DeferredHolder<SoundEvent, SoundEvent> OVAMORPH_OPEN = sound("mobs.ovamorph.open");
    public static final DeferredHolder<SoundEvent, SoundEvent> ALIEN_LIVING = sound("mobs.alien.living");
    public static final DeferredHolder<SoundEvent, SoundEvent> ALIEN_HURT = sound("mobs.alien.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ALIEN_DEATH = sound("mobs.alien.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ALIEN_ATTACK = sound("mobs.alien.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> TURRET_FIRE = sound("blocks.turret.fire");

    public static final DeferredHolder<SoundEvent, SoundEvent> PULSE_RIFLE = sound("weapons.pulserifle");

    /** Sound sets of the catalog creatures (CreatureSpec.sounds): "set.event" -> sound. */
    private static final java.util.Map<String, DeferredHolder<SoundEvent, SoundEvent>> CREATURE = new java.util.HashMap<>();

    static
    {
        creatureSounds("praetorian", "living", "hurt", "death");
        creatureSounds("queen", "living", "hurt", "death");
        creatureSounds("predalien", "living", "hurt", "death", "attack");
        creatureSounds("prequelmorph", "living", "hurt", "death", "attack");
        creatureSounds("predator", "living", "hurt", "death");
        creatureSounds("marine", "hurt", "death");
    }

    private static void creatureSounds(String set, String... events)
    {
        for (String event : events)
        {
            CREATURE.put(set + "." + event, sound("mobs." + set + "." + event));
        }
    }

    /** A creature's sound: its own set, else the shared alien/chestburster/facehugger sounds, else null. */
    @javax.annotation.Nullable
    public static SoundEvent creature(String set, String event)
    {
        DeferredHolder<SoundEvent, SoundEvent> holder = CREATURE.get(set + "." + event);
        if (holder != null)
        {
            return holder.get();
        }
        return switch (set + "." + event)
        {
            case "alien.living" -> ALIEN_LIVING.get();
            case "alien.hurt" -> ALIEN_HURT.get();
            case "alien.death" -> ALIEN_DEATH.get();
            case "alien.attack" -> ALIEN_ATTACK.get();
            case "chestburster.hurt" -> CHESTBURSTER_HURT.get();
            case "chestburster.death" -> CHESTBURSTER_DEATH.get();
            case "chestburster.attack" -> CHESTBURSTER_ATTACK.get();
            case "facehugger.living" -> FACEHUGGER_LIVING.get();
            case "facehugger.hurt" -> FACEHUGGER_HURT.get();
            case "facehugger.death" -> FACEHUGGER_DEATH.get();
            default -> null;
        };
    }

    private static DeferredHolder<SoundEvent, SoundEvent> sound(String name)
    {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, name)));
    }

    private ModSounds()
    {
    }
}
