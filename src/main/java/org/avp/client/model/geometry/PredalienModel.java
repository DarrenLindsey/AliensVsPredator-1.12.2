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
 * Geometry converted automatically from the 1.12.2 ModelPredalien by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class PredalienModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "predalien"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart faceTop;
    public final ModelPart midJaw;
    public final ModelPart leftUpperMandible;
    public final ModelPart headTop;
    public final ModelPart rightLowerMandible2;
    public final ModelPart leftLowerTooth;
    public final ModelPart dread3;
    public final ModelPart rightLowerTooth;
    public final ModelPart dread2;
    public final ModelPart leftUpperTooth;
    public final ModelPart leftLowerMandible2;
    public final ModelPart midHeadSpike;
    public final ModelPart dread4;
    public final ModelPart headTopRidge;
    public final ModelPart leftLowerMandible;
    public final ModelPart faceMid;
    public final ModelPart rightUpperMandible;
    public final ModelPart dread8;
    public final ModelPart rightUpperTooth;
    public final ModelPart topTeeth;
    public final ModelPart rightJawWebbing;
    public final ModelPart dread6;
    public final ModelPart leftJawWebbing;
    public final ModelPart bottomTeeth;
    public final ModelPart dread5;
    public final ModelPart rightInnerJawWebbing;
    public final ModelPart rightLowerMandible;
    public final ModelPart dread1;
    public final ModelPart dread7;
    public final ModelPart leftInnerJawWebbing;
    public final ModelPart leftThigh;
    public final ModelPart leftFoot;
    public final ModelPart leftFootToe3;
    public final ModelPart leftFootToe2;
    public final ModelPart leftFootBackToe;
    public final ModelPart leftFootToe1;
    public final ModelPart leftShin;
    public final ModelPart topTorso;
    public final ModelPart neck;
    public final ModelPart abdomin;
    public final ModelPart backSpikes;
    public final ModelPart rightTopSpike;
    public final ModelPart RightThigh;
    public final ModelPart rightFoot;
    public final ModelPart rightFootBackToe;
    public final ModelPart rightFootToe2;
    public final ModelPart rightFootToe1;
    public final ModelPart rightFootToe3;
    public final ModelPart rightShin;
    public final ModelPart rightLowerSpike;
    public final ModelPart leftTopSpike;
    public final ModelPart leftShoulder;
    public final ModelPart leftForearm;
    public final ModelPart leftBicep;
    public final ModelPart leftFinger1;
    public final ModelPart leftFinger4;
    public final ModelPart leftFinger3;
    public final ModelPart leftFinger2;
    public final ModelPart rightShoulder;
    public final ModelPart rightFinger3;
    public final ModelPart rightFinger4;
    public final ModelPart rightForearm;
    public final ModelPart rightFinger1;
    public final ModelPart rightBicep;
    public final ModelPart rightFinger2;
    public final ModelPart leftLowerSpike;
    public final ModelPart tailBase;
    public final ModelPart tailSpines0;
    public final ModelPart tail1;
    public final ModelPart tailSpines1;
    public final ModelPart tail2;
    public final ModelPart tailSpines2;
    public final ModelPart tail3;
    public final ModelPart tailSpines3;
    public final ModelPart tail4;
    public final ModelPart tailStabber;
    public final ModelPart tailSpines4;

    public PredalienModel(ModelPart root)
    {
        this.root = root;
        this.faceTop = root.getChild("faceTop");
        this.parts.put("faceTop", this.faceTop);
        this.midJaw = this.faceTop.getChild("midJaw");
        this.parts.put("midJaw", this.midJaw);
        this.leftUpperMandible = this.faceTop.getChild("leftUpperMandible");
        this.parts.put("leftUpperMandible", this.leftUpperMandible);
        this.headTop = this.faceTop.getChild("headTop");
        this.parts.put("headTop", this.headTop);
        this.rightLowerMandible2 = this.faceTop.getChild("rightLowerMandible2");
        this.parts.put("rightLowerMandible2", this.rightLowerMandible2);
        this.leftLowerTooth = this.faceTop.getChild("leftLowerTooth");
        this.parts.put("leftLowerTooth", this.leftLowerTooth);
        this.dread3 = this.faceTop.getChild("dread3");
        this.parts.put("dread3", this.dread3);
        this.rightLowerTooth = this.faceTop.getChild("rightLowerTooth");
        this.parts.put("rightLowerTooth", this.rightLowerTooth);
        this.dread2 = this.faceTop.getChild("dread2");
        this.parts.put("dread2", this.dread2);
        this.leftUpperTooth = this.faceTop.getChild("leftUpperTooth");
        this.parts.put("leftUpperTooth", this.leftUpperTooth);
        this.leftLowerMandible2 = this.faceTop.getChild("leftLowerMandible2");
        this.parts.put("leftLowerMandible2", this.leftLowerMandible2);
        this.midHeadSpike = this.faceTop.getChild("midHeadSpike");
        this.parts.put("midHeadSpike", this.midHeadSpike);
        this.dread4 = this.faceTop.getChild("dread4");
        this.parts.put("dread4", this.dread4);
        this.headTopRidge = this.faceTop.getChild("headTopRidge");
        this.parts.put("headTopRidge", this.headTopRidge);
        this.leftLowerMandible = this.faceTop.getChild("leftLowerMandible");
        this.parts.put("leftLowerMandible", this.leftLowerMandible);
        this.faceMid = this.faceTop.getChild("faceMid");
        this.parts.put("faceMid", this.faceMid);
        this.rightUpperMandible = this.faceTop.getChild("rightUpperMandible");
        this.parts.put("rightUpperMandible", this.rightUpperMandible);
        this.dread8 = this.faceTop.getChild("dread8");
        this.parts.put("dread8", this.dread8);
        this.rightUpperTooth = this.faceTop.getChild("rightUpperTooth");
        this.parts.put("rightUpperTooth", this.rightUpperTooth);
        this.topTeeth = this.faceTop.getChild("topTeeth");
        this.parts.put("topTeeth", this.topTeeth);
        this.rightJawWebbing = this.faceTop.getChild("rightJawWebbing");
        this.parts.put("rightJawWebbing", this.rightJawWebbing);
        this.dread6 = this.faceTop.getChild("dread6");
        this.parts.put("dread6", this.dread6);
        this.leftJawWebbing = this.faceTop.getChild("leftJawWebbing");
        this.parts.put("leftJawWebbing", this.leftJawWebbing);
        this.bottomTeeth = this.faceTop.getChild("bottomTeeth");
        this.parts.put("bottomTeeth", this.bottomTeeth);
        this.dread5 = this.faceTop.getChild("dread5");
        this.parts.put("dread5", this.dread5);
        this.rightInnerJawWebbing = this.faceTop.getChild("rightInnerJawWebbing");
        this.parts.put("rightInnerJawWebbing", this.rightInnerJawWebbing);
        this.rightLowerMandible = this.faceTop.getChild("rightLowerMandible");
        this.parts.put("rightLowerMandible", this.rightLowerMandible);
        this.dread1 = this.faceTop.getChild("dread1");
        this.parts.put("dread1", this.dread1);
        this.dread7 = this.faceTop.getChild("dread7");
        this.parts.put("dread7", this.dread7);
        this.leftInnerJawWebbing = this.faceTop.getChild("leftInnerJawWebbing");
        this.parts.put("leftInnerJawWebbing", this.leftInnerJawWebbing);
        this.leftThigh = root.getChild("leftThigh");
        this.parts.put("leftThigh", this.leftThigh);
        this.leftFoot = this.leftThigh.getChild("leftFoot");
        this.parts.put("leftFoot", this.leftFoot);
        this.leftFootToe3 = this.leftFoot.getChild("leftFootToe3");
        this.parts.put("leftFootToe3", this.leftFootToe3);
        this.leftFootToe2 = this.leftFoot.getChild("leftFootToe2");
        this.parts.put("leftFootToe2", this.leftFootToe2);
        this.leftFootBackToe = this.leftFoot.getChild("leftFootBackToe");
        this.parts.put("leftFootBackToe", this.leftFootBackToe);
        this.leftFootToe1 = this.leftFoot.getChild("leftFootToe1");
        this.parts.put("leftFootToe1", this.leftFootToe1);
        this.leftShin = this.leftThigh.getChild("leftShin");
        this.parts.put("leftShin", this.leftShin);
        this.topTorso = root.getChild("topTorso");
        this.parts.put("topTorso", this.topTorso);
        this.neck = this.topTorso.getChild("neck");
        this.parts.put("neck", this.neck);
        this.abdomin = this.topTorso.getChild("abdomin");
        this.parts.put("abdomin", this.abdomin);
        this.backSpikes = this.topTorso.getChild("backSpikes");
        this.parts.put("backSpikes", this.backSpikes);
        this.rightTopSpike = root.getChild("rightTopSpike");
        this.parts.put("rightTopSpike", this.rightTopSpike);
        this.RightThigh = root.getChild("RightThigh");
        this.parts.put("RightThigh", this.RightThigh);
        this.rightFoot = this.RightThigh.getChild("rightFoot");
        this.parts.put("rightFoot", this.rightFoot);
        this.rightFootBackToe = this.rightFoot.getChild("rightFootBackToe");
        this.parts.put("rightFootBackToe", this.rightFootBackToe);
        this.rightFootToe2 = this.rightFoot.getChild("rightFootToe2");
        this.parts.put("rightFootToe2", this.rightFootToe2);
        this.rightFootToe1 = this.rightFoot.getChild("rightFootToe1");
        this.parts.put("rightFootToe1", this.rightFootToe1);
        this.rightFootToe3 = this.rightFoot.getChild("rightFootToe3");
        this.parts.put("rightFootToe3", this.rightFootToe3);
        this.rightShin = this.RightThigh.getChild("rightShin");
        this.parts.put("rightShin", this.rightShin);
        this.rightLowerSpike = root.getChild("rightLowerSpike");
        this.parts.put("rightLowerSpike", this.rightLowerSpike);
        this.leftTopSpike = root.getChild("leftTopSpike");
        this.parts.put("leftTopSpike", this.leftTopSpike);
        this.leftShoulder = root.getChild("leftShoulder");
        this.parts.put("leftShoulder", this.leftShoulder);
        this.leftForearm = this.leftShoulder.getChild("leftForearm");
        this.parts.put("leftForearm", this.leftForearm);
        this.leftBicep = this.leftShoulder.getChild("leftBicep");
        this.parts.put("leftBicep", this.leftBicep);
        this.leftFinger1 = this.leftShoulder.getChild("leftFinger1");
        this.parts.put("leftFinger1", this.leftFinger1);
        this.leftFinger4 = this.leftShoulder.getChild("leftFinger4");
        this.parts.put("leftFinger4", this.leftFinger4);
        this.leftFinger3 = this.leftShoulder.getChild("leftFinger3");
        this.parts.put("leftFinger3", this.leftFinger3);
        this.leftFinger2 = this.leftShoulder.getChild("leftFinger2");
        this.parts.put("leftFinger2", this.leftFinger2);
        this.rightShoulder = root.getChild("rightShoulder");
        this.parts.put("rightShoulder", this.rightShoulder);
        this.rightFinger3 = this.rightShoulder.getChild("rightFinger3");
        this.parts.put("rightFinger3", this.rightFinger3);
        this.rightFinger4 = this.rightShoulder.getChild("rightFinger4");
        this.parts.put("rightFinger4", this.rightFinger4);
        this.rightForearm = this.rightShoulder.getChild("rightForearm");
        this.parts.put("rightForearm", this.rightForearm);
        this.rightFinger1 = this.rightShoulder.getChild("rightFinger1");
        this.parts.put("rightFinger1", this.rightFinger1);
        this.rightBicep = this.rightShoulder.getChild("rightBicep");
        this.parts.put("rightBicep", this.rightBicep);
        this.rightFinger2 = this.rightShoulder.getChild("rightFinger2");
        this.parts.put("rightFinger2", this.rightFinger2);
        this.leftLowerSpike = root.getChild("leftLowerSpike");
        this.parts.put("leftLowerSpike", this.leftLowerSpike);
        this.tailBase = root.getChild("tailBase");
        this.parts.put("tailBase", this.tailBase);
        this.tailSpines0 = this.tailBase.getChild("tailSpines0");
        this.parts.put("tailSpines0", this.tailSpines0);
        this.tail1 = this.tailBase.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tailSpines1 = this.tail1.getChild("tailSpines1");
        this.parts.put("tailSpines1", this.tailSpines1);
        this.tail2 = this.tail1.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tailSpines2 = this.tail2.getChild("tailSpines2");
        this.parts.put("tailSpines2", this.tailSpines2);
        this.tail3 = this.tail2.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tailSpines3 = this.tail3.getChild("tailSpines3");
        this.parts.put("tailSpines3", this.tailSpines3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tailStabber = this.tail4.getChild("tailStabber");
        this.parts.put("tailStabber", this.tailStabber);
        this.tailSpines4 = this.tail4.getChild("tailSpines4");
        this.parts.put("tailSpines4", this.tailSpines4);
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
        PartDefinition p_faceTop = root.addOrReplaceChild("faceTop",
                CubeListBuilder.create().texOffs(130, 101).addBox(-3.5F, -7.3F, -9.2F, 7.0F, 8.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -18.3F, 0.0F, -1.0122909661567112F, 0.0F, 0.0F));
        PartDefinition p_midJaw = p_faceTop.addOrReplaceChild("midJaw",
                CubeListBuilder.create().texOffs(95, 136).addBox(-1.5F, -2.0F, -5.5F, 3.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, 0.0F, 0.0F));
        PartDefinition p_leftUpperMandible = p_faceTop.addOrReplaceChild("leftUpperMandible",
                CubeListBuilder.create().texOffs(103, 113).addBox(2.5F, -4.0F, -7.5F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0016444577195458F, -0.091106186954104F, -0.22759093446006054F));
        PartDefinition p_headTop = p_faceTop.addOrReplaceChild("headTop",
                CubeListBuilder.create().texOffs(57, 51).addBox(-3.5F, -19.6F, -11.5F, 7.0F, 17.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_rightLowerMandible2 = p_faceTop.addOrReplaceChild("rightLowerMandible2",
                CubeListBuilder.create().texOffs(99, 85).addBox(-3.7F, -1.8F, -7.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, -0.18203784098300857F, 0.22759093446006054F));
        PartDefinition p_leftLowerTooth = p_faceTop.addOrReplaceChild("leftLowerTooth",
                CubeListBuilder.create().texOffs(145, 93).addBox(3.1F, -3.7F, -7.4F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, 0.18203784098300857F, -0.22759093446006054F));
        PartDefinition p_dread3 = p_faceTop.addOrReplaceChild("dread3",
                CubeListBuilder.create().texOffs(0, 111).addBox(-8.5F, -8.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_rightLowerTooth = p_faceTop.addOrReplaceChild("rightLowerTooth",
                CubeListBuilder.create().texOffs(145, 97).addBox(-3.2F, -3.7F, -7.4F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, -0.18203784098300857F, 0.22759093446006054F));
        PartDefinition p_dread2 = p_faceTop.addOrReplaceChild("dread2",
                CubeListBuilder.create().texOffs(0, 123).addBox(-8.5F, -6.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_leftUpperTooth = p_faceTop.addOrReplaceChild("leftUpperTooth",
                CubeListBuilder.create().texOffs(99, 77).addBox(2.0F, -4.2F, -6.6F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0122909661567112F, 0.0F, -0.05235987755982988F));
        PartDefinition p_leftLowerMandible2 = p_faceTop.addOrReplaceChild("leftLowerMandible2",
                CubeListBuilder.create().texOffs(99, 81).addBox(2.6F, -1.8F, -7.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, 0.18203784098300857F, -0.22759093446006054F));
        PartDefinition p_midHeadSpike = p_faceTop.addOrReplaceChild("midHeadSpike",
                CubeListBuilder.create().texOffs(57, 113).addBox(0.0F, -19.6F, -15.5F, 0.0F, 26.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_dread4 = p_faceTop.addOrReplaceChild("dread4",
                CubeListBuilder.create().texOffs(0, 99).addBox(-8.5F, -10.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_headTopRidge = p_faceTop.addOrReplaceChild("headTopRidge",
                CubeListBuilder.create().texOffs(78, 113).addBox(-4.5F, -20.6F, -10.5F, 9.0F, 18.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_leftLowerMandible = p_faceTop.addOrReplaceChild("leftLowerMandible",
                CubeListBuilder.create().texOffs(99, 90).addBox(1.9F, -0.5F, -7.4F, 1.0F, 1.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1383037381507017F, 0.136659280431156F, -0.31869712141416456F));
        PartDefinition p_faceMid = p_faceTop.addOrReplaceChild("faceMid",
                CubeListBuilder.create().texOffs(99, 101).addBox(-3.5F, -6.5F, -6.6F, 7.0F, 3.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8552113334772213F, 0.0F, 0.0F));
        PartDefinition p_rightUpperMandible = p_faceTop.addOrReplaceChild("rightUpperMandible",
                CubeListBuilder.create().texOffs(122, 115).addBox(-3.5F, -4.0F, -7.5F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0016444577195458F, 0.091106186954104F, 0.22759093446006054F));
        PartDefinition p_dread8 = p_faceTop.addOrReplaceChild("dread8",
                CubeListBuilder.create().texOffs(0, 63).addBox(-8.5F, -18.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_rightUpperTooth = p_faceTop.addOrReplaceChild("rightUpperTooth",
                CubeListBuilder.create().texOffs(99, 79).addBox(-4.0F, -4.2F, -6.6F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0122909661567112F, 0.0F, 0.05235987755982988F));
        PartDefinition p_topTeeth = p_faceTop.addOrReplaceChild("topTeeth",
                CubeListBuilder.create().texOffs(148, 95).addBox(-2.0F, -4.0F, -6.5F, 4.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8552113334772213F, 0.0F, 0.0F));
        PartDefinition p_rightJawWebbing = p_faceTop.addOrReplaceChild("rightJawWebbing",
                CubeListBuilder.create().texOffs(86, 51).addBox(-2.4F, -3.9F, -6.3F, 0.0F, 3.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1383037381507017F, 0.091106186954104F, 0.22759093446006054F));
        PartDefinition p_dread6 = p_faceTop.addOrReplaceChild("dread6",
                CubeListBuilder.create().texOffs(0, 75).addBox(-8.5F, -14.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_leftJawWebbing = p_faceTop.addOrReplaceChild("leftJawWebbing",
                CubeListBuilder.create().texOffs(78, 135).addBox(2.4F, -3.9F, -6.3F, 0.0F, 3.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1383037381507017F, -0.091106186954104F, -0.22759093446006054F));
        PartDefinition p_bottomTeeth = p_faceTop.addOrReplaceChild("bottomTeeth",
                CubeListBuilder.create().texOffs(148, 98).addBox(-1.4F, -2.5F, -5.5F, 3.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0122909661567112F, 0.0F, 0.0F));
        PartDefinition p_dread5 = p_faceTop.addOrReplaceChild("dread5",
                CubeListBuilder.create().texOffs(0, 87).addBox(-8.5F, -12.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_rightInnerJawWebbing = p_faceTop.addOrReplaceChild("rightInnerJawWebbing",
                CubeListBuilder.create().texOffs(86, 63).addBox(-1.2F, -6.1F, -4.0F, 0.0F, 4.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, 0.136659280431156F, -0.091106186954104F));
        PartDefinition p_rightLowerMandible = p_faceTop.addOrReplaceChild("rightLowerMandible",
                CubeListBuilder.create().texOffs(99, 90).addBox(-2.9F, -0.5F, -7.5F, 1.0F, 1.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1383037381507017F, -0.136659280431156F, 0.31869712141416456F));
        PartDefinition p_dread1 = p_faceTop.addOrReplaceChild("dread1",
                CubeListBuilder.create().texOffs(0, 135).addBox(-8.5F, -4.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_dread7 = p_faceTop.addOrReplaceChild("dread7",
                CubeListBuilder.create().texOffs(0, 51).addBox(-8.5F, -16.6F, -10.5F, 17.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_leftInnerJawWebbing = p_faceTop.addOrReplaceChild("leftInnerJawWebbing",
                CubeListBuilder.create().texOffs(103, 123).addBox(1.2F, -6.1F, -4.0F, 0.0F, 4.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2042771838760875F, -0.136659280431156F, 0.091106186954104F));
        PartDefinition p_leftThigh = root.addOrReplaceChild("leftThigh",
                CubeListBuilder.create().texOffs(122, 69).addBox(-1.5F, -2.0F, -2.0F, 5.0F, 16.0F, 5.0F),
                PartPose.offsetAndRotation(1.0F, -2.3F, 7.0F, -0.47123889803846897F, -0.3490658503988659F, -0.24434609527920614F));
        PartDefinition p_leftFoot = p_leftThigh.addOrReplaceChild("leftFoot",
                CubeListBuilder.create().texOffs(122, 91).addBox(-0.6F, -0.7F, -5.2F, 4.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(-2.7F, 22.9F, 10.6F, 0.5462880558742251F, 0.045553093477052F, 0.27314402793711257F));
        PartDefinition p_leftFootToe3 = p_leftFoot.addOrReplaceChild("leftFootToe3",
                CubeListBuilder.create().texOffs(181, 69).addBox(2.2F, -1.7F, -11.5F, 0.0F, 3.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F));
        PartDefinition p_leftFootToe2 = p_leftFoot.addOrReplaceChild("leftFootToe2",
                CubeListBuilder.create().texOffs(143, 69).addBox(1.7F, -1.7F, -11.5F, 0.0F, 3.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_leftFootBackToe = p_leftFoot.addOrReplaceChild("leftFootBackToe",
                CubeListBuilder.create().texOffs(143, 82).addBox(1.3F, -2.9F, 0.5F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.40980330836826856F, 0.045553093477052F, 0.0F));
        PartDefinition p_leftFootToe1 = p_leftFoot.addOrReplaceChild("leftFootToe1",
                CubeListBuilder.create().texOffs(162, 69).addBox(0.8F, -1.7F, -11.5F, 0.0F, 3.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.136659280431156F, 0.0F));
        PartDefinition p_leftShin = p_leftThigh.addOrReplaceChild("leftShin",
                CubeListBuilder.create().texOffs(105, 69).addBox(2.5F, 9.0F, -9.3F, 4.0F, 15.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.045553093477052F, 0.27314402793711257F));
        PartDefinition p_topTorso = root.addOrReplaceChild("topTorso",
                CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.6F, -4.0F, 12.0F, 8.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, -16.3F, 5.0F, 0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_neck = p_topTorso.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 34).addBox(-2.5F, -8.0F, -2.3F, 5.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_abdomin = p_topTorso.addOrReplaceChild("abdomin",
                CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 4.4F, -3.5F, 8.0F, 10.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.10471975511965977F, 0.0F, 0.0F));
        PartDefinition p_backSpikes = p_topTorso.addOrReplaceChild("backSpikes",
                CubeListBuilder.create().texOffs(29, 17).addBox(0.0F, -1.6F, 3.0F, 0.0F, 15.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rightTopSpike = root.addOrReplaceChild("rightTopSpike",
                CubeListBuilder.create().texOffs(41, 0).mirror().addBox(0.5F, 0.27F, 2.0F, 2.0F, 2.0F, 14.0F),
                PartPose.offsetAndRotation(-4.0F, -16.3F, 5.0F, 0.20943951023931953F, -0.47123889803846897F, -0.40142572795869574F));
        PartDefinition p_RightThigh = root.addOrReplaceChild("RightThigh",
                CubeListBuilder.create().texOffs(122, 69).mirror().addBox(-3.5F, -2.0F, -2.0F, 5.0F, 16.0F, 5.0F),
                PartPose.offsetAndRotation(-1.0F, -2.3F, 7.0F, -0.47123889803846897F, 0.3490658503988659F, 0.24434609527920614F));
        PartDefinition p_rightFoot = p_RightThigh.addOrReplaceChild("rightFoot",
                CubeListBuilder.create().texOffs(122, 91).mirror().addBox(-0.6F, -0.3F, -5.2F, 4.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 23.3F, 10.3F, 0.5462880558742251F, -0.045553093477052F, -0.27314402793711257F));
        PartDefinition p_rightFootBackToe = p_rightFoot.addOrReplaceChild("rightFootBackToe",
                CubeListBuilder.create().texOffs(143, 82).addBox(1.6F, -2.9F, 0.5F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.40980330836826856F, -0.045553093477052F, 0.0F));
        PartDefinition p_rightFootToe2 = p_rightFoot.addOrReplaceChild("rightFootToe2",
                CubeListBuilder.create().texOffs(143, 69).addBox(1.1F, -1.3F, -11.5F, 0.0F, 3.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rightFootToe1 = p_rightFoot.addOrReplaceChild("rightFootToe1",
                CubeListBuilder.create().texOffs(162, 69).addBox(2.0F, -1.3F, -11.5F, 0.0F, 3.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F));
        PartDefinition p_rightFootToe3 = p_rightFoot.addOrReplaceChild("rightFootToe3",
                CubeListBuilder.create().texOffs(181, 69).addBox(0.6F, -1.3F, -11.5F, 0.0F, 3.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.136659280431156F, 0.0F));
        PartDefinition p_rightShin = p_RightThigh.addOrReplaceChild("rightShin",
                CubeListBuilder.create().texOffs(105, 69).mirror().addBox(-6.4F, 9.0F, -9.3F, 4.0F, 15.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, -0.045553093477052F, -0.27314402793711257F));
        PartDefinition p_rightLowerSpike = root.addOrReplaceChild("rightLowerSpike",
                CubeListBuilder.create().texOffs(46, 17).mirror().addBox(0.23F, 2.27F, 3.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-4.0F, -16.3F, 5.0F, -0.20943951023931953F, -0.47123889803846897F, -0.40142572795869574F));
        PartDefinition p_leftTopSpike = root.addOrReplaceChild("leftTopSpike",
                CubeListBuilder.create().texOffs(41, 0).addBox(-0.5F, -0.3F, 3.0F, 2.0F, 2.0F, 14.0F),
                PartPose.offsetAndRotation(1.5F, -16.3F, 5.0F, 0.20943951023931953F, 0.47123889803846897F, 0.40142572795869574F));
        PartDefinition p_leftShoulder = root.addOrReplaceChild("leftShoulder",
                CubeListBuilder.create().texOffs(161, 55).addBox(-4.5F, -3.6F, -3.5F, 6.0F, 6.0F, 6.0F),
                PartPose.offsetAndRotation(6.0F, -15.3F, 6.0F, 0.13962634015954636F, 0.0F, -0.5235987755982988F));
        PartDefinition p_leftForearm = p_leftShoulder.addOrReplaceChild("leftForearm",
                CubeListBuilder.create().texOffs(133, 52).addBox(-3.3F, 7.4F, 3.6F, 3.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.0F, -0.03717551306747922F));
        PartDefinition p_leftBicep = p_leftShoulder.addOrReplaceChild("leftBicep",
                CubeListBuilder.create().texOffs(144, 51).addBox(-3.5F, -1.6F, -2.5F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_leftFinger1 = p_leftShoulder.addOrReplaceChild("leftFinger1",
                CubeListBuilder.create().texOffs(126, 55).addBox(2.4F, 16.7F, 0.4F, 0.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3968878719035105F, 0.2617993877991494F, 0.18203784098300857F));
        PartDefinition p_leftFinger4 = p_leftShoulder.addOrReplaceChild("leftFinger4",
                CubeListBuilder.create().texOffs(105, 55).addBox(-2.4F, 16.8F, 2.6F, 0.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.0F, -0.14189526818713902F));
        PartDefinition p_leftFinger3 = p_leftShoulder.addOrReplaceChild("leftFinger3",
                CubeListBuilder.create().texOffs(112, 54).addBox(-1.1F, 16.9F, 1.7F, 0.0F, 10.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.15707963267948966F, -0.091106186954104F));
        PartDefinition p_leftFinger2 = p_leftShoulder.addOrReplaceChild("leftFinger2",
                CubeListBuilder.create().texOffs(119, 54).addBox(0.7F, 16.9F, 2.0F, 0.0F, 10.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.17453292519943295F, 0.045553093477052F));
        PartDefinition p_rightShoulder = root.addOrReplaceChild("rightShoulder",
                CubeListBuilder.create().texOffs(161, 55).mirror().addBox(-1.5F, -3.6F, -3.5F, 6.0F, 6.0F, 6.0F),
                PartPose.offsetAndRotation(-6.0F, -15.3F, 6.0F, 0.13962634015954636F, 0.0F, 0.5235987755982988F));
        PartDefinition p_rightFinger3 = p_rightShoulder.addOrReplaceChild("rightFinger3",
                CubeListBuilder.create().texOffs(112, 54).addBox(1.4F, 16.9F, 1.7F, 0.0F, 10.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, -0.15707963267948966F, 0.091106186954104F));
        PartDefinition p_rightFinger4 = p_rightShoulder.addOrReplaceChild("rightFinger4",
                CubeListBuilder.create().texOffs(105, 55).addBox(2.6F, 16.8F, 2.6F, 0.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.0F, 0.14189526818713902F));
        PartDefinition p_rightForearm = p_rightShoulder.addOrReplaceChild("rightForearm",
                CubeListBuilder.create().texOffs(133, 52).mirror().addBox(0.5F, 7.4F, 3.6F, 3.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.0F, 0.03717551306747922F));
        PartDefinition p_rightFinger1 = p_rightShoulder.addOrReplaceChild("rightFinger1",
                CubeListBuilder.create().texOffs(126, 55).addBox(-2.4F, 16.7F, 0.7F, 0.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3968878719035105F, -0.2617993877991494F, -0.18291050560900574F));
        PartDefinition p_rightBicep = p_rightShoulder.addOrReplaceChild("rightBicep",
                CubeListBuilder.create().texOffs(144, 51).mirror().addBox(-0.5F, -1.6F, -2.5F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rightFinger2 = p_rightShoulder.addOrReplaceChild("rightFinger2",
                CubeListBuilder.create().texOffs(119, 54).addBox(-0.5F, 16.9F, 2.0F, 0.0F, 10.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, -0.17453292519943295F, -0.045553093477052F));
        PartDefinition p_leftLowerSpike = root.addOrReplaceChild("leftLowerSpike",
                CubeListBuilder.create().texOffs(46, 17).addBox(-0.2F, 1.0F, 3.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(1.5F, -16.3F, 5.0F, -0.20943951023931953F, 0.47123889803846897F, 0.40142572795869574F));
        PartDefinition p_tailBase = root.addOrReplaceChild("tailBase",
                CubeListBuilder.create().texOffs(49, 33).addBox(-3.0F, 0.07F, -3.0F, 6.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -3.3F, 8.0F, 0.6981317007977318F, 0.0F, 0.0F));
        PartDefinition p_tailSpines0 = p_tailBase.addOrReplaceChild("tailSpines0",
                CubeListBuilder.create().texOffs(122, 0).addBox(0.0F, 0.4F, 1.7F, 0.0F, 9.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail1 = p_tailBase.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(76, 0).addBox(-2.5F, -0.93F, -3.0F, 5.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 9.4F, 0.5F, 0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_tailSpines1 = p_tail1.addOrReplaceChild("tailSpines1",
                CubeListBuilder.create().texOffs(122, 12).addBox(0.0F, 0.1F, 1.3F, 0.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(76, 15).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 9.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 8.5F, -0.7F, 0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_tailSpines2 = p_tail2.addOrReplaceChild("tailSpines2",
                CubeListBuilder.create().texOffs(122, 23).addBox(0.0F, 0.0F, 0.8F, 0.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(76, 29).addBox(-1.5F, -2.6F, -1.0F, 3.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_tailSpines3 = p_tail3.addOrReplaceChild("tailSpines3",
                CubeListBuilder.create().texOffs(122, 36).addBox(0.0F, -1.7F, 0.9F, 0.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(89, 29).addBox(-1.0F, -0.1F, -1.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.3F, 0.5F, 0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_tailStabber = p_tail4.addOrReplaceChild("tailStabber",
                CubeListBuilder.create().texOffs(97, 0).addBox(0.0F, -0.4F, -1.5F, 0.0F, 10.0F, 5.0F),
                PartPose.offset(0.0F, 9.2F, 0.0F));
        PartDefinition p_tailSpines4 = p_tail4.addOrReplaceChild("tailSpines4",
                CubeListBuilder.create().texOffs(135, 36).addBox(0.0F, 0.5F, -0.3F, 0.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 256);
    }
}
