package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DebugRenderer {
    public final PathfindingRenderer pathfindingRenderer = new PathfindingRenderer();
    public final DebugRenderer.SimpleDebugRenderer waterDebugRenderer;
    public final DebugRenderer.SimpleDebugRenderer chunkBorderRenderer;
    public final DebugRenderer.SimpleDebugRenderer heightMapRenderer;
    public final DebugRenderer.SimpleDebugRenderer collisionBoxRenderer;
    public final DebugRenderer.SimpleDebugRenderer supportBlockRenderer;
    public final DebugRenderer.SimpleDebugRenderer neighborsUpdateRenderer;
    public final StructureRenderer structureRenderer;
    public final DebugRenderer.SimpleDebugRenderer lightDebugRenderer;
    public final DebugRenderer.SimpleDebugRenderer worldGenAttemptRenderer;
    public final DebugRenderer.SimpleDebugRenderer solidFaceRenderer;
    public final DebugRenderer.SimpleDebugRenderer chunkRenderer;
    public final BrainDebugRenderer brainDebugRenderer;
    public final VillageSectionsDebugRenderer villageSectionsDebugRenderer;
    public final BeeDebugRenderer beeDebugRenderer;
    public final RaidDebugRenderer raidDebugRenderer;
    public final GoalSelectorDebugRenderer goalSelectorRenderer;
    public final GameTestDebugRenderer gameTestDebugRenderer;
    public final GameEventListenerRenderer gameEventListenerRenderer;
    public final LightSectionDebugRenderer skyLightSectionDebugRenderer;
    public final BreezeDebugRenderer breezeDebugRenderer;
    private boolean renderChunkborder;

    public DebugRenderer(Minecraft p_113433_) {
        this.waterDebugRenderer = new WaterDebugRenderer(p_113433_);
        this.chunkBorderRenderer = new ChunkBorderRenderer(p_113433_);
        this.heightMapRenderer = new HeightMapRenderer(p_113433_);
        this.collisionBoxRenderer = new CollisionBoxRenderer(p_113433_);
        this.supportBlockRenderer = new SupportBlockRenderer(p_113433_);
        this.neighborsUpdateRenderer = new NeighborsUpdateRenderer(p_113433_);
        this.structureRenderer = new StructureRenderer(p_113433_);
        this.lightDebugRenderer = new LightDebugRenderer(p_113433_);
        this.worldGenAttemptRenderer = new WorldGenAttemptRenderer();
        this.solidFaceRenderer = new SolidFaceRenderer(p_113433_);
        this.chunkRenderer = new ChunkDebugRenderer(p_113433_);
        this.brainDebugRenderer = new BrainDebugRenderer(p_113433_);
        this.villageSectionsDebugRenderer = new VillageSectionsDebugRenderer();
        this.beeDebugRenderer = new BeeDebugRenderer(p_113433_);
        this.raidDebugRenderer = new RaidDebugRenderer(p_113433_);
        this.goalSelectorRenderer = new GoalSelectorDebugRenderer(p_113433_);
        this.gameTestDebugRenderer = new GameTestDebugRenderer();
        this.gameEventListenerRenderer = new GameEventListenerRenderer(p_113433_);
        this.skyLightSectionDebugRenderer = new LightSectionDebugRenderer(p_113433_, LightLayer.SKY);
        this.breezeDebugRenderer = new BreezeDebugRenderer(p_113433_);
    }

    public void clear() {
        this.pathfindingRenderer.clear();
        this.waterDebugRenderer.clear();
        this.chunkBorderRenderer.clear();
        this.heightMapRenderer.clear();
        this.collisionBoxRenderer.clear();
        this.supportBlockRenderer.clear();
        this.neighborsUpdateRenderer.clear();
        this.structureRenderer.clear();
        this.lightDebugRenderer.clear();
        this.worldGenAttemptRenderer.clear();
        this.solidFaceRenderer.clear();
        this.chunkRenderer.clear();
        this.brainDebugRenderer.clear();
        this.villageSectionsDebugRenderer.clear();
        this.beeDebugRenderer.clear();
        this.raidDebugRenderer.clear();
        this.goalSelectorRenderer.clear();
        this.gameTestDebugRenderer.clear();
        this.gameEventListenerRenderer.clear();
        this.skyLightSectionDebugRenderer.clear();
        this.breezeDebugRenderer.clear();
    }

    public boolean switchRenderChunkborder() {
        this.renderChunkborder = !this.renderChunkborder;
        return this.renderChunkborder;
    }

    public void render(PoseStack p_113458_, MultiBufferSource.BufferSource p_113459_, double p_113460_, double p_113461_, double p_113462_) {
        if (this.renderChunkborder && !Minecraft.getInstance().showOnlyReducedInfo()) {
            this.chunkBorderRenderer.render(p_113458_, p_113459_, p_113460_, p_113461_, p_113462_);
        }

        this.gameTestDebugRenderer.render(p_113458_, p_113459_, p_113460_, p_113461_, p_113462_);
    }

    public static Optional<Entity> getTargetedEntity(@Nullable Entity entity, int distance) {
        if (entity == null) {
            return Optional.empty();
        } else {
            Vec3 vec3 = entity.getEyePosition();
            Vec3 vec31 = entity.getViewVector(1.0F).scale((double)distance);
            Vec3 vec32 = vec3.add(vec31);
            AABB aabb = entity.getBoundingBox().expandTowards(vec31).inflate(1.0);
            int i = distance * distance;
            Predicate<Entity> predicate = p_113447_ -> !p_113447_.isSpectator() && p_113447_.isPickable();
            EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(entity, vec3, vec32, aabb, predicate, (double)i);
            if (entityhitresult == null) {
                return Optional.empty();
            } else {
                return vec3.distanceToSqr(entityhitresult.getLocation()) > (double)i ? Optional.empty() : Optional.of(entityhitresult.getEntity());
            }
        }
    }

    public static void renderFilledUnitCube(
        PoseStack poseStack, MultiBufferSource bufferSource, BlockPos pos, float red, float green, float blue, float alpha
    ) {
        renderFilledBox(poseStack, bufferSource, pos, pos.offset(1, 1, 1), red, green, blue, alpha);
    }

    public static void renderFilledBox(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        BlockPos startPos,
        BlockPos endPos,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (camera.isInitialized()) {
            Vec3 vec3 = camera.getPosition().reverse();
            AABB aabb = AABB.encapsulatingFullBlocks(startPos, endPos).move(vec3);
            renderFilledBox(poseStack, bufferSource, aabb, red, green, blue, alpha);
        }
    }

    public static void renderFilledBox(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        BlockPos pos,
        float scale,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (camera.isInitialized()) {
            Vec3 vec3 = camera.getPosition().reverse();
            AABB aabb = new AABB(pos).move(vec3).inflate((double)scale);
            renderFilledBox(poseStack, bufferSource, aabb, red, green, blue, alpha);
        }
    }

    public static void renderFilledBox(
        PoseStack poseStack, MultiBufferSource bufferSource, AABB boundingBox, float red, float green, float blue, float alpha
    ) {
        renderFilledBox(
            poseStack,
            bufferSource,
            boundingBox.minX,
            boundingBox.minY,
            boundingBox.minZ,
            boundingBox.maxX,
            boundingBox.maxY,
            boundingBox.maxZ,
            red,
            green,
            blue,
            alpha
        );
    }

    public static void renderFilledBox(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        double minX,
        double minY,
        double minZ,
        double maxX,
        double maxY,
        double maxZ,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.debugFilledBox());
        LevelRenderer.addChainedFilledBoxVertices(
            poseStack, vertexconsumer, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha
        );
    }

    public static void renderFloatingText(
        PoseStack poseStack, MultiBufferSource bufferSource, String text, int x, int y, int z, int color
    ) {
        renderFloatingText(poseStack, bufferSource, text, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, color);
    }

    public static void renderFloatingText(
        PoseStack poseStack, MultiBufferSource bufferSource, String text, double x, double y, double z, int color
    ) {
        renderFloatingText(poseStack, bufferSource, text, x, y, z, color, 0.02F);
    }

    public static void renderFloatingText(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        String text,
        double x,
        double y,
        double z,
        int color,
        float scale
    ) {
        renderFloatingText(poseStack, bufferSource, text, x, y, z, color, scale, true, 0.0F, false);
    }

    public static void renderFloatingText(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        String text,
        double x,
        double y,
        double z,
        int color,
        float scale,
        boolean center,
        float xOffset,
        boolean transparent
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.getMainCamera();
        if (camera.isInitialized() && minecraft.getEntityRenderDispatcher().options != null) {
            Font font = minecraft.font;
            double d0 = camera.getPosition().x;
            double d1 = camera.getPosition().y;
            double d2 = camera.getPosition().z;
            poseStack.pushPose();
            poseStack.translate((float)(x - d0), (float)(y - d1) + 0.07F, (float)(z - d2));
            poseStack.mulPose(camera.rotation());
            poseStack.scale(scale, -scale, scale);
            float f = center ? (float)(-font.width(text)) / 2.0F : 0.0F;
            f -= xOffset / scale;
            font.drawInBatch(
                text,
                f,
                0.0F,
                color,
                false,
                poseStack.last().pose(),
                bufferSource,
                transparent ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL,
                0,
                15728880
            );
            poseStack.popPose();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface SimpleDebugRenderer {
        void render(PoseStack p_113507_, MultiBufferSource p_113508_, double p_113509_, double p_113510_, double p_113511_);

        default void clear() {
        }
    }
}
