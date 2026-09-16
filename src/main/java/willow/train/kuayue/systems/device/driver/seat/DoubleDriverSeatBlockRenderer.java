package willow.train.kuayue.systems.device.driver.seat;

import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import willow.train.kuayue.initial.AllElements;
import kasuga.lib.core.client.model.BedrockModelLoader;
import kasuga.lib.core.client.model.anim_model.AnimModel;

public class DoubleDriverSeatBlockRenderer implements BlockEntityRenderer<DoubleDriverSeatBlockEntity> {
    protected static AnimModel bedrockModel = BedrockModelLoader.getModel(
            AllElements.testRegistry.asResource("block/seats/double_driver_seat"),
            RenderType.cutoutMipped()
    );

    static {
        bedrockModel.init();
    }

    public DoubleDriverSeatBlockRenderer(BlockEntityRendererProvider.Context context) {
        super();
    }

    @Override
    public void render(DoubleDriverSeatBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int packedLight, int overlay) {
        renderCommon(blockEntity.getBlockState(), ms, bufferSource, packedLight, overlay);
    }

    public static void renderCommon(BlockState blockState, PoseStack poseStack,
                                    MultiBufferSource bufferSource, int packedLight, int overlay) {
        poseStack.pushPose();
        Direction facing = blockState.getValue(DoubleDriverSeatBlock.FACING);
        
        // 设置模型位置和旋转
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(new Quaternionf().rotationXYZ(0, facing.toYRot() * 3.141f / 180, 0));
        poseStack.translate(-0.5, 0, -0.5);
        
        // 渲染模型
        bedrockModel.render(poseStack, bufferSource, packedLight, overlay);
        
        poseStack.popPose();
    }
}
