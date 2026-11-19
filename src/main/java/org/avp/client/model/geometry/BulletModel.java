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
 * Geometry converted automatically from the 1.12.2 ModelBullet by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BulletModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "bullet"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart caseFR;
    public final ModelPart case2;
    public final ModelPart tip1;
    public final ModelPart tip2;
    public final ModelPart caseBR;
    public final ModelPart caseFL;
    public final ModelPart caseBL;

    public BulletModel(ModelPart root)
    {
        this.root = root;
        this.caseFR = root.getChild("caseFR");
        this.parts.put("caseFR", this.caseFR);
        this.case2 = root.getChild("case2");
        this.parts.put("case2", this.case2);
        this.tip1 = root.getChild("tip1");
        this.parts.put("tip1", this.tip1);
        this.tip2 = root.getChild("tip2");
        this.parts.put("tip2", this.tip2);
        this.caseBR = root.getChild("caseBR");
        this.parts.put("caseBR", this.caseBR);
        this.caseFL = root.getChild("caseFL");
        this.parts.put("caseFL", this.caseFL);
        this.caseBL = root.getChild("caseBL");
        this.parts.put("caseBL", this.caseBL);
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
        PartDefinition p_caseFR = root.addOrReplaceChild("caseFR",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.4F, -1.0F, 0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_case2 = root.addOrReplaceChild("case2",
                CubeListBuilder.create().texOffs(10, 0).addBox(0.5F, -2.5F, 0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tip1 = root.addOrReplaceChild("tip1",
                CubeListBuilder.create().texOffs(25, 0).addBox(1.0F, -4.0F, 0.5F, 0.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tip2 = root.addOrReplaceChild("tip2",
                CubeListBuilder.create().texOffs(29, 0).addBox(-1.0F, -4.0F, 0.5F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_caseBR = root.addOrReplaceChild("caseBR",
                CubeListBuilder.create().texOffs(0, 6).addBox(0.4F, -1.0F, 0.6F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_caseFL = root.addOrReplaceChild("caseFL",
                CubeListBuilder.create().texOffs(5, 0).addBox(0.6F, -1.0F, 0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_caseBL = root.addOrReplaceChild("caseBL",
                CubeListBuilder.create().texOffs(5, 6).addBox(0.6F, -1.0F, 0.6F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
