package willow.train.kuayue.block.bogey.loco.renderer;

import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.gui.GuiGraphics;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import willow.train.kuayue.utils.render.KuayueBogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import net.createmod.catnip.nbt.NBTHelper;
import kasuga.lib.core.create.BogeyDataConstants;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import willow.train.kuayue.initial.AllElements;
import willow.train.kuayue.initial.create.AllLocoBogeys;

public class JY290Renderer extends KuayueBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return AllElements.testRegistry.asResource("block/" + path);
    }

    public static final PartialModel
            JY290_FRAME = PartialModel.of(asBlockModelResource("bogey/jy290/jy290_frame")),
            JY290_WHEEL = PartialModel.of(asBlockModelResource("bogey/jy290/jy290_wheel"));

    private static final double FRAME_TRANS_Y = -0.07;
    private static final double WHEEL_TRANS_Y = 0.775;
    private static final double WHEEL_TRANS_Z = 1.195;

    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, MultiBufferSource bufferSource, int light, VertexConsumer vb, boolean inContraption) {

        Direction direction =
                bogeyData.contains(BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(JY290_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(JY290_WHEEL, ms, inInstancedContraption, 2);

        if (!inContraption) {
            // 正向转向架未组装架体
            frame.rotateY(180).translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

            // 正向转向架未组装轮对
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            return;
        }

        if (direction == Direction.NORTH || direction == Direction.WEST) {
            // 正向转向架北西方向架体
            frame.rotateY(180).translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

            // 正向转向架北西方向轮对
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            return;
        }

        // 正向转向架南东方向架体
        frame.translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

        // 正向转向架南东方向轮对
        if (!inInstancedContraption) ms.pushPose();
        wheels[0].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();

        if (!inInstancedContraption) ms.pushPose();
        wheels[1].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();
    }

    @Override
    public BogeySizes.BogeySize getSize() {
        return AllLocoBogeys.jy290.getSize();
    }

    @Override
    public void initialiseContraptionModelData(Object materialManager, CarriageBogey carriageBogey) {
        this.createModelInstance(materialManager, JY290_FRAME);
        this.createModelInstance(materialManager, JY290_WHEEL, 2);
    }

    public static class Backward extends KuayueBogeyRenderer {

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, MultiBufferSource bufferSource, int light, VertexConsumer vb, boolean inContraption) {

            Direction direction =
                    bogeyData.contains(BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(JY290_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(JY290_WHEEL, ms, inInstancedContraption, 2);

            // 反向转向架未组装
            if (!inContraption) {
                // 未组装架体
                frame.translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

                // 未组装轮对
                if (!inInstancedContraption) ms.pushPose();
                wheels[0].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[1].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                return;
            }

            // 反向转向架北西方向已组装
            if (direction == Direction.NORTH || direction == Direction.WEST) {
                // 北西方向已组装架体
                frame.translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

                // 北西方向已组装轮对
                if (!inInstancedContraption) ms.pushPose();
                wheels[0].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[1].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                return;
            }

            // 反向转向架南东方向已组装
            // 南东方向已组装架体
            frame.rotateY(180).translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

            // 南东方向已组装轮对
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return AllLocoBogeys.jy290Backward.getSize();
        }

        @Override
        public void initialiseContraptionModelData(Object materialManager, CarriageBogey carriageBogey) {
            this.createModelInstance(materialManager, JY290_FRAME);
            this.createModelInstance(materialManager, JY290_WHEEL, 2);
        }
    }
}
