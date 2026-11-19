package org.avp.client.render;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.avp.AliensVsPredator;
import org.avp.client.model.GeneratedModel;
import org.avp.client.model.geometry.AethonHeadModel;
import org.avp.client.model.geometry.AmpuleModel;
import org.avp.client.model.geometry.BiomaskBerserkerModel;
import org.avp.client.model.geometry.BiomaskClassicModel;
import org.avp.client.model.geometry.BiomaskFalconerModel;
import org.avp.client.model.geometry.BiomaskTrackerModel;
import org.avp.client.model.geometry.EngineerModel;
import org.avp.client.model.geometry.GigerAlienHeadModel;
import org.avp.client.model.geometry.GroundFernModel;
import org.avp.client.model.geometry.LockerModel;
import org.avp.client.model.geometry.SupplyChuteModel;
import org.avp.block.StorageBlock;
import org.avp.client.model.geometry.NeomorphSkullModel;
import org.avp.client.model.geometry.ProtomorphSkullModel;
import org.avp.client.model.geometry.QueenSkullModel;
import org.avp.client.model.geometry.TreeFernModel;
import org.avp.client.model.geometry.WarriorModel;
import org.avp.client.model.geometry.YautjaModel;
import org.avp.registry.ModBlocks;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

/**
 * How each model block is drawn: which converted model, which texture, which
 * parts of the model, and the scale/offset. The numbers come straight from the
 * 1.12.2 classes (BlockSkull*.preRenderTransforms / getSkullModelRenderers).
 */
public final class ModelBlockRenderInfo
{
    /** How the old 1.12.2 renderer placed the model. */
    public enum Kind
    {
        /** Skulls etc.: scaled to Info.size and stood on the floor. */
        TROPHY,
        /** Ferns: random size/colour/turn per position. */
        PLANT,
        /** Machines/furniture: the old TESR transform as-is (translate, flip, face the player). */
        PROP
    }

    /** Sets animated parts (e.g. a door angle) before drawing. */
    public interface Poser
    {
        void pose(GeneratedModel model, net.minecraft.world.level.block.state.BlockState state);
    }

    /**
     * @param layer   the converted model's geometry
     * @param factory wraps the baked geometry so parts can be found by name
     * @param texture the texture file
     * @param parts   which parts to draw (empty = the whole model)
     * @param scale   old preRenderTransforms scale
     * @param x,y,z   old preRenderTransforms translate
     * @param turnY   extra turn around the vertical axis, in degrees
     * @param kind    how the model is placed (see Kind)
     * @param size    trophies only: how big the placed trophy is, in blocks (its longest side).
     *                The renderer scales it to this size and stands it on the floor of the block.
     */
    public record Info(ModelLayerLocation layer, Function<ModelPart, GeneratedModel> factory, ResourceLocation texture,
                       List<String> parts, float scale, float x, float y, float z, float turnY, Kind kind, float size, Poser poser)
    {
    }

    private static final Map<Block, Info> INFO = new IdentityHashMap<>();
    private static final Map<ModelLayerLocation, GeneratedModel> BAKED = new java.util.HashMap<>();
    private static EntityModelSet bakedFrom;

    private static void add(Supplier<? extends Block> block, Info info)
    {
        INFO.put(block.get(), info);
    }

    private static ResourceLocation tex(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/" + path);
    }

    private static Info trophy(ModelLayerLocation layer, Function<ModelPart, GeneratedModel> factory, String texture, List<String> parts,
                               float scale, float x, float y, float z, float turnY, float size)
    {
        return new Info(layer, factory, tex(texture), parts, scale, x, y, z, turnY, Kind.TROPHY, size, null);
    }

