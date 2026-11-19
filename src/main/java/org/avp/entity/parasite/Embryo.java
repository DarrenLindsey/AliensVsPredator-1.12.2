package org.avp.entity.parasite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * A Xenomorph embryo growing inside a host (the 1.12.2 Organism capability + Embryo class).
 * Stored on the host as a data attachment (ModAttachments.EMBRYO), so it's saved with the
 * player or mob. When age reaches gestation, it bursts out (EmbryoEvents).
 *
 * @param age       ticks since it was implanted
 * @param gestation ticks it needs to grow
 * @param kind      what will emerge (only "standard" until the other Xenomorph types are ported)
 */
public record Embryo(int age, int gestation, String kind)
{
    public static final Codec<Embryo> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("age").forGetter(Embryo::age),
            Codec.INT.fieldOf("gestation").forGetter(Embryo::gestation),
            Codec.STRING.optionalFieldOf("kind", "standard").forGetter(Embryo::kind)
    ).apply(i, Embryo::new));

    public Embryo older()
    {
        return new Embryo(age + 1, gestation, kind);
    }

    /** 0..1 */
    public float progress()
    {
        return gestation <= 0 ? 1 : Math.min(1F, (float) age / gestation);
    }

    /** In the last tenth of gestation the host feels it (and surgery gets risky). */
    public boolean isLate()
    {
        return age >= gestation - gestation / 10;
    }
}
