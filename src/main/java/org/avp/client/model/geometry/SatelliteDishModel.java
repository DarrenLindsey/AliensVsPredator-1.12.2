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
 * Geometry converted automatically from the 1.12.2 ModelSatelliteDish by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class SatelliteDishModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "satellite_dish"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart discBaseN;
    public final ModelPart latticeW;
    public final ModelPart latticeN;
    public final ModelPart discBaseW;
    public final ModelPart spokeW;
    public final ModelPart latticeE;
    public final ModelPart discBaseE;
    public final ModelPart spokeE;
    public final ModelPart panelNNW;
    public final ModelPart discBaseNW;
    public final ModelPart spokeNW;
    public final ModelPart subreflector;
    public final ModelPart panelENE;
    public final ModelPart discBaseSW;
    public final ModelPart spokeSW;
    public final ModelPart spokeN;
    public final ModelPart panelWNW;
    public final ModelPart panelESE;
    public final ModelPart panelNNE;
    public final ModelPart latticeS;
    public final ModelPart panelSSE;
    public final ModelPart panelSSW;
    public final ModelPart discBaseS;
    public final ModelPart spokeS;
    public final ModelPart panelWSW;
    public final ModelPart discBaseNE;
    public final ModelPart spokeNE;
    public final ModelPart discBaseSE;
    public final ModelPart spokeSE;
    public final ModelPart base;
    public final ModelPart baseLatticeE2;
    public final ModelPart swivelJointConnector;
    public final ModelPart baseLatticeW2;
    public final ModelPart baseLatticeS2;
    public final ModelPart baseLatticeN2;
    public final ModelPart baseLatticeS;
    public final ModelPart baseLatticeE;
    public final ModelPart baseLatticeW;
    public final ModelPart swivelJoint;
    public final ModelPart baseLatticeN;

    public SatelliteDishModel(ModelPart root)
    {
        this.root = root;
        this.discBaseN = root.getChild("discBaseN");
        this.parts.put("discBaseN", this.discBaseN);
        this.latticeW = this.discBaseN.getChild("latticeW");
        this.parts.put("latticeW", this.latticeW);
        this.latticeN = this.discBaseN.getChild("latticeN");
        this.parts.put("latticeN", this.latticeN);
        this.discBaseW = this.discBaseN.getChild("discBaseW");
        this.parts.put("discBaseW", this.discBaseW);
        this.spokeW = this.discBaseW.getChild("spokeW");
        this.parts.put("spokeW", this.spokeW);
        this.latticeE = this.discBaseN.getChild("latticeE");
        this.parts.put("latticeE", this.latticeE);
        this.discBaseE = this.discBaseN.getChild("discBaseE");
        this.parts.put("discBaseE", this.discBaseE);
        this.spokeE = this.discBaseE.getChild("spokeE");
        this.parts.put("spokeE", this.spokeE);
        this.panelNNW = this.discBaseN.getChild("panelNNW");
        this.parts.put("panelNNW", this.panelNNW);
        this.discBaseNW = this.discBaseN.getChild("discBaseNW");
        this.parts.put("discBaseNW", this.discBaseNW);
        this.spokeNW = this.discBaseNW.getChild("spokeNW");
        this.parts.put("spokeNW", this.spokeNW);
        this.subreflector = this.discBaseN.getChild("subreflector");
        this.parts.put("subreflector", this.subreflector);
        this.panelENE = this.discBaseN.getChild("panelENE");
        this.parts.put("panelENE", this.panelENE);
        this.discBaseSW = this.discBaseN.getChild("discBaseSW");
        this.parts.put("discBaseSW", this.discBaseSW);
        this.spokeSW = this.discBaseSW.getChild("spokeSW");
        this.parts.put("spokeSW", this.spokeSW);
        this.spokeN = this.discBaseN.getChild("spokeN");
        this.parts.put("spokeN", this.spokeN);
        this.panelWNW = this.discBaseN.getChild("panelWNW");
        this.parts.put("panelWNW", this.panelWNW);
        this.panelESE = this.discBaseN.getChild("panelESE");
        this.parts.put("panelESE", this.panelESE);
        this.panelNNE = this.discBaseN.getChild("panelNNE");
        this.parts.put("panelNNE", this.panelNNE);
        this.latticeS = this.discBaseN.getChild("latticeS");
        this.parts.put("latticeS", this.latticeS);
        this.panelSSE = this.discBaseN.getChild("panelSSE");
        this.parts.put("panelSSE", this.panelSSE);
        this.panelSSW = this.discBaseN.getChild("panelSSW");
        this.parts.put("panelSSW", this.panelSSW);
        this.discBaseS = this.discBaseN.getChild("discBaseS");
        this.parts.put("discBaseS", this.discBaseS);
        this.spokeS = this.discBaseS.getChild("spokeS");
        this.parts.put("spokeS", this.spokeS);
        this.panelWSW = this.discBaseN.getChild("panelWSW");
        this.parts.put("panelWSW", this.panelWSW);
        this.discBaseNE = this.discBaseN.getChild("discBaseNE");
        this.parts.put("discBaseNE", this.discBaseNE);
        this.spokeNE = this.discBaseNE.getChild("spokeNE");
        this.parts.put("spokeNE", this.spokeNE);
        this.discBaseSE = this.discBaseN.getChild("discBaseSE");
        this.parts.put("discBaseSE", this.discBaseSE);
        this.spokeSE = this.discBaseSE.getChild("spokeSE");
        this.parts.put("spokeSE", this.spokeSE);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.baseLatticeE2 = root.getChild("baseLatticeE2");
        this.parts.put("baseLatticeE2", this.baseLatticeE2);
        this.swivelJointConnector = root.getChild("swivelJointConnector");
        this.parts.put("swivelJointConnector", this.swivelJointConnector);
        this.baseLatticeW2 = root.getChild("baseLatticeW2");
        this.parts.put("baseLatticeW2", this.baseLatticeW2);
        this.baseLatticeS2 = root.getChild("baseLatticeS2");
        this.parts.put("baseLatticeS2", this.baseLatticeS2);
        this.baseLatticeN2 = root.getChild("baseLatticeN2");
        this.parts.put("baseLatticeN2", this.baseLatticeN2);
        this.baseLatticeS = root.getChild("baseLatticeS");
        this.parts.put("baseLatticeS", this.baseLatticeS);
        this.baseLatticeE = root.getChild("baseLatticeE");
        this.parts.put("baseLatticeE", this.baseLatticeE);
        this.baseLatticeW = root.getChild("baseLatticeW");
        this.parts.put("baseLatticeW", this.baseLatticeW);
        this.swivelJoint = root.getChild("swivelJoint");
        this.parts.put("swivelJoint", this.swivelJoint);
        this.baseLatticeN = root.getChild("baseLatticeN");
        this.parts.put("baseLatticeN", this.baseLatticeN);
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
        PartDefinition p_discBaseN = root.addOrReplaceChild("discBaseN",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 4.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, -1.7F, 0.0F));
        PartDefinition p_latticeW = p_discBaseN.addOrReplaceChild("latticeW",
                CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -16.9F, 9.3F, 2.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3839724354387525F, -1.5707963267948966F, 0.0F));
        PartDefinition p_latticeN = p_discBaseN.addOrReplaceChild("latticeN",
                CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -16.9F, 9.3F, 2.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3839724354387525F, 0.0F, 0.0F));
        PartDefinition p_discBaseW = p_discBaseN.addOrReplaceChild("discBaseW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 4.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_spokeW = p_discBaseW.addOrReplaceChild("spokeW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_latticeE = p_discBaseN.addOrReplaceChild("latticeE",
                CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -16.9F, 9.3F, 2.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3839724354387525F, 1.5707963267948966F, 0.0F));
        PartDefinition p_discBaseE = p_discBaseN.addOrReplaceChild("discBaseE",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 4.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_spokeE = p_discBaseE.addOrReplaceChild("spokeE",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_panelNNW = p_discBaseN.addOrReplaceChild("panelNNW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-5.5F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, -0.41887902047863906F, 0.0F));
        PartDefinition p_discBaseNW = p_discBaseN.addOrReplaceChild("discBaseNW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_spokeNW = p_discBaseNW.addOrReplaceChild("spokeNW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 3.141592653589793F, 0.0F));
        PartDefinition p_subreflector = p_discBaseN.addOrReplaceChild("subreflector",
                CubeListBuilder.create().texOffs(47, 18).addBox(-2.5F, -21.3F, -2.5F, 5.0F, 3.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_panelENE = p_discBaseN.addOrReplaceChild("panelENE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-5.8F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 1.1693705988362009F, 0.0F));
        PartDefinition p_discBaseSW = p_discBaseN.addOrReplaceChild("discBaseSW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_spokeSW = p_discBaseSW.addOrReplaceChild("spokeSW",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 3.141592653589793F, 0.0F));
        PartDefinition p_spokeN = p_discBaseN.addOrReplaceChild("spokeN",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_panelWNW = p_discBaseN.addOrReplaceChild("panelWNW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-5.9F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, -1.1838568316277536F, 0.0F));
        PartDefinition p_panelESE = p_discBaseN.addOrReplaceChild("panelESE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-6.2F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 1.9722220547535922F, 0.0F));
        PartDefinition p_panelNNE = p_discBaseN.addOrReplaceChild("panelNNE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-7.0F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 0.4363323129985824F, 0.0F));
        PartDefinition p_latticeS = p_discBaseN.addOrReplaceChild("latticeS",
                CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -16.9F, 9.3F, 2.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3839724354387525F, 3.141592653589793F, 0.0F));
        PartDefinition p_panelSSE = p_discBaseN.addOrReplaceChild("panelSSE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-5.5F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 2.7227136331111543F, 0.0F));
        PartDefinition p_panelSSW = p_discBaseN.addOrReplaceChild("panelSSW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-5.8F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 3.5255650890285457F, 0.0F));
        PartDefinition p_discBaseS = p_discBaseN.addOrReplaceChild("discBaseS",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 4.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_spokeS = p_discBaseS.addOrReplaceChild("spokeS",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_panelWSW = p_discBaseN.addOrReplaceChild("panelWSW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-5.8F, -20.1F, 3.7F, 12.0F, 17.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, -1.9722220547535922F, 0.0F));
        PartDefinition p_discBaseNE = p_discBaseN.addOrReplaceChild("discBaseNE",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 4.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_spokeNE = p_discBaseNE.addOrReplaceChild("spokeNE",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.2F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_discBaseSE = p_discBaseN.addOrReplaceChild("discBaseSE",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 4.0F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_spokeSE = p_discBaseSE.addOrReplaceChild("spokeSE",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(46, 31).addBox(-4.0F, 10.4F, -4.0F, 8.0F, 14.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_baseLatticeE2 = root.addOrReplaceChild("baseLatticeE2",
                CubeListBuilder.create().texOffs(84, 26).addBox(0.0F, 9.4F, -14.0F, 0.0F, 23.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, 1.5707963267948966F, 0.0F));
        PartDefinition p_swivelJointConnector = root.addOrReplaceChild("swivelJointConnector",
                CubeListBuilder.create().texOffs(75, 0).addBox(-4.0F, -1.6F, -4.0F, 8.0F, 4.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_baseLatticeW2 = root.addOrReplaceChild("baseLatticeW2",
                CubeListBuilder.create().texOffs(84, 26).addBox(0.0F, 9.4F, -14.0F, 0.0F, 23.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, -1.5707963267948966F, 0.0F));
        PartDefinition p_baseLatticeS2 = root.addOrReplaceChild("baseLatticeS2",
                CubeListBuilder.create().texOffs(84, 26).addBox(0.0F, 9.4F, -14.0F, 0.0F, 23.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, 3.141592653589793F, 0.0F));
        PartDefinition p_baseLatticeN2 = root.addOrReplaceChild("baseLatticeN2",
                CubeListBuilder.create().texOffs(84, 26).addBox(0.0F, 9.4F, -14.0F, 0.0F, 23.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, 0.0F, 0.0F));
        PartDefinition p_baseLatticeS = root.addOrReplaceChild("baseLatticeS",
                CubeListBuilder.create().texOffs(28, 22).addBox(-1.5F, 9.4F, -11.0F, 3.0F, 23.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, 3.141592653589793F, 0.0F));
        PartDefinition p_baseLatticeE = root.addOrReplaceChild("baseLatticeE",
                CubeListBuilder.create().texOffs(28, 22).addBox(-1.5F, 9.4F, -11.0F, 3.0F, 23.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, 1.5707963267948966F, 0.0F));
        PartDefinition p_baseLatticeW = root.addOrReplaceChild("baseLatticeW",
                CubeListBuilder.create().texOffs(28, 22).addBox(-1.5F, 9.4F, -11.0F, 3.0F, 23.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, -1.5707963267948966F, 0.0F));
        PartDefinition p_swivelJoint = root.addOrReplaceChild("swivelJoint",
                CubeListBuilder.create().texOffs(46, 0).addBox(-3.0F, 2.4F, -3.0F, 6.0F, 8.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_baseLatticeN = root.addOrReplaceChild("baseLatticeN",
                CubeListBuilder.create().texOffs(28, 22).addBox(-1.5F, 9.4F, -11.0F, 3.0F, 23.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0927506446736497F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
