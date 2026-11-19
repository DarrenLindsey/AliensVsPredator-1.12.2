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

    private ModAttachments()
    {
    }
}
