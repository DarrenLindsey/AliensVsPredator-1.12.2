package org.avp.client.model;

import java.util.Map;

import net.minecraft.client.model.geom.ModelPart;

/** Implemented by every model converted from 1.12.2 (see tools/convert_models.py). */
public interface GeneratedModel
{
    /** The whole model. */
    ModelPart root();

    /** Every part of the model by its old 1.12.2 name, e.g. "headBase". */
    Map<String, ModelPart> parts();
}
