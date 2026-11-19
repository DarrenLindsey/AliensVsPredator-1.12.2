package org.avp.client.model.geometry;

import java.util.LinkedHashMap;
import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.client.model.GeneratedModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

/**
 * Geometry converted automatically from the 1.12.2 ModelSniper by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class SniperModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "sniper"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart barrel;
    public final ModelPart clipHolder1;
    public final ModelPart clipHolder2;
    public final ModelPart base1;
    public final ModelPart base2;
    public final ModelPart clip;
    public final ModelPart handle;
    public final ModelPart triggerGuard;
    public final ModelPart trigger;
    public final ModelPart stock1;
    public final ModelPart stock2;
    public final ModelPart stockEnd;
    public final ModelPart barrelSupport;
    public final ModelPart scopeSupport;
    public final ModelPart scopeBase;
    public final ModelPart scope;
    public final ModelPart scopeFirstPerson;

    public SniperModel(ModelPart root)
    {
        this.root = root;
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.clipHolder1 = root.getChild("clipHolder1");
        this.parts.put("clipHolder1", this.clipHolder1);
        this.clipHolder2 = root.getChild("clipHolder2");
        this.parts.put("clipHolder2", this.clipHolder2);
        this.base1 = root.getChild("base1");
        this.parts.put("base1", this.base1);
        this.base2 = root.getChild("base2");
        this.parts.put("base2", this.base2);
        this.clip = root.getChild("clip");
        this.parts.put("clip", this.clip);
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.triggerGuard = root.getChild("triggerGuard");
        this.parts.put("triggerGuard", this.triggerGuard);
        this.trigger = root.getChild("trigger");
        this.parts.put("trigger", this.trigger);
        this.stock1 = root.getChild("stock1");
        this.parts.put("stock1", this.stock1);
        this.stock2 = root.getChild("stock2");
        this.parts.put("stock2", this.stock2);
        this.stockEnd = root.getChild("stockEnd");
        this.parts.put("stockEnd", this.stockEnd);
        this.barrelSupport = root.getChild("barrelSupport");
        this.parts.put("barrelSupport", this.barrelSupport);
        this.scopeSupport = root.getChild("scopeSupport");
        this.parts.put("scopeSupport", this.scopeSupport);
        this.scopeBase = root.getChild("scopeBase");
        this.parts.put("scopeBase", this.scopeBase);
        this.scope = root.getChild("scope");
        this.parts.put("scope", this.scope);
        this.scopeFirstPerson = root.getChild("scopeFirstPerson");
        this.parts.put("scopeFirstPerson", this.scopeFirstPerson);
    }

    @Override
    public ModelPart root()
    {
        return root;
    }

    @Override
    public Map<String, ModelPart> parts()
    {
        return parts;
    }

    public static LayerDefinition createLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_barrel = root.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.6666667F, 0.0F, 1.0F, 1.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_clipHolder1 = root.addOrReplaceChild("clipHolder1",
                CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, 2.0F, -1.6F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_clipHolder2 = root.addOrReplaceChild("clipHolder2",
                CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 1.7F, -2.7F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, 0.0F));
        PartDefinition p_base1 = root.addOrReplaceChild("base1",
                CubeListBuilder.create().texOffs(40, 8).addBox(-0.5F, 0.4F, -5.6F, 2.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_base2 = root.addOrReplaceChild("base2",
                CubeListBuilder.create().texOffs(5, 25).addBox(-0.5F, -2.2F, -5.6F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.445059F, 0.0F, 0.0F));
        PartDefinition p_clip = root.addOrReplaceChild("clip",
                CubeListBuilder.create().texOffs(16, 22).addBox(0.0F, 2.3F, -2.4F, 1.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1919862F, 0.0F, 0.0F));
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(17, 17).addBox(0.0F, 1.5F, -4.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.3665191F, 0.0F, 0.0F));
        PartDefinition p_triggerGuard = root.addOrReplaceChild("triggerGuard",
                CubeListBuilder.create().texOffs(7, 21).addBox(0.0F, 3.3F, -4.9F, 1.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_trigger = root.addOrReplaceChild("trigger",
                CubeListBuilder.create().texOffs(0, 28).addBox(0.5F, 2.3F, -4.5F, 0.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_stock1 = root.addOrReplaceChild("stock1",
                CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, 0.6F, -11.7F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_stock2 = root.addOrReplaceChild("stock2",
                CubeListBuilder.create().texOffs(23, 8).addBox(0.0F, 0.6F, -11.0F, 1.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0942478F, 0.0F, 0.0F));
        PartDefinition p_stockEnd = root.addOrReplaceChild("stockEnd",
                CubeListBuilder.create().texOffs(17, 12).addBox(0.0F, 0.4F, -11.7F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_barrelSupport = root.addOrReplaceChild("barrelSupport",
                CubeListBuilder.create().texOffs(23, 0).addBox(0.5F, 0.0F, 0.9F, 0.0F, 1.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_scopeSupport = root.addOrReplaceChild("scopeSupport",
                CubeListBuilder.create().texOffs(5, 28).addBox(0.5F, -0.3F, -3.366667F, 0.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_scopeBase = root.addOrReplaceChild("scopeBase",
                CubeListBuilder.create().texOffs(36, 0).addBox(0.0F, 0.0F, -5.1F, 1.0F, 1.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_scope = root.addOrReplaceChild("scope",
                CubeListBuilder.create().texOffs(23, 16).addBox(0.0F, -1.3F, -5.5F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_scopeFirstPerson = root.addOrReplaceChild("scopeFirstPerson",
                CubeListBuilder.create().texOffs(28, 29).addBox(0.0F, -1.3F, -5.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
