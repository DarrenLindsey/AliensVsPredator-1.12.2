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
 * Geometry converted automatically from the 1.12.2 ModelPredatorHound by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class PredatorHoundModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "predator_hound"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart lQuills;
    public final ModelPart lShoulder;
    public final ModelPart lForearm;
    public final ModelPart lFPaw;
    public final ModelPart lFClaws;
    public final ModelPart rQuills;
    public final ModelPart stomach;
    public final ModelPart rHip;
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart lLowerLeg;
    public final ModelPart lHPaw;
    public final ModelPart lHClaws;
    public final ModelPart tail;
    public final ModelPart lHip;
    public final ModelPart rThigh;
    public final ModelPart rShin;
    public final ModelPart rLowerLeg;
    public final ModelPart rHPaw;
    public final ModelPart rHClaws;
    public final ModelPart rShoulder;
    public final ModelPart rForearm;
    public final ModelPart rFPaw;
    public final ModelPart rFClaws;
    public final ModelPart rMidQuills;
    public final ModelPart lMidQuills;
    public final ModelPart neck;
    public final ModelPart head1;
    public final ModelPart l2Horn1a;
    public final ModelPart lHorn1d;
    public final ModelPart lHorn2;
    public final ModelPart lHorn1b;
    public final ModelPart lHorn3;
    public final ModelPart lHorn1c;
    public final ModelPart nose;
    public final ModelPart jaw1;
    public final ModelPart teethLower;
    public final ModelPart r2Horn1a;
    public final ModelPart lHorn2_1;
    public final ModelPart lHorn1b_1;
    public final ModelPart lHorn3_1;
    public final ModelPart lHorn1c_1;
    public final ModelPart lHorn1d_1;
    public final ModelPart lNoseSpike1;
    public final ModelPart lNoseSpike2;
    public final ModelPart rNoseSpike1;
    public final ModelPart rNoseSpike2;
    public final ModelPart jawUpper;
    public final ModelPart teethUpper;
    public final ModelPart lTusk1;
    public final ModelPart lTusk2c;
    public final ModelPart lTusk4;
    public final ModelPart lTusk3;
    public final ModelPart lTusk2a;
    public final ModelPart lTusk2b;
    public final ModelPart lTusk2d;
    public final ModelPart lHorn1a;
    public final ModelPart lHorn1c_2;
    public final ModelPart lHorn3_2;
    public final ModelPart lHorn1b_2;
    public final ModelPart lHorn2_2;
    public final ModelPart lHorn1d_2;
    public final ModelPart rHorn1a;
    public final ModelPart rHorn1d;
    public final ModelPart rHorn3;
    public final ModelPart rHorn1c;
    public final ModelPart rHorn2;
    public final ModelPart rHorn1b;
    public final ModelPart rTusk1;
    public final ModelPart rTusk2b;
    public final ModelPart rTusk2a;
    public final ModelPart rTusk3;
    public final ModelPart rTusk2c;
    public final ModelPart rTusk2d;
    public final ModelPart rTusk4;

    public PredatorHoundModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.lQuills = this.chest.getChild("lQuills");
        this.parts.put("lQuills", this.lQuills);
        this.lShoulder = this.chest.getChild("lShoulder");
        this.parts.put("lShoulder", this.lShoulder);
        this.lForearm = this.lShoulder.getChild("lForearm");
        this.parts.put("lForearm", this.lForearm);
        this.lFPaw = this.lForearm.getChild("lFPaw");
        this.parts.put("lFPaw", this.lFPaw);
        this.lFClaws = this.lFPaw.getChild("lFClaws");
        this.parts.put("lFClaws", this.lFClaws);
        this.rQuills = this.chest.getChild("rQuills");
        this.parts.put("rQuills", this.rQuills);
        this.stomach = this.chest.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.rHip = this.stomach.getChild("rHip");
        this.parts.put("rHip", this.rHip);
        this.lThigh = this.stomach.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lLowerLeg = this.lShin.getChild("lLowerLeg");
        this.parts.put("lLowerLeg", this.lLowerLeg);
        this.lHPaw = this.lLowerLeg.getChild("lHPaw");
        this.parts.put("lHPaw", this.lHPaw);
        this.lHClaws = this.lHPaw.getChild("lHClaws");
        this.parts.put("lHClaws", this.lHClaws);
        this.tail = this.stomach.getChild("tail");
        this.parts.put("tail", this.tail);
        this.lHip = this.stomach.getChild("lHip");
        this.parts.put("lHip", this.lHip);
        this.rThigh = this.stomach.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rLowerLeg = this.rShin.getChild("rLowerLeg");
        this.parts.put("rLowerLeg", this.rLowerLeg);
        this.rHPaw = this.rLowerLeg.getChild("rHPaw");
        this.parts.put("rHPaw", this.rHPaw);
        this.rHClaws = this.rHPaw.getChild("rHClaws");
        this.parts.put("rHClaws", this.rHClaws);
        this.rShoulder = this.chest.getChild("rShoulder");
        this.parts.put("rShoulder", this.rShoulder);
        this.rForearm = this.rShoulder.getChild("rForearm");
        this.parts.put("rForearm", this.rForearm);
        this.rFPaw = this.rForearm.getChild("rFPaw");
        this.parts.put("rFPaw", this.rFPaw);
        this.rFClaws = this.rFPaw.getChild("rFClaws");
        this.parts.put("rFClaws", this.rFClaws);
        this.rMidQuills = this.chest.getChild("rMidQuills");
        this.parts.put("rMidQuills", this.rMidQuills);
        this.lMidQuills = this.chest.getChild("lMidQuills");
        this.parts.put("lMidQuills", this.lMidQuills);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.head1 = this.neck.getChild("head1");
        this.parts.put("head1", this.head1);
        this.l2Horn1a = this.head1.getChild("l2Horn1a");
        this.parts.put("l2Horn1a", this.l2Horn1a);
        this.lHorn1d = this.l2Horn1a.getChild("lHorn1d");
        this.parts.put("lHorn1d", this.lHorn1d);
        this.lHorn2 = this.l2Horn1a.getChild("lHorn2");
        this.parts.put("lHorn2", this.lHorn2);
        this.lHorn1b = this.l2Horn1a.getChild("lHorn1b");
        this.parts.put("lHorn1b", this.lHorn1b);
        this.lHorn3 = this.l2Horn1a.getChild("lHorn3");
        this.parts.put("lHorn3", this.lHorn3);
        this.lHorn1c = this.l2Horn1a.getChild("lHorn1c");
        this.parts.put("lHorn1c", this.lHorn1c);
        this.nose = this.head1.getChild("nose");
        this.parts.put("nose", this.nose);
        this.jaw1 = this.head1.getChild("jaw1");
        this.parts.put("jaw1", this.jaw1);
        this.teethLower = this.jaw1.getChild("teethLower");
        this.parts.put("teethLower", this.teethLower);
        this.r2Horn1a = this.head1.getChild("r2Horn1a");
        this.parts.put("r2Horn1a", this.r2Horn1a);
        this.lHorn2_1 = this.r2Horn1a.getChild("lHorn2_1");
        this.parts.put("lHorn2_1", this.lHorn2_1);
        this.lHorn1b_1 = this.r2Horn1a.getChild("lHorn1b_1");
        this.parts.put("lHorn1b_1", this.lHorn1b_1);
        this.lHorn3_1 = this.r2Horn1a.getChild("lHorn3_1");
        this.parts.put("lHorn3_1", this.lHorn3_1);
        this.lHorn1c_1 = this.r2Horn1a.getChild("lHorn1c_1");
        this.parts.put("lHorn1c_1", this.lHorn1c_1);
        this.lHorn1d_1 = this.r2Horn1a.getChild("lHorn1d_1");
        this.parts.put("lHorn1d_1", this.lHorn1d_1);
        this.lNoseSpike1 = this.head1.getChild("lNoseSpike1");
        this.parts.put("lNoseSpike1", this.lNoseSpike1);
        this.lNoseSpike2 = this.lNoseSpike1.getChild("lNoseSpike2");
        this.parts.put("lNoseSpike2", this.lNoseSpike2);
        this.rNoseSpike1 = this.head1.getChild("rNoseSpike1");
        this.parts.put("rNoseSpike1", this.rNoseSpike1);
        this.rNoseSpike2 = this.rNoseSpike1.getChild("rNoseSpike2");
        this.parts.put("rNoseSpike2", this.rNoseSpike2);
        this.jawUpper = this.head1.getChild("jawUpper");
        this.parts.put("jawUpper", this.jawUpper);
        this.teethUpper = this.jawUpper.getChild("teethUpper");
        this.parts.put("teethUpper", this.teethUpper);
        this.lTusk1 = this.neck.getChild("lTusk1");
        this.parts.put("lTusk1", this.lTusk1);
        this.lTusk2c = this.lTusk1.getChild("lTusk2c");
        this.parts.put("lTusk2c", this.lTusk2c);
        this.lTusk4 = this.lTusk1.getChild("lTusk4");
        this.parts.put("lTusk4", this.lTusk4);
        this.lTusk3 = this.lTusk1.getChild("lTusk3");
        this.parts.put("lTusk3", this.lTusk3);
        this.lTusk2a = this.lTusk1.getChild("lTusk2a");
        this.parts.put("lTusk2a", this.lTusk2a);
        this.lTusk2b = this.lTusk1.getChild("lTusk2b");
        this.parts.put("lTusk2b", this.lTusk2b);
        this.lTusk2d = this.lTusk1.getChild("lTusk2d");
        this.parts.put("lTusk2d", this.lTusk2d);
        this.lHorn1a = this.neck.getChild("lHorn1a");
        this.parts.put("lHorn1a", this.lHorn1a);
        this.lHorn1c_2 = this.lHorn1a.getChild("lHorn1c_2");
        this.parts.put("lHorn1c_2", this.lHorn1c_2);
        this.lHorn3_2 = this.lHorn1a.getChild("lHorn3_2");
        this.parts.put("lHorn3_2", this.lHorn3_2);
        this.lHorn1b_2 = this.lHorn1a.getChild("lHorn1b_2");
        this.parts.put("lHorn1b_2", this.lHorn1b_2);
        this.lHorn2_2 = this.lHorn1a.getChild("lHorn2_2");
        this.parts.put("lHorn2_2", this.lHorn2_2);
        this.lHorn1d_2 = this.lHorn1a.getChild("lHorn1d_2");
        this.parts.put("lHorn1d_2", this.lHorn1d_2);
        this.rHorn1a = this.neck.getChild("rHorn1a");
        this.parts.put("rHorn1a", this.rHorn1a);
        this.rHorn1d = this.rHorn1a.getChild("rHorn1d");
        this.parts.put("rHorn1d", this.rHorn1d);
        this.rHorn3 = this.rHorn1a.getChild("rHorn3");
        this.parts.put("rHorn3", this.rHorn3);
        this.rHorn1c = this.rHorn1a.getChild("rHorn1c");
        this.parts.put("rHorn1c", this.rHorn1c);
        this.rHorn2 = this.rHorn1a.getChild("rHorn2");
        this.parts.put("rHorn2", this.rHorn2);
        this.rHorn1b = this.rHorn1a.getChild("rHorn1b");
        this.parts.put("rHorn1b", this.rHorn1b);
        this.rTusk1 = this.neck.getChild("rTusk1");
        this.parts.put("rTusk1", this.rTusk1);
        this.rTusk2b = this.rTusk1.getChild("rTusk2b");
        this.parts.put("rTusk2b", this.rTusk2b);
        this.rTusk2a = this.rTusk1.getChild("rTusk2a");
        this.parts.put("rTusk2a", this.rTusk2a);
        this.rTusk3 = this.rTusk1.getChild("rTusk3");
        this.parts.put("rTusk3", this.rTusk3);
        this.rTusk2c = this.rTusk1.getChild("rTusk2c");
        this.parts.put("rTusk2c", this.rTusk2c);
        this.rTusk2d = this.rTusk1.getChild("rTusk2d");
        this.parts.put("rTusk2d", this.rTusk2d);
        this.rTusk4 = this.rTusk1.getChild("rTusk4");
        this.parts.put("rTusk4", this.rTusk4);
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
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.9F, -3.0F, -4.5F, 7.0F, 7.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 10.2F, -4.5F, 0.12217304763960307F, 0.0F, 0.0F));
        PartDefinition p_lQuills = p_chest.addOrReplaceChild("lQuills",
                CubeListBuilder.create().texOffs(0, 30).addBox(2.5F, -8.5F, -2.8F, 0.0F, 7.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.03490658503988659F, 0.10471975511965977F));
        PartDefinition p_lShoulder = p_chest.addOrReplaceChild("lShoulder",
                CubeListBuilder.create().texOffs(33, 0).mirror().addBox(-1.8F, -0.6F, -3.2F, 3.0F, 8.0F, 4.0F),
                PartPose.offsetAndRotation(4.0F, -0.3F, 1.0F, 0.18203784098300857F, -0.017453292519943295F, -0.15707963267948966F));
        PartDefinition p_lForearm = p_lShoulder.addOrReplaceChild("lForearm",
                CubeListBuilder.create().texOffs(34, 13).mirror().addBox(-1.7F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F),
                PartPose.offsetAndRotation(-0.2F, 6.3F, -0.5F, -0.5918411493512771F, 0.0F, 0.045553093477052F));
        PartDefinition p_lFPaw = p_lForearm.addOrReplaceChild("lFPaw",
                CubeListBuilder.create().texOffs(33, 25).mirror().addBox(-1.5F, 0.0F, -3.9F, 3.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 6.3F, 0.9F, 0.27314402793711257F, 0.0F, 0.136659280431156F));
        PartDefinition p_lFClaws = p_lFPaw.addOrReplaceChild("lFClaws",
                CubeListBuilder.create().texOffs(61, 41).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.3F, -3.5F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rQuills = p_chest.addOrReplaceChild("rQuills",
                CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -8.5F, -2.8F, 0.0F, 7.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.03490658503988659F, -0.10471975511965977F));
        PartDefinition p_stomach = p_chest.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(1, 17).addBox(-2.5F, -2.5F, 0.0F, 6.0F, 5.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.8F, -0.26703537555513246F, 0.0F, 0.0F));
        PartDefinition p_rHip = p_stomach.addOrReplaceChild("rHip",
                CubeListBuilder.create().texOffs(32, 44).addBox(-3.0F, -5.3F, 4.5F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F, -0.40980330836826856F));
        PartDefinition p_lThigh = p_stomach.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(48, 1).mirror().addBox(-1.0F, -0.9F, -1.1F, 4.0F, 8.0F, 4.0F),
                PartPose.offsetAndRotation(1.7F, -0.6F, 6.5F, -0.091106186954104F, 0.0F, -0.15707963267948966F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(50, 14).mirror().addBox(-0.4F, -1.6F, -0.4F, 3.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 5.8F, 0.1F, -0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_lLowerLeg = p_lShin.addOrReplaceChild("lLowerLeg",
                CubeListBuilder.create().texOffs(51, 25).mirror().addBox(-1.5F, -0.5F, -1.4F, 3.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(1.0F, -0.4F, 5.2F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lHPaw = p_lLowerLeg.addOrReplaceChild("lHPaw",
                CubeListBuilder.create().texOffs(51, 34).mirror().addBox(-1.5F, -0.2F, -3.5F, 3.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.36425021489121656F, 0.0F, 0.15707963267948966F));
        PartDefinition p_lHClaws = p_lHPaw.addOrReplaceChild("lHClaws",
                CubeListBuilder.create().texOffs(61, 41).mirror().addBox(-1.5F, -0.3F, -1.7F, 3.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, -3.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_tail = p_stomach.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(65, 1).addBox(-0.5F, 0.4F, 8.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lHip = p_stomach.addOrReplaceChild("lHip",
                CubeListBuilder.create().texOffs(32, 44).mirror().addBox(0.8F, -5.6F, 4.5F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F, 0.40980330836826856F));
        PartDefinition p_rThigh = p_stomach.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(48, 1).addBox(-1.0F, -1.3F, -1.1F, 4.0F, 8.0F, 4.0F),
                PartPose.offsetAndRotation(-3.0F, -0.6F, 6.5F, -0.091106186954104F, 0.0F, 0.15707963267948966F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(50, 14).addBox(-0.4F, -1.7F, -0.4F, 3.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 5.8F, 0.1F, -0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_rLowerLeg = p_rShin.addOrReplaceChild("rLowerLeg",
                CubeListBuilder.create().texOffs(51, 25).addBox(-1.5F, -0.5F, -1.4F, 3.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(1.0F, -0.4F, 5.2F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_rHPaw = p_rLowerLeg.addOrReplaceChild("rHPaw",
                CubeListBuilder.create().texOffs(51, 34).addBox(-1.5F, -0.2F, -3.5F, 3.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.5F, 0.0F, 0.36425021489121656F, 0.0F, -0.15707963267948966F));
        PartDefinition p_rHClaws = p_rHPaw.addOrReplaceChild("rHClaws",
                CubeListBuilder.create().texOffs(61, 41).addBox(-1.5F, -0.3F, -1.7F, 3.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, -3.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_rShoulder = p_chest.addOrReplaceChild("rShoulder",
                CubeListBuilder.create().texOffs(33, 0).addBox(-1.2F, -0.6F, -3.2F, 3.0F, 8.0F, 4.0F),
                PartPose.offsetAndRotation(-3.0F, -0.3F, 1.0F, 0.18203784098300857F, 0.017453292519943295F, 0.15707963267948966F));
        PartDefinition p_rForearm = p_rShoulder.addOrReplaceChild("rForearm",
                CubeListBuilder.create().texOffs(34, 13).mirror().addBox(-1.3F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F),
                PartPose.offsetAndRotation(0.2F, 6.3F, -0.5F, -0.5918411493512771F, 0.0F, -0.045553093477052F));
        PartDefinition p_rFPaw = p_rForearm.addOrReplaceChild("rFPaw",
                CubeListBuilder.create().texOffs(33, 25).addBox(-1.5F, 0.0F, -3.9F, 3.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 6.3F, 0.9F, 0.27314402793711257F, 0.0F, -0.091106186954104F));
        PartDefinition p_rFClaws = p_rFPaw.addOrReplaceChild("rFClaws",
                CubeListBuilder.create().texOffs(61, 41).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.3F, -3.5F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMidQuills = p_chest.addOrReplaceChild("rMidQuills",
                CubeListBuilder.create().texOffs(0, 19).addBox(0.8F, -10.0F, -7.4F, 0.0F, 8.0F, 14.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.22759093446006054F));
        PartDefinition p_lMidQuills = p_chest.addOrReplaceChild("lMidQuills",
                CubeListBuilder.create().texOffs(0, 19).addBox(0.5F, -10.0F, -7.4F, 0.0F, 8.0F, 14.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.22759093446006054F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(80, 23).addBox(-2.5F, -2.3F, -3.9F, 5.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.5F, 0.0F, -3.5F, 0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition p_head1 = p_neck.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(80, 15).addBox(-2.5F, -2.13F, -2.8F, 5.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -3.5F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_l2Horn1a = p_head1.addOrReplaceChild("l2Horn1a",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.8F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(2.7F, -0.4F, 1.4F, -0.5009094953223726F, -0.22689280275926282F, 0.0F));
        PartDefinition p_lHorn1d = p_l2Horn1a.addOrReplaceChild("lHorn1d",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.2F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn2 = p_l2Horn1a.addOrReplaceChild("lHorn2",
                CubeListBuilder.create().texOffs(65, 7).addBox(-1.0F, -0.8F, -7.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn1b = p_l2Horn1a.addOrReplaceChild("lHorn1b",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.8F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn3 = p_l2Horn1a.addOrReplaceChild("lHorn3",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.0F, -2.6F, -10.5F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_lHorn1c = p_l2Horn1a.addOrReplaceChild("lHorn1c",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.2F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nose = p_head1.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(80, 8).addBox(-1.5F, -2.56F, -3.8F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_jaw1 = p_head1.addOrReplaceChild("jaw1",
                CubeListBuilder.create().texOffs(80, 38).addBox(-1.5F, -0.69F, -3.5F, 3.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 1.4F, -1.5F));
        PartDefinition p_teethLower = p_jaw1.addOrReplaceChild("teethLower",
                CubeListBuilder.create().texOffs(0, 57).addBox(-1.5F, -1.5F, -3.4F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_r2Horn1a = p_head1.addOrReplaceChild("r2Horn1a",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.8F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-1.7F, -0.4F, 1.4F, -0.5009094953223726F, 0.22689280275926282F, 0.0F));
        PartDefinition p_lHorn2_1 = p_r2Horn1a.addOrReplaceChild("lHorn2_1",
                CubeListBuilder.create().texOffs(65, 7).addBox(-1.0F, -0.8F, -7.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn1b_1 = p_r2Horn1a.addOrReplaceChild("lHorn1b_1",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.8F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn3_1 = p_r2Horn1a.addOrReplaceChild("lHorn3_1",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.0F, -2.6F, -10.5F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_lHorn1c_1 = p_r2Horn1a.addOrReplaceChild("lHorn1c_1",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.2F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn1d_1 = p_r2Horn1a.addOrReplaceChild("lHorn1d_1",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.2F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lNoseSpike1 = p_head1.addOrReplaceChild("lNoseSpike1",
                CubeListBuilder.create().texOffs(67, 9).addBox(-0.1F, -0.1F, -5.4F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.17453292519943295F, -0.4363323129985824F, 0.0F));
        PartDefinition p_lNoseSpike2 = p_lNoseSpike1.addOrReplaceChild("lNoseSpike2",
                CubeListBuilder.create().texOffs(67, 9).addBox(2.1F, -0.1F, -7.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363323129985824F, 0.0F));
        PartDefinition p_rNoseSpike1 = p_head1.addOrReplaceChild("rNoseSpike1",
                CubeListBuilder.create().texOffs(67, 9).addBox(-0.9F, -0.1F, -5.4F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.17453292519943295F, 0.4363323129985824F, 0.0F));
        PartDefinition p_rNoseSpike2 = p_rNoseSpike1.addOrReplaceChild("rNoseSpike2",
                CubeListBuilder.create().texOffs(67, 9).addBox(-3.1F, -0.2F, -7.6F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363323129985824F, 0.0F));
        PartDefinition p_jawUpper = p_head1.addOrReplaceChild("jawUpper",
                CubeListBuilder.create().texOffs(95, 8).addBox(-1.5F, -0.6F, -5.2F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_teethUpper = p_jawUpper.addOrReplaceChild("teethUpper",
                CubeListBuilder.create().texOffs(0, 52).addBox(-1.5F, 0.2F, -5.2F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lTusk1 = p_neck.addOrReplaceChild("lTusk1",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.9F, -0.5F, -3.2F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(2.4F, 1.5F, -0.7F, 0.36425021489121656F, -0.3141592653589793F, 0.0F));
        PartDefinition p_lTusk2c = p_lTusk1.addOrReplaceChild("lTusk2c",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.6F, -0.3F, -7.2F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lTusk4 = p_lTusk1.addOrReplaceChild("lTusk4",
                CubeListBuilder.create().texOffs(67, 9).addBox(-0.4F, 4.0F, -11.6F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5235987755982988F, 0.0F, 0.0F));
        PartDefinition p_lTusk3 = p_lTusk1.addOrReplaceChild("lTusk3",
                CubeListBuilder.create().texOffs(67, 9).addBox(-0.4F, 2.4F, -9.4F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition p_lTusk2a = p_lTusk1.addOrReplaceChild("lTusk2a",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.1F, -0.3F, -7.2F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lTusk2b = p_lTusk1.addOrReplaceChild("lTusk2b",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.1F, 0.2F, -7.2F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lTusk2d = p_lTusk1.addOrReplaceChild("lTusk2d",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.6F, 0.2F, -7.2F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn1a = p_neck.addOrReplaceChild("lHorn1a",
                CubeListBuilder.create().texOffs(66, 8).mirror().addBox(-0.8F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(2.8F, -2.3F, -1.0F, -0.36425021489121656F, -0.22689280275926282F, 0.0F));
        PartDefinition p_lHorn1c_2 = p_lHorn1a.addOrReplaceChild("lHorn1c_2",
                CubeListBuilder.create().texOffs(66, 8).mirror().addBox(-1.2F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn3_2 = p_lHorn1a.addOrReplaceChild("lHorn3_2",
                CubeListBuilder.create().texOffs(66, 8).mirror().addBox(-1.0F, -2.6F, -10.5F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_lHorn1b_2 = p_lHorn1a.addOrReplaceChild("lHorn1b_2",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.8F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn2_2 = p_lHorn1a.addOrReplaceChild("lHorn2_2",
                CubeListBuilder.create().texOffs(65, 7).mirror().addBox(-1.0F, -0.8F, -7.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHorn1d_2 = p_lHorn1a.addOrReplaceChild("lHorn1d_2",
                CubeListBuilder.create().texOffs(66, 8).addBox(-1.2F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rHorn1a = p_neck.addOrReplaceChild("rHorn1a",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.1F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-2.9F, -2.3F, -1.0F, -0.36425021489121656F, 0.22689280275926282F, 0.0F));
        PartDefinition p_rHorn1d = p_rHorn1a.addOrReplaceChild("rHorn1d",
                CubeListBuilder.create().texOffs(66, 8).addBox(0.3F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rHorn3 = p_rHorn1a.addOrReplaceChild("rHorn3",
                CubeListBuilder.create().texOffs(66, 8).addBox(0.1F, -2.6F, -10.5F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_rHorn1c = p_rHorn1a.addOrReplaceChild("rHorn1c",
                CubeListBuilder.create().texOffs(66, 8).addBox(0.3F, -1.0F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rHorn2 = p_rHorn1a.addOrReplaceChild("rHorn2",
                CubeListBuilder.create().texOffs(65, 7).addBox(0.1F, -0.8F, -7.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rHorn1b = p_rHorn1a.addOrReplaceChild("rHorn1b",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.1F, -0.6F, -2.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTusk1 = p_neck.addOrReplaceChild("rTusk1",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.9F, -0.5F, -3.2F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-2.5F, 1.5F, -0.7F, 0.36425021489121656F, 0.3141592653589793F, 0.0F));
        PartDefinition p_rTusk2b = p_rTusk1.addOrReplaceChild("rTusk2b",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.6F, 0.2F, -7.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTusk2a = p_rTusk1.addOrReplaceChild("rTusk2a",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.6F, -0.3F, -7.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTusk3 = p_rTusk1.addOrReplaceChild("rTusk3",
                CubeListBuilder.create().texOffs(67, 9).addBox(-0.4F, 2.4F, -9.4F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition p_rTusk2c = p_rTusk1.addOrReplaceChild("rTusk2c",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.1F, -0.3F, -7.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTusk2d = p_rTusk1.addOrReplaceChild("rTusk2d",
                CubeListBuilder.create().texOffs(66, 8).addBox(-0.1F, 0.2F, -7.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTusk4 = p_rTusk1.addOrReplaceChild("rTusk4",
                CubeListBuilder.create().texOffs(67, 9).addBox(-0.4F, 4.0F, -11.6F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5235987755982988F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
