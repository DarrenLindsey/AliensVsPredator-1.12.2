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
 * Geometry converted automatically from the 1.12.2 ModelNetworkModuleRack by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class NetworkModuleRackModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "network_module_rack"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart LowerFrameL;
    public final ModelPart lLattice;
    public final ModelPart upperFrameR;
    public final ModelPart upperFrameF;
    public final ModelPart shelf3;
    public final ModelPart rLattice;
    public final ModelPart light1;
    public final ModelPart frame1;
    public final ModelPart light2;
    public final ModelPart lowerFrameF;
    public final ModelPart shelf2;
    public final ModelPart frame3;
    public final ModelPart frame4;
    public final ModelPart lowerFrameB;
    public final ModelPart shelf4;
    public final ModelPart upperFrameB;
    public final ModelPart shelf1;
    public final ModelPart upperFrameL;
    public final ModelPart lowerFrameR;
    public final ModelPart frame2;

    public NetworkModuleRackModel(ModelPart root)
    {
        this.root = root;
        this.LowerFrameL = root.getChild("LowerFrameL");
        this.parts.put("LowerFrameL", this.LowerFrameL);
        this.lLattice = root.getChild("lLattice");
        this.parts.put("lLattice", this.lLattice);
        this.upperFrameR = root.getChild("upperFrameR");
        this.parts.put("upperFrameR", this.upperFrameR);
        this.upperFrameF = root.getChild("upperFrameF");
        this.parts.put("upperFrameF", this.upperFrameF);
        this.shelf3 = root.getChild("shelf3");
        this.parts.put("shelf3", this.shelf3);
        this.rLattice = root.getChild("rLattice");
        this.parts.put("rLattice", this.rLattice);
        this.light1 = root.getChild("light1");
        this.parts.put("light1", this.light1);
        this.frame1 = root.getChild("frame1");
        this.parts.put("frame1", this.frame1);
        this.light2 = root.getChild("light2");
        this.parts.put("light2", this.light2);
        this.lowerFrameF = root.getChild("lowerFrameF");
        this.parts.put("lowerFrameF", this.lowerFrameF);
        this.shelf2 = root.getChild("shelf2");
        this.parts.put("shelf2", this.shelf2);
        this.frame3 = root.getChild("frame3");
        this.parts.put("frame3", this.frame3);
        this.frame4 = root.getChild("frame4");
        this.parts.put("frame4", this.frame4);
        this.lowerFrameB = root.getChild("lowerFrameB");
        this.parts.put("lowerFrameB", this.lowerFrameB);
        this.shelf4 = root.getChild("shelf4");
        this.parts.put("shelf4", this.shelf4);
        this.upperFrameB = root.getChild("upperFrameB");
        this.parts.put("upperFrameB", this.upperFrameB);
        this.shelf1 = root.getChild("shelf1");
        this.parts.put("shelf1", this.shelf1);
        this.upperFrameL = root.getChild("upperFrameL");
        this.parts.put("upperFrameL", this.upperFrameL);
        this.lowerFrameR = root.getChild("lowerFrameR");
        this.parts.put("lowerFrameR", this.lowerFrameR);
        this.frame2 = root.getChild("frame2");
        this.parts.put("frame2", this.frame2);
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
        PartDefinition p_LowerFrameL = root.addOrReplaceChild("LowerFrameL",
                CubeListBuilder.create().texOffs(90, 10).addBox(18.0F, 22.3F, -7.5F, 1.0F, 1.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lLattice = root.addOrReplaceChild("lLattice",
                CubeListBuilder.create().texOffs(20, -15).addBox(18.7F, -21.5F, -7.5F, 0.0F, 44.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_upperFrameR = root.addOrReplaceChild("upperFrameR",
                CubeListBuilder.create().texOffs(55, 10).addBox(-7.5F, -22.5F, -7.5F, 1.0F, 1.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_upperFrameF = root.addOrReplaceChild("upperFrameF",
                CubeListBuilder.create().texOffs(55, 5).addBox(-7.8F, -22.5F, -7.5F, 27.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_shelf3 = root.addOrReplaceChild("shelf3",
                CubeListBuilder.create().texOffs(8, 50).addBox(-15.0F, -0.5F, -6.0F, 25.0F, 1.0F, 12.0F),
                PartPose.offset(8.0F, 0.5F, 0.0F));
        PartDefinition p_rLattice = root.addOrReplaceChild("rLattice",
                CubeListBuilder.create().texOffs(20, -15).mirror().addBox(-7.1F, -21.5F, -7.5F, 0.0F, 44.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light1 = root.addOrReplaceChild("light1",
                CubeListBuilder.create().texOffs(100, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(-7.0F, -1.5F, -7.3F));
        PartDefinition p_frame1 = root.addOrReplaceChild("frame1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -23.0F, -8.0F, 2.0F, 47.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light2 = root.addOrReplaceChild("light2",
                CubeListBuilder.create().texOffs(100, 0).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(18.5F, -1.5F, -7.3F));
        PartDefinition p_lowerFrameF = root.addOrReplaceChild("lowerFrameF",
                CubeListBuilder.create().texOffs(55, 35).addBox(-7.8F, 22.3F, -7.5F, 27.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_shelf2 = root.addOrReplaceChild("shelf2",
                CubeListBuilder.create().texOffs(8, 50).addBox(-15.0F, -0.5F, -6.0F, 25.0F, 1.0F, 12.0F),
                PartPose.offset(8.0F, 11.5F, 0.0F));
        PartDefinition p_frame3 = root.addOrReplaceChild("frame3",
                CubeListBuilder.create().texOffs(0, 0).mirror().addBox(17.5F, -23.0F, -8.0F, 2.0F, 47.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_frame4 = root.addOrReplaceChild("frame4",
                CubeListBuilder.create().texOffs(10, 0).mirror().addBox(17.5F, -23.0F, 6.0F, 2.0F, 47.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerFrameB = root.addOrReplaceChild("lowerFrameB",
                CubeListBuilder.create().texOffs(55, 35).addBox(-7.8F, 22.3F, 6.5F, 27.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_shelf4 = root.addOrReplaceChild("shelf4",
                CubeListBuilder.create().texOffs(8, 50).addBox(-15.0F, -0.5F, -6.0F, 25.0F, 1.0F, 12.0F),
                PartPose.offset(8.0F, -11.5F, 0.0F));
        PartDefinition p_upperFrameB = root.addOrReplaceChild("upperFrameB",
                CubeListBuilder.create().texOffs(55, 5).addBox(-7.8F, -22.5F, 6.5F, 27.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_shelf1 = root.addOrReplaceChild("shelf1",
                CubeListBuilder.create().texOffs(8, 50).addBox(-15.0F, -0.5F, -6.0F, 25.0F, 1.0F, 12.0F),
                PartPose.offset(8.0F, 22.5F, 0.0F));
        PartDefinition p_upperFrameL = root.addOrReplaceChild("upperFrameL",
                CubeListBuilder.create().texOffs(55, 10).addBox(18.0F, -22.5F, -7.5F, 1.0F, 1.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerFrameR = root.addOrReplaceChild("lowerFrameR",
                CubeListBuilder.create().texOffs(90, 10).addBox(-7.5F, 22.3F, -7.5F, 1.0F, 1.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_frame2 = root.addOrReplaceChild("frame2",
                CubeListBuilder.create().texOffs(10, 0).addBox(-8.0F, -23.0F, 6.0F, 2.0F, 47.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
