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
 * Geometry converted automatically from the 1.12.2 ModelCryostasisTube by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class CryostasisTubeModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "cryostasis_tube"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart cap;
    public final ModelPart capN;
    public final ModelPart glassN;
    public final ModelPart capNE;
    public final ModelPart capE;
    public final ModelPart capSE;
    public final ModelPart supportBar;
    public final ModelPart capSW;
    public final ModelPart capW;
    public final ModelPart capNW;
    public final ModelPart glassNW;
    public final ModelPart glassW;
    public final ModelPart glassSW;
    public final ModelPart glassNE;
    public final ModelPart glassE;
    public final ModelPart glassSE;
    public final ModelPart baseBottom;
    public final ModelPart baseSW;
    public final ModelPart baseW;
    public final ModelPart baseNW;
    public final ModelPart baseN;
    public final ModelPart baseNE;
    public final ModelPart baseE;
    public final ModelPart baseSE;

    public CryostasisTubeModel(ModelPart root)
    {
        this.root = root;
        this.cap = root.getChild("cap");
        this.parts.put("cap", this.cap);
        this.capN = root.getChild("capN");
        this.parts.put("capN", this.capN);
        this.glassN = root.getChild("glassN");
        this.parts.put("glassN", this.glassN);
        this.capNE = root.getChild("capNE");
        this.parts.put("capNE", this.capNE);
        this.capE = root.getChild("capE");
        this.parts.put("capE", this.capE);
        this.capSE = root.getChild("capSE");
        this.parts.put("capSE", this.capSE);
        this.supportBar = root.getChild("supportBar");
        this.parts.put("supportBar", this.supportBar);
        this.capSW = root.getChild("capSW");
        this.parts.put("capSW", this.capSW);
        this.capW = root.getChild("capW");
        this.parts.put("capW", this.capW);
        this.capNW = root.getChild("capNW");
        this.parts.put("capNW", this.capNW);
        this.glassNW = root.getChild("glassNW");
        this.parts.put("glassNW", this.glassNW);
        this.glassW = root.getChild("glassW");
        this.parts.put("glassW", this.glassW);
        this.glassSW = root.getChild("glassSW");
        this.parts.put("glassSW", this.glassSW);
        this.glassNE = root.getChild("glassNE");
        this.parts.put("glassNE", this.glassNE);
        this.glassE = root.getChild("glassE");
        this.parts.put("glassE", this.glassE);
        this.glassSE = root.getChild("glassSE");
        this.parts.put("glassSE", this.glassSE);
        this.baseBottom = root.getChild("baseBottom");
        this.parts.put("baseBottom", this.baseBottom);
        this.baseSW = root.getChild("baseSW");
        this.parts.put("baseSW", this.baseSW);
        this.baseW = root.getChild("baseW");
        this.parts.put("baseW", this.baseW);
        this.baseNW = root.getChild("baseNW");
        this.parts.put("baseNW", this.baseNW);
        this.baseN = root.getChild("baseN");
        this.parts.put("baseN", this.baseN);
        this.baseNE = root.getChild("baseNE");
        this.parts.put("baseNE", this.baseNE);
        this.baseE = root.getChild("baseE");
        this.parts.put("baseE", this.baseE);
        this.baseSE = root.getChild("baseSE");
        this.parts.put("baseSE", this.baseSE);
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
        PartDefinition p_cap = root.addOrReplaceChild("cap",
                CubeListBuilder.create().texOffs(0, 44).addBox(-8.5F, -19.0F, -8.5F, 17.0F, 0.0F, 17.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -1.570796F, 0.0F));
        PartDefinition p_capN = root.addOrReplaceChild("capN",
                CubeListBuilder.create().texOffs(85, 26).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glassN = root.addOrReplaceChild("glassN",
                CubeListBuilder.create().texOffs(36, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_capNE = root.addOrReplaceChild("capNE",
                CubeListBuilder.create().texOffs(106, 26).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
        PartDefinition p_capE = root.addOrReplaceChild("capE",
                CubeListBuilder.create().texOffs(127, 26).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_capSE = root.addOrReplaceChild("capSE",
                CubeListBuilder.create().texOffs(85, 34).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194F, 0.0F));
        PartDefinition p_supportBar = root.addOrReplaceChild("supportBar",
                CubeListBuilder.create().texOffs(148, 0).addBox(-4.5F, -18.0F, 6.5F, 9.0F, 42.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141593F, 0.0F));
        PartDefinition p_capSW = root.addOrReplaceChild("capSW",
                CubeListBuilder.create().texOffs(85, 18).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194F, 0.0F));
        PartDefinition p_capW = root.addOrReplaceChild("capW",
                CubeListBuilder.create().texOffs(106, 18).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.570796F, 0.0F));
        PartDefinition p_capNW = root.addOrReplaceChild("capNW",
                CubeListBuilder.create().texOffs(127, 18).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
        PartDefinition p_glassNW = root.addOrReplaceChild("glassNW",
                CubeListBuilder.create().texOffs(24, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
        PartDefinition p_glassW = root.addOrReplaceChild("glassW",
                CubeListBuilder.create().texOffs(12, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.570796F, 0.0F));
        PartDefinition p_glassSW = root.addOrReplaceChild("glassSW",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194F, 0.0F));
        PartDefinition p_glassNE = root.addOrReplaceChild("glassNE",
                CubeListBuilder.create().texOffs(48, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
        PartDefinition p_glassE = root.addOrReplaceChild("glassE",
                CubeListBuilder.create().texOffs(60, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_glassSE = root.addOrReplaceChild("glassSE",
                CubeListBuilder.create().texOffs(72, 18).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 25.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194F, 0.0F));
        PartDefinition p_baseBottom = root.addOrReplaceChild("baseBottom",
                CubeListBuilder.create().texOffs(68, 44).addBox(-8.5F, 23.0F, -8.5F, 17.0F, 0.0F, 17.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -1.570796F, 0.0F));
        PartDefinition p_baseSW = root.addOrReplaceChild("baseSW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194F, 0.0F));
        PartDefinition p_baseW = root.addOrReplaceChild("baseW",
                CubeListBuilder.create().texOffs(20, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.570796F, 0.0F));
        PartDefinition p_baseNW = root.addOrReplaceChild("baseNW",
                CubeListBuilder.create().texOffs(40, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853982F, 0.0F));
        PartDefinition p_baseN = root.addOrReplaceChild("baseN",
                CubeListBuilder.create().texOffs(60, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_baseNE = root.addOrReplaceChild("baseNE",
                CubeListBuilder.create().texOffs(80, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
        PartDefinition p_baseE = root.addOrReplaceChild("baseE",
                CubeListBuilder.create().texOffs(100, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_baseSE = root.addOrReplaceChild("baseSE",
                CubeListBuilder.create().texOffs(120, 0).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
