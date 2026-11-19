package org.avp.entity.creature;

import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Everything about one creature in the catalog (Creatures): stats (from 1.12.2, see
 * tools/survey_creatures.py), family, abilities and how it's drawn.
 *
 * @param model    client model class name in org.avp.client.model.geometry (e.g. "WarriorModel")
 * @param texture  texture file under textures/entity/
 * @param scale    render scale (from the 1.12.2 renderer)
 * @param sounds   sound set: "alien", "praetorian", "queen", "predalien", "prequelmorph", "predator", "marine", "chestburster", "facehugger" or "" for vanilla-ish
 */
public record CreatureSpec(String id, Family family, double health, double damage, double speed, double armor,
        float width, float height, int xp, Set<Ability> abilities, @Nullable String matureInto, @Nullable String embryo,
        boolean hostile, String model, String texture, float scale, String sounds, int eggPrimary, int eggSecondary, String lore)
{
    public boolean has(Ability ability)
    {
        return abilities.contains(ability);
    }

    public static Builder of(String id, Family family)
    {
        return new Builder(id, family);
    }

    public static final class Builder
    {
        private final String id;
        private final Family family;
        private double health = 20, damage = 2, speed = 0.3, armor;
        private float width = 0.8F, height = 1.8F;
        private int xp = 10;
        private final EnumSet<Ability> abilities = EnumSet.noneOf(Ability.class);
        private String matureInto, embryo, model, texture, sounds = "alien", lore = "";
        private boolean hostile = true;
        private float scale = 1;
        private int egg1 = 0x202020, egg2 = 0x505050;

        Builder(String id, Family family)
        {
            this.id = id;
            this.family = family;
        }

        /** health, attack damage, movement speed (modern units), armour */
        public Builder stats(double health, double damage, double speed, double armor)
        {
            this.health = health;
            this.damage = damage;
            this.speed = speed;
            this.armor = armor;
            return this;
        }

        public Builder size(float width, float height)
        {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder xp(int xp)
        {
            this.xp = xp;
            return this;
        }

        public Builder can(Ability... abilities)
        {
            this.abilities.addAll(java.util.List.of(abilities));
            return this;
        }

        public Builder matures(String into)
        {
            this.matureInto = into;
            return this;
        }

        /** Embryo kind it implants (parasites, biting implanters). */
        public Builder embryo(String kind)
        {
            this.embryo = kind;
            return this;
        }

        public Builder neutral()
        {
            this.hostile = false;
            return this;
        }

        public Builder look(String model, String texture, float scale)
        {
            this.model = model;
            this.texture = texture;
            this.scale = scale;
            return this;
        }

        public Builder sounds(String sounds)
        {
            this.sounds = sounds;
            return this;
        }

        public Builder egg(int primary, int secondary)
        {
            this.egg1 = primary;
            this.egg2 = secondary;
            return this;
        }

        public Builder lore(String lore)
        {
            this.lore = lore;
            return this;
        }

        public CreatureSpec build()
        {
            return new CreatureSpec(id, family, health, damage, speed, armor, width, height, xp, Set.copyOf(abilities.isEmpty()
                    ? EnumSet.noneOf(Ability.class) : abilities), matureInto, embryo, hostile, model, texture, scale, sounds, egg1, egg2, lore);
        }
    }
}
