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
 * Geometry converted automatically from the 1.12.2 ModelOvamorphGiger by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class OvamorphGigerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "ovamorph_giger"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart fleshyBase01;
    public final ModelPart root5a;
    public final ModelPart root5ba;
    public final ModelPart root5bc;
    public final ModelPart root5c;
    public final ModelPart root5d;
    public final ModelPart root5e;
    public final ModelPart root5bd;
    public final ModelPart root5bb;
    public final ModelPart fleshyBase02;
    public final ModelPart root1a;
    public final ModelPart root1ba;
    public final ModelPart root1bb;
    public final ModelPart root1bd;
    public final ModelPart root1bc;
    public final ModelPart root1c;
    public final ModelPart root1d;
    public final ModelPart root1e;
    public final ModelPart root2a;
    public final ModelPart root2ba;
    public final ModelPart root2c;
    public final ModelPart root2d;
    public final ModelPart root2e;
    public final ModelPart root2bc;
    public final ModelPart root2bb;
    public final ModelPart root2bd;
    public final ModelPart root4a;
    public final ModelPart root4ba;
    public final ModelPart root4bd;
    public final ModelPart root4bc;
    public final ModelPart root4bb;
    public final ModelPart root4c;
    public final ModelPart root4d;
    public final ModelPart root4e;
    public final ModelPart root3a;
    public final ModelPart root3ba;
    public final ModelPart root3c;
    public final ModelPart root3d;
    public final ModelPart root3e;
    public final ModelPart root3bb;
    public final ModelPart root3bc;
    public final ModelPart root3bd;
    public final ModelPart section1;
    public final ModelPart section2;
    public final ModelPart section3;
    public final ModelPart section4;
    public final ModelPart lobeB1;
    public final ModelPart lobeB2;
    public final ModelPart lobeB3;
    public final ModelPart lobeR1;
    public final ModelPart lobeR2;
    public final ModelPart lobeR3;
    public final ModelPart lobeL1;
    public final ModelPart lobeL2;
    public final ModelPart lobeL3;
    public final ModelPart lobeF1;
    public final ModelPart lobeF2;
    public final ModelPart lobeF3;

    public OvamorphGigerModel(ModelPart root)
    {
        this.root = root;
        this.fleshyBase01 = root.getChild("fleshyBase01");
        this.parts.put("fleshyBase01", this.fleshyBase01);
        this.root5a = this.fleshyBase01.getChild("root5a");
        this.parts.put("root5a", this.root5a);
        this.root5ba = this.root5a.getChild("root5ba");
        this.parts.put("root5ba", this.root5ba);
        this.root5bc = this.root5ba.getChild("root5bc");
        this.parts.put("root5bc", this.root5bc);
        this.root5c = this.root5ba.getChild("root5c");
        this.parts.put("root5c", this.root5c);
        this.root5d = this.root5c.getChild("root5d");
        this.parts.put("root5d", this.root5d);
        this.root5e = this.root5d.getChild("root5e");
        this.parts.put("root5e", this.root5e);
        this.root5bd = this.root5ba.getChild("root5bd");
        this.parts.put("root5bd", this.root5bd);
        this.root5bb = this.root5ba.getChild("root5bb");
        this.parts.put("root5bb", this.root5bb);
        this.fleshyBase02 = this.fleshyBase01.getChild("fleshyBase02");
        this.parts.put("fleshyBase02", this.fleshyBase02);
        this.root1a = this.fleshyBase01.getChild("root1a");
        this.parts.put("root1a", this.root1a);
        this.root1ba = this.root1a.getChild("root1ba");
        this.parts.put("root1ba", this.root1ba);
        this.root1bb = this.root1ba.getChild("root1bb");
        this.parts.put("root1bb", this.root1bb);
        this.root1bd = this.root1ba.getChild("root1bd");
        this.parts.put("root1bd", this.root1bd);
        this.root1bc = this.root1ba.getChild("root1bc");
        this.parts.put("root1bc", this.root1bc);
        this.root1c = this.root1ba.getChild("root1c");
        this.parts.put("root1c", this.root1c);
        this.root1d = this.root1c.getChild("root1d");
        this.parts.put("root1d", this.root1d);
        this.root1e = this.root1d.getChild("root1e");
        this.parts.put("root1e", this.root1e);
        this.root2a = this.fleshyBase01.getChild("root2a");
        this.parts.put("root2a", this.root2a);
        this.root2ba = this.root2a.getChild("root2ba");
        this.parts.put("root2ba", this.root2ba);
        this.root2c = this.root2ba.getChild("root2c");
        this.parts.put("root2c", this.root2c);
        this.root2d = this.root2c.getChild("root2d");
        this.parts.put("root2d", this.root2d);
        this.root2e = this.root2d.getChild("root2e");
        this.parts.put("root2e", this.root2e);
        this.root2bc = this.root2ba.getChild("root2bc");
        this.parts.put("root2bc", this.root2bc);
        this.root2bb = this.root2ba.getChild("root2bb");
        this.parts.put("root2bb", this.root2bb);
        this.root2bd = this.root2ba.getChild("root2bd");
        this.parts.put("root2bd", this.root2bd);
        this.root4a = this.fleshyBase01.getChild("root4a");
        this.parts.put("root4a", this.root4a);
        this.root4ba = this.root4a.getChild("root4ba");
        this.parts.put("root4ba", this.root4ba);
        this.root4bd = this.root4ba.getChild("root4bd");
        this.parts.put("root4bd", this.root4bd);
        this.root4bc = this.root4ba.getChild("root4bc");
        this.parts.put("root4bc", this.root4bc);
        this.root4bb = this.root4ba.getChild("root4bb");
        this.parts.put("root4bb", this.root4bb);
        this.root4c = this.root4ba.getChild("root4c");
        this.parts.put("root4c", this.root4c);
        this.root4d = this.root4c.getChild("root4d");
        this.parts.put("root4d", this.root4d);
        this.root4e = this.root4d.getChild("root4e");
        this.parts.put("root4e", this.root4e);
        this.root3a = this.fleshyBase01.getChild("root3a");
        this.parts.put("root3a", this.root3a);
        this.root3ba = this.root3a.getChild("root3ba");
        this.parts.put("root3ba", this.root3ba);
        this.root3c = this.root3ba.getChild("root3c");
        this.parts.put("root3c", this.root3c);
        this.root3d = this.root3c.getChild("root3d");
        this.parts.put("root3d", this.root3d);
        this.root3e = this.root3d.getChild("root3e");
        this.parts.put("root3e", this.root3e);
        this.root3bb = this.root3ba.getChild("root3bb");
        this.parts.put("root3bb", this.root3bb);
        this.root3bc = this.root3ba.getChild("root3bc");
        this.parts.put("root3bc", this.root3bc);
        this.root3bd = this.root3ba.getChild("root3bd");
        this.parts.put("root3bd", this.root3bd);
        this.section1 = root.getChild("section1");
        this.parts.put("section1", this.section1);
        this.section2 = this.section1.getChild("section2");
        this.parts.put("section2", this.section2);
        this.section3 = this.section2.getChild("section3");
        this.parts.put("section3", this.section3);
        this.section4 = this.section3.getChild("section4");
        this.parts.put("section4", this.section4);
        this.lobeB1 = this.section4.getChild("lobeB1");
        this.parts.put("lobeB1", this.lobeB1);
        this.lobeB2 = this.lobeB1.getChild("lobeB2");
        this.parts.put("lobeB2", this.lobeB2);
        this.lobeB3 = this.lobeB2.getChild("lobeB3");
        this.parts.put("lobeB3", this.lobeB3);
        this.lobeR1 = this.section4.getChild("lobeR1");
        this.parts.put("lobeR1", this.lobeR1);
        this.lobeR2 = this.lobeR1.getChild("lobeR2");
        this.parts.put("lobeR2", this.lobeR2);
        this.lobeR3 = this.lobeR2.getChild("lobeR3");
        this.parts.put("lobeR3", this.lobeR3);
        this.lobeL1 = this.section4.getChild("lobeL1");
        this.parts.put("lobeL1", this.lobeL1);
        this.lobeL2 = this.lobeL1.getChild("lobeL2");
        this.parts.put("lobeL2", this.lobeL2);
        this.lobeL3 = this.lobeL2.getChild("lobeL3");
        this.parts.put("lobeL3", this.lobeL3);
        this.lobeF1 = this.section4.getChild("lobeF1");
        this.parts.put("lobeF1", this.lobeF1);
        this.lobeF2 = this.lobeF1.getChild("lobeF2");
        this.parts.put("lobeF2", this.lobeF2);
        this.lobeF3 = this.lobeF2.getChild("lobeF3");
        this.parts.put("lobeF3", this.lobeF3);
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
        PartDefinition p_fleshyBase01 = root.addOrReplaceChild("fleshyBase01",
                CubeListBuilder.create().texOffs(34, 15).addBox(-6.5F, 0.0F, -6.5F, 13.0F, 1.0F, 13.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_root5a = p_fleshyBase01.addOrReplaceChild("root5a",
                CubeListBuilder.create().texOffs(89, 34).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(3.2F, 0.6F, 0.7F, 0.0F, -1.2292353921796064F, 0.0F));
        PartDefinition p_root5ba = p_root5a.addOrReplaceChild("root5ba",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-0.3F, 0.0F, -4.5F, 0.0F, -0.7740535232594852F, 0.0F));
        PartDefinition p_root5bc = p_root5ba.addOrReplaceChild("root5bc",
                CubeListBuilder.create().texOffs(117, 34).addBox(-0.2F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root5c = p_root5ba.addOrReplaceChild("root5c",
                CubeListBuilder.create().texOffs(106, 44).addBox(-0.5F, -0.5F, -4.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.1F, 0.1F, -3.6F, 0.0F, -0.7740535232594852F, 0.0F));
        PartDefinition p_root5d = p_root5c.addOrReplaceChild("root5d",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.5F, 0.1F, -3.6F, 0.0F, 0.8196066167365371F, 0.0F));
        PartDefinition p_root5e = p_root5d.addOrReplaceChild("root5e",
                CubeListBuilder.create().texOffs(106, 44).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.2F, 0.1F, -3.1F, 0.0F, 0.9105382707654417F, 0.0F));
        PartDefinition p_root5bd = p_root5ba.addOrReplaceChild("root5bd",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.2F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root5bb = p_root5ba.addOrReplaceChild("root5bb",
                CubeListBuilder.create().texOffs(117, 27).addBox(-0.5F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_fleshyBase02 = p_fleshyBase01.addOrReplaceChild("fleshyBase02",
                CubeListBuilder.create().texOffs(34, 34).addBox(-9.0F, 0.0F, -9.0F, 18.0F, 1.0F, 18.0F),
                PartPose.offset(0.0F, 0.5F, 0.0F));
        PartDefinition p_root1a = p_fleshyBase01.addOrReplaceChild("root1a",
                CubeListBuilder.create().texOffs(89, 34).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-2.4F, 0.6F, -4.5F, 0.0F, 0.6829473363053812F, 0.0F));
        PartDefinition p_root1ba = p_root1a.addOrReplaceChild("root1ba",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -4.5F, 0.0F, 0.5918411493512771F, 0.0F));
        PartDefinition p_root1bb = p_root1ba.addOrReplaceChild("root1bb",
                CubeListBuilder.create().texOffs(117, 27).addBox(-0.5F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root1bd = p_root1ba.addOrReplaceChild("root1bd",
                CubeListBuilder.create().texOffs(117, 41).addBox(-0.2F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root1bc = p_root1ba.addOrReplaceChild("root1bc",
                CubeListBuilder.create().texOffs(117, 34).addBox(-0.2F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root1c = p_root1ba.addOrReplaceChild("root1c",
                CubeListBuilder.create().texOffs(106, 44).addBox(-0.5F, -0.5F, -4.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, -3.5F, 0.0F, -0.9105382707654417F, 0.0F));
        PartDefinition p_root1d = p_root1c.addOrReplaceChild("root1d",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.4F, 0.1F, -3.6F, 0.0F, -1.1838568316277536F, 0.0F));
        PartDefinition p_root1e = p_root1d.addOrReplaceChild("root1e",
                CubeListBuilder.create().texOffs(106, 58).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.1F, 0.1F, -3.1F, 0.0F, -1.1383037381507017F, 0.0F));
        PartDefinition p_root2a = p_fleshyBase01.addOrReplaceChild("root2a",
                CubeListBuilder.create().texOffs(89, 34).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(3.5F, 0.6F, -4.5F, 0.0F, -0.7740535232594852F, 0.0F));
        PartDefinition p_root2ba = p_root2a.addOrReplaceChild("root2ba",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-0.3F, 0.0F, -4.5F, 0.0F, -0.40980330836826856F, 0.0F));
        PartDefinition p_root2c = p_root2ba.addOrReplaceChild("root2c",
                CubeListBuilder.create().texOffs(106, 44).addBox(-0.5F, -0.5F, -4.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.2F, 0.1F, -3.5F, 0.0F, 0.8196066167365371F, 0.0F));
        PartDefinition p_root2d = p_root2c.addOrReplaceChild("root2d",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.6F, 0.1F, -3.6F, 0.0F, 1.1383037381507017F, 0.0F));
        PartDefinition p_root2e = p_root2d.addOrReplaceChild("root2e",
                CubeListBuilder.create().texOffs(106, 58).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, -3.4F, 0.0F, 0.36425021489121656F, 0.0F));
        PartDefinition p_root2bc = p_root2ba.addOrReplaceChild("root2bc",
                CubeListBuilder.create().texOffs(117, 34).addBox(-0.2F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root2bb = p_root2ba.addOrReplaceChild("root2bb",
                CubeListBuilder.create().texOffs(117, 27).addBox(-0.5F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root2bd = p_root2ba.addOrReplaceChild("root2bd",
                CubeListBuilder.create().texOffs(117, 41).addBox(-0.2F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root4a = p_fleshyBase01.addOrReplaceChild("root4a",
                CubeListBuilder.create().texOffs(89, 34).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-0.2F, 0.6F, 3.8F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_root4ba = p_root4a.addOrReplaceChild("root4ba",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -4.5F, 0.0F, 0.7285004297824331F, 0.0F));
        PartDefinition p_root4bd = p_root4ba.addOrReplaceChild("root4bd",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.2F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root4bc = p_root4ba.addOrReplaceChild("root4bc",
                CubeListBuilder.create().texOffs(117, 34).addBox(-0.2F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root4bb = p_root4ba.addOrReplaceChild("root4bb",
                CubeListBuilder.create().texOffs(117, 27).addBox(-0.5F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root4c = p_root4ba.addOrReplaceChild("root4c",
                CubeListBuilder.create().texOffs(106, 44).addBox(-0.5F, -0.5F, -4.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.2F, 0.1F, -3.5F, 0.0F, -0.40980330836826856F, 0.0F));
        PartDefinition p_root4d = p_root4c.addOrReplaceChild("root4d",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.4F, 0.1F, -3.6F, 0.0F, -0.8196066167365371F, 0.0F));
        PartDefinition p_root4e = p_root4d.addOrReplaceChild("root4e",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.3F, 0.0F, -3.0F, 0.0F, -1.2747884856566583F, 0.0F));
        PartDefinition p_root3a = p_fleshyBase01.addOrReplaceChild("root3a",
                CubeListBuilder.create().texOffs(89, 34).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-4.2F, 0.6F, 1.0F, 0.0F, 1.2747884856566583F, 0.0F));
        PartDefinition p_root3ba = p_root3a.addOrReplaceChild("root3ba",
                CubeListBuilder.create().texOffs(106, 34).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -4.5F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_root3c = p_root3ba.addOrReplaceChild("root3c",
                CubeListBuilder.create().texOffs(106, 44).addBox(-0.5F, -0.5F, -4.2F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.3F, 0.1F, -3.5F, 0.0F, 0.36425021489121656F, 0.0F));
        PartDefinition p_root3d = p_root3c.addOrReplaceChild("root3d",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.3F, 0.1F, -3.5F, 0.0F, -0.6373942428283291F, 0.0F));
        PartDefinition p_root3e = p_root3d.addOrReplaceChild("root3e",
                CubeListBuilder.create().texOffs(106, 51).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, -3.3F, 0.0F, -0.5918411493512771F, 0.0F));
        PartDefinition p_root3bb = p_root3ba.addOrReplaceChild("root3bb",
                CubeListBuilder.create().texOffs(117, 27).addBox(-0.5F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root3bc = p_root3ba.addOrReplaceChild("root3bc",
                CubeListBuilder.create().texOffs(117, 34).addBox(-0.2F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_root3bd = p_root3ba.addOrReplaceChild("root3bd",
                CubeListBuilder.create().texOffs(117, 41).addBox(-0.2F, -0.2F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_section1 = root.addOrReplaceChild("section1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 1.0F, 8.0F),
                PartPose.offset(0.0F, 22.4F, 0.0F));
        PartDefinition p_section2 = p_section1.addOrReplaceChild("section2",
                CubeListBuilder.create().texOffs(0, 11).addBox(-4.5F, -2.0F, -4.5F, 9.0F, 1.0F, 9.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_section3 = p_section2.addOrReplaceChild("section3",
                CubeListBuilder.create().texOffs(0, 24).addBox(-5.5F, -8.0F, -5.5F, 11.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition p_section4 = p_section3.addOrReplaceChild("section4",
                CubeListBuilder.create().texOffs(0, 46).addBox(-5.0F, -2.5F, -5.0F, 10.0F, 2.0F, 10.0F),
                PartPose.offset(0.0F, -7.4F, 0.0F));
        PartDefinition p_lobeB1 = p_section4.addOrReplaceChild("lobeB1",
                CubeListBuilder.create().texOffs(60, 0).addBox(-3.5F, -3.0F, -0.5F, 7.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, 4.3F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_lobeB2 = p_lobeB1.addOrReplaceChild("lobeB2",
                CubeListBuilder.create().texOffs(63, 6).addBox(-2.5F, -2.2F, -0.5F, 5.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, 0.0F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_lobeB3 = p_lobeB2.addOrReplaceChild("lobeB3",
                CubeListBuilder.create().texOffs(65, 10).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.8F, -0.1F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lobeR1 = p_section4.addOrReplaceChild("lobeR1",
                CubeListBuilder.create().texOffs(104, 0).addBox(-0.5F, -3.0F, -3.5F, 1.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(-4.3F, -2.0F, 0.0F, 0.0F, 0.0F, 0.5918411493512771F));
        PartDefinition p_lobeR2 = p_lobeR1.addOrReplaceChild("lobeR2",
                CubeListBuilder.create().texOffs(105, 13).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, 0.0F, 0.0F, 0.0F, 0.5918411493512771F));
        PartDefinition p_lobeR3 = p_lobeR2.addOrReplaceChild("lobeR3",
                CubeListBuilder.create().texOffs(108, 23).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.1F, -1.8F, 0.0F, 0.0F, 0.0F, 0.31869712141416456F));
        PartDefinition p_lobeL1 = p_section4.addOrReplaceChild("lobeL1",
                CubeListBuilder.create().texOffs(83, 0).addBox(-0.5F, -3.0F, -3.5F, 1.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(4.3F, -2.0F, 0.0F, 0.0F, 0.0F, -0.5918411493512771F));
        PartDefinition p_lobeL2 = p_lobeL1.addOrReplaceChild("lobeL2",
                CubeListBuilder.create().texOffs(84, 13).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, 0.0F, 0.0F, 0.0F, -0.5918411493512771F));
        PartDefinition p_lobeL3 = p_lobeL2.addOrReplaceChild("lobeL3",
                CubeListBuilder.create().texOffs(87, 23).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.1F, -1.8F, 0.0F, 0.0F, 0.0F, -0.31869712141416456F));
        PartDefinition p_lobeF1 = p_section4.addOrReplaceChild("lobeF1",
                CubeListBuilder.create().texOffs(36, 0).addBox(-3.5F, -3.0F, -0.5F, 7.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, -4.3F, -0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_lobeF2 = p_lobeF1.addOrReplaceChild("lobeF2",
                CubeListBuilder.create().texOffs(38, 6).addBox(-2.5F, -2.2F, -0.5F, 5.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, 0.0F, -0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_lobeF3 = p_lobeF2.addOrReplaceChild("lobeF3",
                CubeListBuilder.create().texOffs(41, 10).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.8F, 0.1F, -0.31869712141416456F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