    /** Called once on the client after blocks are registered. */
    public static void init()
    {
        List<String> engineerParts = List.of("head1", "head2", "hose", "rJaw", "lJaw", "nozzle1", "nozzle2", "nozzle3a", "nozzle3b", "nozzle3c", "nozzle3d");
        List<String> xenoParts = List.of("headBase", "headSpine1", "headSpine2", "headTop", "lHead", "rHead", "jaw", "jaw2");

        // the 1.12.2 BlockSkull default transform was scale 3.0, translate (0, 0.05, 0)
        add(ModBlocks.SKULL_ENGINEER, trophy(EngineerModel.LAYER, EngineerModel::new, "mob/engineer_bio.png", engineerParts, 3.0F, 0, 0.05F, 0, 0, 0.8F));
        add(ModBlocks.SKULL_SPACEJOCKEY, trophy(EngineerModel.LAYER, EngineerModel::new, "mob/engineer.png", engineerParts, 3.0F, 0, 0.05F, 0, 0, 0.9F));
        add(ModBlocks.SKULL_XENOMORPH, trophy(WarriorModel.LAYER, WarriorModel::new, "mob/xenomorphskullgiger.png", xenoParts, 1.5F, 0, 0.115F, 0, 0, 0.95F));
        add(ModBlocks.SKULL_XENOMORPH_WARRIOR, trophy(WarriorModel.LAYER, WarriorModel::new, "mob/xenomorphskullwarrior.png", xenoParts, 1.5F, 0, 0.115F, 0, 0, 0.95F));
        add(ModBlocks.SKULL_YAUTJA, trophy(YautjaModel.LAYER, YautjaModel::new, "mob/yautjaskull.png", List.of("face"), 1.9F, 0, 0.25F, 0, 0, 0.6F));
        add(ModBlocks.SKULL_MATRIARCH, trophy(QueenSkullModel.LAYER, QueenSkullModel::new, "tile/skulls/queenskull.png", List.of("head0"), 1.9F, 0, -1.35F, -0.25F, 0, 1.3F));
        add(ModBlocks.SKULL_PROTOMORPH, trophy(ProtomorphSkullModel.LAYER, ProtomorphSkullModel::new, "tile/skulls/protomorph_skull.png", List.of("head1"), 1.9F, 0, -1.5F, 0, 0, 0.8F));
        add(ModBlocks.SKULL_NEOMORPH, trophy(NeomorphSkullModel.LAYER, NeomorphSkullModel::new, "tile/skulls/neomorph_skull.png", List.of("head"), 1.9F, 0, -1.5F, 0, 0, 0.8F));
        add(ModBlocks.BIOMASK_CLASSIC, trophy(BiomaskClassicModel.LAYER, BiomaskClassicModel::new, "mob/yautja/yautja.png", List.of("biomaskMouth"), 1.9F, 0, -1.3F, 0, 0, 0.6F));
        add(ModBlocks.BIOMASK_BERSERKER, trophy(BiomaskBerserkerModel.LAYER, BiomaskBerserkerModel::new, "mob/yautja/yautja_berserker.png", List.of("biomaskMouth"), 1.9F, 0, -1.3F, 0, 0, 0.6F));
        add(ModBlocks.BIOMASK_FALCONER, trophy(BiomaskFalconerModel.LAYER, BiomaskFalconerModel::new, "mob/yautja/yautja_falconer.png", List.of("biomaskMouth"), 1.9F, 0, -1.3F, 0, 0, 0.6F));
        add(ModBlocks.BIOMASK_TRACKER, trophy(BiomaskTrackerModel.LAYER, BiomaskTrackerModel::new, "mob/yautja/yautja_tracker.png", List.of("biomaskMouth"), 1.9F, 0, -1.3F, 0, 0, 0.6F));
        add(ModBlocks.HEAD_AETHON, trophy(AethonHeadModel.LAYER, AethonHeadModel::new, "mob/aethon/aethon.png", List.of("head"), 1.9F, 0, -1.5F, 0, 0, 0.8F));
        add(ModBlocks.HEAD_GIGERALIEN, trophy(GigerAlienHeadModel.LAYER, GigerAlienHeadModel::new, "mob/gigeralien.png", List.of("head"), 1.0F, 0, -1.25F, -0.25F, 90, 0.9F));

        add(ModBlocks.PARADISIAN_GROUND_FERN, new Info(GroundFernModel.LAYER, GroundFernModel::new, tex("tile/plants/paradisiangroundfern.png"), List.of(), 1, 0, 1.455F, 0, 0, Kind.PLANT, 1, null));
        add(ModBlocks.PARADISIAN_TREE_FERN, new Info(TreeFernModel.LAYER, TreeFernModel::new, tex("tile/plants/paradisiantreefern.png"), List.of(), 1, 0, 1.455F, 0, 0, Kind.PLANT, 1, null));

        // ampule: 1.12.2 RenderAmpule (translate y 0.955, scale 0.64, flipped, faces the player)
        add(ModBlocks.ENGINEERSHIP_AMPULE, new Info(AmpuleModel.LAYER, AmpuleModel::new, tex("tile/ampule.png"), List.of(), 0.64F, 0, 0.955F, 0, 0, Kind.PROP, 1, null));

        // lockers: 1.12.2 RenderLocker/RenderGunLocker (translate y 1.41, scale 0.95, door swings -1.5 rad when open)
        Poser lockerDoor = (model, state) -> model.parts().get("door").yRot =
                state.hasProperty(StorageBlock.VIEWED) && StorageBlock.isOpen(state) ? -1.5F : 0F;
        add(ModBlocks.LOCKER, new Info(LockerModel.LAYER, LockerModel::new, tex("tile/locker.png"), List.of(), 0.95F, 0, 1.41F, 0, 0, Kind.PROP, 1, lockerDoor));
        add(ModBlocks.GUNLOCKER, new Info(LockerModel.LAYER, LockerModel::new, tex("tile/gunlocker.png"), List.of(), 0.95F, 0, 1.41F, 0, 0, Kind.PROP, 1, lockerDoor));
        // supply crates: 1.12.2 RenderSupplyCrate draws only the crate parts of the supply chute model
        List<String> crateParts = List.of("casing", "lHinge", "rHinge", "lid");
        add(ModBlocks.SUPPLYCRATE, new Info(SupplyChuteModel.LAYER, SupplyChuteModel::new, tex("tile/supplychute.png"), crateParts, 1F, 0, 1.5F, 0, 0, Kind.PROP, 1, null));
        add(ModBlocks.SUPPLYCRATE_MARINES, new Info(SupplyChuteModel.LAYER, SupplyChuteModel::new, tex("tile/supplychute.marines.png"), crateParts, 1F, 0, 1.5F, 0, 0, Kind.PROP, 1, null));
        add(ModBlocks.SUPPLYCRATE_SEEGSON, new Info(SupplyChuteModel.LAYER, SupplyChuteModel::new, tex("tile/supplychute.seegson.png"), crateParts, 1F, 0, 1.5F, 0, 0, Kind.PROP, 1, null));
    }

    @Nullable
    public static Info get(Block block)
    {
        return INFO.get(block);
    }

    /** The baked model for a layer. Re-baked automatically after a resource reload (F3+T). */
    public static GeneratedModel model(Info info, EntityModelSet models)
    {
        if (bakedFrom != models)
        {
            BAKED.clear();
            bakedFrom = models;
        }
        return BAKED.computeIfAbsent(info.layer(), layer -> info.factory().apply(models.bakeLayer(layer)));
    }

    private ModelBlockRenderInfo()
    {
    }
}
