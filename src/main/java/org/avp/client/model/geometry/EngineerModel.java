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
 * Geometry converted automatically from the 1.12.2 ModelEngineer by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class EngineerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "engineer"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart nozzle3c;
    public final ModelPart rShin;
    public final ModelPart lArmUpper;
    public final ModelPart head1;
    public final ModelPart nozzle1;
    public final ModelPart rFoot;
    public final ModelPart lThigh;
    public final ModelPart lFoot;
    public final ModelPart rJaw;
    public final ModelPart rThigh;
    public final ModelPart rArmUpper;
    public final ModelPart lShin;
    public final ModelPart nozzle2;
    public final ModelPart lArmLower;
    public final ModelPart nozzle3a;
    public final ModelPart lJaw;
    public final ModelPart stomach;
    public final ModelPart chest;
    public final ModelPart nozzle3b;
    public final ModelPart sexyFace;
    public final ModelPart hose;
    public final ModelPart lArmLower_1;
    public final ModelPart neck;
    public final ModelPart nozzle3d;
    public final ModelPart head2;

    public EngineerModel(ModelPart root)
    {
        this.root = root;
        this.nozzle3c = root.getChild("nozzle3c");
        this.parts.put("nozzle3c", this.nozzle3c);
        this.rShin = root.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.lArmUpper = root.getChild("lArmUpper");
        this.parts.put("lArmUpper", this.lArmUpper);
        this.head1 = root.getChild("head1");
        this.parts.put("head1", this.head1);
        this.nozzle1 = root.getChild("nozzle1");
        this.parts.put("nozzle1", this.nozzle1);
        this.rFoot = root.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.lThigh = root.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lFoot = root.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.rJaw = root.getChild("rJaw");
        this.parts.put("rJaw", this.rJaw);
        this.rThigh = root.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rArmUpper = root.getChild("rArmUpper");
        this.parts.put("rArmUpper", this.rArmUpper);
        this.lShin = root.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.nozzle2 = root.getChild("nozzle2");
        this.parts.put("nozzle2", this.nozzle2);
        this.lArmLower = root.getChild("lArmLower");
        this.parts.put("lArmLower", this.lArmLower);
        this.nozzle3a = root.getChild("nozzle3a");
        this.parts.put("nozzle3a", this.nozzle3a);
        this.lJaw = root.getChild("lJaw");
        this.parts.put("lJaw", this.lJaw);
        this.stomach = root.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.nozzle3b = root.getChild("nozzle3b");
        this.parts.put("nozzle3b", this.nozzle3b);
        this.sexyFace = root.getChild("sexyFace");
        this.parts.put("sexyFace", this.sexyFace);
        this.hose = root.getChild("hose");
        this.parts.put("hose", this.hose);
        this.lArmLower_1 = root.getChild("lArmLower_1");
        this.parts.put("lArmLower_1", this.lArmLower_1);
        this.neck = root.getChild("neck");
        this.parts.put("neck", this.neck);
        this.nozzle3d = root.getChild("nozzle3d");
        this.parts.put("nozzle3d", this.nozzle3d);
        this.head2 = root.getChild("head2");
        this.parts.put("head2", this.head2);
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
        PartDefinition p_nozzle3c = root.addOrReplaceChild("nozzle3c",
                CubeListBuilder.create().texOffs(57, 5).addBox(-0.699999988079071F, -4.699999809265137F, -2.4000000953674316F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.2217304706573486F, 0.0F, 0.0F));
        PartDefinition p_rShin = root.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(71, 35).addBox(-1.5F, 6.0F, -3.0F, 2.0F, 8.0F, 3.0F),
                PartPose.offsetAndRotation(-1.5F, 9.0F, 0.0F, 0.05235987901687623F, 0.0F, 0.05235987901687623F));
        PartDefinition p_lArmUpper = root.addOrReplaceChild("lArmUpper",
                CubeListBuilder.create().texOffs(55, 22).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -0.12217304855585097F, 0.0F, -0.2617993950843811F));
        PartDefinition p_head1 = root.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(17, 0).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.12217304855585097F, 0.0F, 0.0F));
        PartDefinition p_nozzle1 = root.addOrReplaceChild("nozzle1",
                CubeListBuilder.create().texOffs(36, 0).addBox(-1.5F, -4.800000190734863F, -1.2000000476837158F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.7330383062362671F, 0.0F, 0.0F));
        PartDefinition p_rFoot = root.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(71, 47).addBox(-2.299999952316284F, 13.0F, -4.199999809265137F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(-1.5F, 9.0F, 0.0F));
        PartDefinition p_lThigh = root.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(88, 22).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F),
                PartPose.offsetAndRotation(1.5F, 9.0F, 0.0F, -0.13962633907794952F, 0.0F, -0.05235987901687623F));
        PartDefinition p_lFoot = root.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(88, 47).addBox(0.20000000298023224F, 13.0F, -4.199999809265137F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(1.5F, 9.0F, 0.0F));
        PartDefinition p_rJaw = root.addOrReplaceChild("rJaw",
                CubeListBuilder.create().texOffs(17, 9).addBox(-2.0F, -4.0F, -2.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8157965990099155F, -0.18959362787687614F, -0.1599701935335199F));
        PartDefinition p_rThigh = root.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(71, 22).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F),
                PartPose.offsetAndRotation(-1.5F, 9.0F, 0.0F, -0.13962633907794952F, 0.0F, 0.05235987901687623F));
        PartDefinition p_rArmUpper = root.addOrReplaceChild("rArmUpper",
                CubeListBuilder.create().texOffs(44, 22).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.12217304855585097F, 0.0F, 0.2617993950843811F));
        PartDefinition p_lShin = root.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(88, 35).addBox(-0.5F, 6.0F, -3.0F, 2.0F, 8.0F, 3.0F),
                PartPose.offsetAndRotation(1.5F, 9.0F, 0.0F, 0.05235987901687623F, 0.0F, -0.05235987901687623F));
        PartDefinition p_nozzle2 = root.addOrReplaceChild("nozzle2",
                CubeListBuilder.create().texOffs(47, 0).addBox(-1.0F, -4.900000095367432F, -2.299999952316284F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.9250245094299316F, 0.0F, 0.0F));
        PartDefinition p_lArmLower = root.addOrReplaceChild("lArmLower",
                CubeListBuilder.create().texOffs(55, 33).addBox(-0.4000000059604645F, 5.0F, 0.30000001192092896F, 2.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, -0.2617993950843811F, 0.0F, -0.33161255717277527F));
        PartDefinition p_nozzle3a = root.addOrReplaceChild("nozzle3a",
                CubeListBuilder.create().texOffs(57, 0).addBox(-0.699999988079071F, -5.199999809265137F, -2.4000000953674316F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.2217304706573486F, 0.0F, 0.0F));
        PartDefinition p_lJaw = root.addOrReplaceChild("lJaw",
                CubeListBuilder.create().texOffs(32, 9).addBox(1.0F, -4.0F, -2.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8157965990099155F, 0.18959362787687614F, 0.1599701935335199F));
        PartDefinition p_stomach = root.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 36).addBox(-2.5F, 3.0F, -2.5F, 5.0F, 7.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nozzle3b = root.addOrReplaceChild("nozzle3b",
                CubeListBuilder.create().texOffs(64, 0).addBox(-0.30000001192092896F, -5.199999809265137F, -2.4000000953674316F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.2217304706573486F, 0.0F, 0.0F));
        PartDefinition p_sexyFace = root.addOrReplaceChild("sexyFace",
                CubeListBuilder.create().texOffs(82, 0).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition p_hose = root.addOrReplaceChild("hose",
                CubeListBuilder.create().texOffs(71, 0).addBox(-0.5F, -1.0F, -5.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.19198621809482574F, 0.0F, 0.0F));
        PartDefinition p_lArmLower_1 = root.addOrReplaceChild("lArmLower_1",
                CubeListBuilder.create().texOffs(44, 33).addBox(-1.600000023841858F, 5.0F, 0.30000001192092896F, 2.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.2617993950843811F, 0.0F, 0.33161255717277527F));
        PartDefinition p_neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.12217304855585097F, 0.0F, 0.0F));
        PartDefinition p_nozzle3d = root.addOrReplaceChild("nozzle3d",
                CubeListBuilder.create().texOffs(64, 5).addBox(-0.30000001192092896F, -4.699999809265137F, -2.4000000953674316F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.2217304706573486F, 0.0F, 0.0F));
        PartDefinition p_head2 = root.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.900000095367432F, -1.0F, 4.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.03490658476948738F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
