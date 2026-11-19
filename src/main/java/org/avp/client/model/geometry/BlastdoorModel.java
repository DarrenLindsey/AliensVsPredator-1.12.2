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
 * Geometry converted automatically from the 1.12.2 ModelBlastdoor by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BlastdoorModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "blastdoor"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart doorLeftCorner1;
    public final ModelPart doorLeftCorner2;
    public final ModelPart doorLeftMain;
    public final ModelPart doorLeftDiagonal2;
    public final ModelPart doorLeftDiagonal1;
    public final ModelPart doorRightMain;
    public final ModelPart doorRightArrow;
    public final ModelPart frameLeft;
    public final ModelPart frameBaseCenterTop;
    public final ModelPart bar3;
    public final ModelPart frameBaseCenter;
    public final ModelPart bar2;
    public final ModelPart frameRight;
    public final ModelPart bar1;
    public final ModelPart bar4;

    public BlastdoorModel(ModelPart root)
    {
        this.root = root;
        this.doorLeftCorner1 = root.getChild("doorLeftCorner1");
        this.parts.put("doorLeftCorner1", this.doorLeftCorner1);
        this.doorLeftCorner2 = root.getChild("doorLeftCorner2");
        this.parts.put("doorLeftCorner2", this.doorLeftCorner2);
        this.doorLeftMain = root.getChild("doorLeftMain");
        this.parts.put("doorLeftMain", this.doorLeftMain);
        this.doorLeftDiagonal2 = root.getChild("doorLeftDiagonal2");
        this.parts.put("doorLeftDiagonal2", this.doorLeftDiagonal2);
        this.doorLeftDiagonal1 = root.getChild("doorLeftDiagonal1");
        this.parts.put("doorLeftDiagonal1", this.doorLeftDiagonal1);
        this.doorRightMain = root.getChild("doorRightMain");
        this.parts.put("doorRightMain", this.doorRightMain);
        this.doorRightArrow = root.getChild("doorRightArrow");
        this.parts.put("doorRightArrow", this.doorRightArrow);
        this.frameLeft = root.getChild("frameLeft");
        this.parts.put("frameLeft", this.frameLeft);
        this.frameBaseCenterTop = root.getChild("frameBaseCenterTop");
        this.parts.put("frameBaseCenterTop", this.frameBaseCenterTop);
        this.bar3 = root.getChild("bar3");
        this.parts.put("bar3", this.bar3);
        this.frameBaseCenter = root.getChild("frameBaseCenter");
        this.parts.put("frameBaseCenter", this.frameBaseCenter);
        this.bar2 = root.getChild("bar2");
        this.parts.put("bar2", this.bar2);
        this.frameRight = root.getChild("frameRight");
        this.parts.put("frameRight", this.frameRight);
        this.bar1 = root.getChild("bar1");
        this.parts.put("bar1", this.bar1);
        this.bar4 = root.getChild("bar4");
        this.parts.put("bar4", this.bar4);
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
        PartDefinition p_doorLeftCorner1 = root.addOrReplaceChild("doorLeftCorner1",
                CubeListBuilder.create().texOffs(141, 0).addBox(16.0F, 2.0F, 0.0F, 12.0F, 13.0F, 6.0F),
                PartPose.offset(-4.0F, -23.0F, 0.0F));
        PartDefinition p_doorLeftCorner2 = root.addOrReplaceChild("doorLeftCorner2",
                CubeListBuilder.create().texOffs(141, 0).addBox(16.0F, 35.0F, 0.0F, 12.0F, 13.0F, 6.0F),
                PartPose.offset(-4.0F, -26.0F, 0.0F));
        PartDefinition p_doorLeftMain = root.addOrReplaceChild("doorLeftMain",
                CubeListBuilder.create().texOffs(182, 0).addBox(-1.0F, 0.0F, 0.0F, 17.0F, 45.0F, 6.0F),
                PartPose.offset(-4.0F, -23.0F, 0.0F));
        PartDefinition p_doorLeftDiagonal2 = root.addOrReplaceChild("doorLeftDiagonal2",
                CubeListBuilder.create().texOffs(103, 2).addBox(-12.7F, 26.3F, 0.0F, 10.0F, 16.0F, 6.0F),
                PartPose.offsetAndRotation(-4.0F, -23.0F, 0.0F, 0.0F, 0.0F, -0.7853982F));
        PartDefinition p_doorLeftDiagonal1 = root.addOrReplaceChild("doorLeftDiagonal1",
                CubeListBuilder.create().texOffs(106, 30).addBox(-5.1F, 18.8F, 0.0F, 16.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(-4.0F, -23.0F, 0.0F, 0.0F, 0.0F, -0.7853982F));
        PartDefinition p_doorRightMain = root.addOrReplaceChild("doorRightMain",
                CubeListBuilder.create().texOffs(235, 0).addBox(0.0F, 0.0F, 0.0F, 29.0F, 45.0F, 6.0F),
                PartPose.offset(24.0F, -23.0F, -4.0F));
        PartDefinition p_doorRightArrow = root.addOrReplaceChild("doorRightArrow",
                CubeListBuilder.create().texOffs(307, 0).addBox(9.0F, 9.0F, 0.0F, 16.0F, 16.0F, 6.0F),
                PartPose.offsetAndRotation(24.0F, -23.0F, -4.0F, 0.0F, 0.0F, 0.7853982F));
        PartDefinition p_frameLeft = root.addOrReplaceChild("frameLeft",
                CubeListBuilder.create().texOffs(82, 52).addBox(0.1333333F, -1.2F, 0.0F, 3.0F, 17.0F, 16.0F),
                PartPose.offset(-8.0F, -8.0F, -8.0F));
        PartDefinition p_frameBaseCenterTop = root.addOrReplaceChild("frameBaseCenterTop",
                CubeListBuilder.create().texOffs(1, 28).addBox(0.0F, 0.0F, 0.0F, 34.0F, 3.0F, 16.0F),
                PartPose.offset(7.0F, -24.0F, -8.0F));
        PartDefinition p_bar3 = root.addOrReplaceChild("bar3",
                CubeListBuilder.create().texOffs(1, 52).addBox(0.0F, 0.0F, 0.0F, 3.0F, 22.0F, 16.0F),
                PartPose.offsetAndRotation(41.0F, 24.0F, -8.0F, 0.0F, 0.0F, -2.414427F));
        PartDefinition p_frameBaseCenter = root.addOrReplaceChild("frameBaseCenter",
                CubeListBuilder.create().texOffs(1, 8).addBox(0.0F, 0.0F, 0.0F, 34.0F, 2.0F, 16.0F),
                PartPose.offset(7.0F, 22.0F, -8.0F));
        PartDefinition p_bar2 = root.addOrReplaceChild("bar2",
                CubeListBuilder.create().texOffs(43, 52).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 21.0F, 16.0F),
                PartPose.offsetAndRotation(41.0F, -24.0F, -8.0F, 0.0F, 0.0F, -0.7912101F));
        PartDefinition p_frameRight = root.addOrReplaceChild("frameRight",
                CubeListBuilder.create().texOffs(82, 52).addBox(-0.1F, -0.2F, 0.0F, 3.0F, 17.0F, 16.0F),
                PartPose.offset(53.0F, -9.0F, -8.0F));
        PartDefinition p_bar1 = root.addOrReplaceChild("bar1",
                CubeListBuilder.create().texOffs(43, 52).addBox(0.0F, 0.0F, 0.0F, 3.0F, 21.0F, 16.0F),
                PartPose.offsetAndRotation(7.0F, -24.0F, -8.0F, 0.0F, 0.0F, 0.7912159F));
        PartDefinition p_bar4 = root.addOrReplaceChild("bar4",
                CubeListBuilder.create().texOffs(1, 52).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 22.0F, 16.0F),
                PartPose.offsetAndRotation(7.0F, 24.0F, -8.0F, 0.0F, 0.0F, 2.414419F));
        return LayerDefinition.create(mesh, 512, 256);
    }
}
