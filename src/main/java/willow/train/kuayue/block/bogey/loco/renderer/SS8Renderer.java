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

public class SS8Renderer extends KuayueBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return AllElements.testRegistry.asResource("block/" + path);
    }
    public static final PartialModel
            SS8_FRAME = PartialModel.of(asBlockModelResource("bogey/ss8/ss8_frame")),
            SS8_WHEEL = PartialModel.of(asBlockModelResource("bogey/ss8/ss8_wheel"));
    @Override
    public void initialiseContraptionModelData(
            Object materialManager, CarriageBogey carriageBogey) {
        this.createModelInstance(materialManager, SS8_FRAME);
        this.createModelInstance(materialManager, SS8_WHEEL, 2);
    }

    @Override
    public BogeySizes.BogeySize getSize() {
        return AllLocoBogeys.ss8.getSize();
    }
    public static final double dp =2.604d;//轴距
    public static final double ad =1.304d;//轮对位移
    @Override
    public void render(
            CompoundTag bogeyData,
            float wheelAngle, PoseStack ms, MultiBufferSource bufferSource,
            int light,
            VertexConsumer vb,
            boolean inContraption) {

        Direction direction =
                bogeyData.contains(BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(SS8_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(SS8_WHEEL, ms, inInstancedContraption, 2);

        if (direction == Direction.SOUTH || direction == Direction.EAST) {
            if (inContraption) {
                frame.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.905, ((double) side) * dp+ad)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            } else {
                frame.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.905, ((double) side) * dp+ad)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            }
        } else {
            frame.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

            for (int side = -1; side < 1; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.905, ((double) side) * dp+ad)
                        .rotateX(wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
        }
    }

    public static class Backward extends KuayueBogeyRenderer {

        @Override
        public void render(
                CompoundTag bogeyData,
                float wheelAngle, PoseStack ms, MultiBufferSource bufferSource,
                int light,
                VertexConsumer vb,
                boolean inContraption) {

            Direction direction =
                    bogeyData.contains(BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            BogeyDataConstants.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            wheelAngle = -wheelAngle;
            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(SS8_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(SS8_WHEEL, ms, inInstancedContraption, 2);

            if (direction == Direction.SOUTH || direction == Direction.EAST) {
                if (inContraption) {
                    frame.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.905, ((double) side) * dp+ad)
                                .rotateX(-wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }
                } else {
                    frame.translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.905, ((double) side) * dp+ad)
                                .rotateX(wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }
                }
            } else {
                frame.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.905, ((double) side) * dp+ad)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            }
        }

        @Override
        public BogeySizes.BogeySize getSize() {
            return AllLocoBogeys.ss8Backward.getSize();
        }

        @Override
        public void initialiseContraptionModelData(
                Object materialManager, CarriageBogey carriageBogey) {
            this.createModelInstance(materialManager, SS8_FRAME);
            this.createModelInstance(materialManager, SS8_WHEEL, 2);
        }
    }
    public static class Andesite extends SS8Renderer {
        @Override
        public void render(
                CompoundTag bogeyData,
                float wheelAngle, PoseStack ms, MultiBufferSource bufferSource,
                int light,
                VertexConsumer vb,
                boolean inContraption) {
            ms.pushPose();
            ms.scale(1.2F, 1, 1);
            super.render(bogeyData, wheelAngle, ms, bufferSource, light, vb, inContraption);
            ms.popPose();
        }

        public static class Backward extends SS8Renderer.Backward {
            @Override
            public void render(
                    CompoundTag bogeyData,
                    float wheelAngle, PoseStack ms, MultiBufferSource bufferSource,
                    int light,
                    VertexConsumer vb,
                    boolean inContraption) {
                ms.pushPose();
                ms.scale(1.2F, 1, 1);
                super.render(bogeyData, wheelAngle, ms, bufferSource, light, vb, inContraption);
                ms.popPose();
            }
        }
    }
}
