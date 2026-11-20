package org.avp.item;

/** An item drawn with a converted 1.12.2 model by FirearmRenderer (guns, flamethrowers, the motion tracker...). */
public interface ModelledItem
{
    /** Model class name in org.avp.client.model.geometry. */
    String model();

    /** Texture file under textures/item/models/. */
    String texture();

    /** Held up in both hands (rifles) rather than one. */
    default boolean twoHanded()
    {
        return true;
    }
}
