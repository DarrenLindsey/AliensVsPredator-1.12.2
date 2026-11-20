package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.entity.parasite.Embryo;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Data attachments: extra data NeoForge stores on entities (the 1.12.2 "capabilities" on
 * entities). Saved with the entity automatically.
 */
public final class ModAttachments
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AliensVsPredator.MOD_ID);

    /** A Xenomorph embryo inside a host. Only present while the host carries one (use hasData). */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Embryo>> EMBRYO = ATTACHMENTS.register("embryo",
            () -> AttachmentType.builder(() -> new Embryo(0, 6000, "standard")).serialize(Embryo.CODEC).build());

    /** A host cocooned in a hive: ticks it's been there (it turns into an egg in time - "eggmorphing"). */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> COCOONED = ATTACHMENTS.register("cocooned",
            () -> AttachmentType.builder(() -> 0).serialize(com.mojang.serialization.Codec.INT).build());

    /** Where a cocooned player is held (they can't move off it until they struggle free). */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<net.minecraft.core.BlockPos>> COCOON_POS = ATTACHMENTS.register("cocoon_pos",
            () -> AttachmentType.builder(() -> net.minecraft.core.BlockPos.ZERO).serialize(net.minecraft.core.BlockPos.CODEC).build());

    /** How far a cocooned player has struggled (0..100). Not saved: logging out doesn't loosen the resin. */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> STRUGGLE = ATTACHMENTS.register("struggle",
            () -> AttachmentType.builder(() -> 0).build());

    private ModAttachments()
    {
    }
}
