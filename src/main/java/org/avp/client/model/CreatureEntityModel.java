package org.avp.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

/**
 * Animation for every catalog creature, worked out from the part names of its 1.12.2 model:
 * heads turn to look, legs walk (left and right out of step), arms swing against the legs,
 * tails sway (more towards the tip), wings flap and jaws work while it attacks. A first pass -
 * each creature can get hand-made animation in the model continuity pass.
 */
public class CreatureEntityModel<T extends Mob> extends AvpEntityModel<T, GeneratedModel>
{
    private enum Role
    {
        HEAD, JAW, LEFT_LEG, RIGHT_LEG, LEFT_ARM, RIGHT_ARM, TAIL, LEFT_WING, RIGHT_WING, OTHER
    }

    private record Animated(ModelPart part, Role role, int index)
    {
    }

    private final List<Animated> animated = new ArrayList<>();
    /** 0..255: drawn see-through (a cloaked Yautja). 255 = solid. */
    public int alpha = 255;

    public CreatureEntityModel(GeneratedModel model)
    {
        super(model);
        int tail = 0;
        for (Map.Entry<String, ModelPart> e : model.parts().entrySet())
        {
            Role role = roleOf(e.getKey());
            if (role != Role.OTHER)
            {
                animated.add(new Animated(e.getValue(), role, role == Role.TAIL ? tail++ : 0));
            }
        }
    }

    /** Parts whose name says they're a tail (left out when centring the model on its hitbox). */
    public static boolean isTail(String name)
    {
        String n = name.toLowerCase(Locale.ROOT);
        return n.contains("tail") || n.contains("stabber");
    }

    private static Role roleOf(String name)
    {
        String n = name.toLowerCase(Locale.ROOT);
        if (isTail(name))
        {
            return Role.TAIL;
        }
        if (n.contains("jaw") || n.contains("mouth") && !n.contains("inner"))
        {
            return Role.JAW;
        }
        if (n.contains("head") || n.contains("neck") || n.contains("face") || n.contains("skull") || n.contains("dome"))
        {
            return Role.HEAD;
        }
        boolean left = n.startsWith("left") || n.startsWith("l") && name.length() > 1 && Character.isUpperCase(name.charAt(1))
                || n.contains("left");
        boolean right = n.startsWith("right") || n.startsWith("r") && name.length() > 1 && Character.isUpperCase(name.charAt(1))
                || n.contains("right");
        if (n.contains("wing"))
        {
            return left ? Role.LEFT_WING : right ? Role.RIGHT_WING : Role.OTHER;
        }
        if (n.contains("leg") || n.contains("thigh") || n.contains("shin") || n.contains("foot") || n.contains("knee") || n.contains("calf"))
        {
            return left ? Role.LEFT_LEG : right ? Role.RIGHT_LEG : Role.OTHER;
        }
        if (n.contains("arm") || n.contains("hand") || n.contains("claw") || n.contains("finger"))
        {
            return left ? Role.LEFT_ARM : right ? Role.RIGHT_ARM : Role.OTHER;
        }
        return Role.OTHER;
    }

    @Override
    protected void animate(T entity, float swing, float swingAmount, float age, float netHeadYaw, float headPitch)
    {
        float yaw = netHeadYaw * Mth.DEG_TO_RAD * 0.6F;
        float pitch = headPitch * Mth.DEG_TO_RAD * 0.4F;
        float walk = Mth.cos(swing * 0.6662F) * 0.9F * swingAmount;
        float walkOpposite = -walk;
        float attack = entity.getAttackAnim(age - entity.tickCount);
        float flap = Mth.sin(age * 0.6F) * 0.7F;
        for (Animated a : animated)
        {
            ModelPart p = a.part;
            switch (a.role)
            {
                case HEAD -> {
                    p.yRot += yaw;
                    p.xRot += pitch;
                }
                case JAW -> {
                    p.yRot += yaw;
                    p.xRot += pitch + Mth.sin(attack * Mth.PI) * 0.5F + Mth.sin(age * 0.05F) * 0.03F;
                }
                case LEFT_LEG -> p.xRot += walk;
                case RIGHT_LEG -> p.xRot += walkOpposite;
                case LEFT_ARM -> p.xRot += walkOpposite * 0.5F - Mth.sin(attack * Mth.PI) * 1.2F;
                case RIGHT_ARM -> p.xRot += walk * 0.5F - Mth.sin(attack * Mth.PI) * 1.2F;
                case TAIL -> p.yRot += Mth.sin(age * 0.08F - a.index * 0.4F) * 0.04F * (a.index + 1) * (1F + swingAmount);
                case LEFT_WING -> p.zRot += flap;
                case RIGHT_WING -> p.zRot -= flap;
                default -> {
                }
            }
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay, int color)
    {
        if (alpha < 255)
        {
            color = (color & 0x00FFFFFF) | (alpha << 24);
        }
        super.renderToBuffer(poseStack, buffer, light, overlay, color);
    }
}
