package willow.train.kuayue.utils.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * Create 0.5.1 {@code SuperByteBuffer} compatibility shim.
 * <p>
 * Create 6.0 removed the CachedBufferer/SuperByteBuffer pipeline. This shim draws the
 * captured partial model quads with an accumulated transform matrix and stored light,
 * preserving the 0.5.1 call surface ({@code CachedBufferer.partial(...).light(...)
 * .renderInto(ms, vb)}).
 * <p>
 * Rotation angles are in <b>degrees</b> (matching the call sites in this codebase).
 */
public class SuperByteBuffer {

    private static final RandomSource RANDOM = RandomSource.create(42L);

    private final PartialModel model;
    private final BlockState state;
    private final Matrix4f transforms;
    private int light = -1;

    SuperByteBuffer(PartialModel model, BlockState state) {
        this.model = model;
        this.state = state;
        this.transforms = new Matrix4f();
    }

    private SuperByteBuffer(PartialModel model, BlockState state, Matrix4f transforms) {
        this.model = model;
        this.state = state;
        this.transforms = transforms;
    }

    public SuperByteBuffer translate(double x, double y, double z) {
        Matrix4f next = new Matrix4f(transforms);
        next.translate((float) x, (float) y, (float) z);
        return new SuperByteBuffer(model, state, next);
    }

    public SuperByteBuffer translateY(double y) {
        return translate(0, y, 0);
    }

    public SuperByteBuffer translateZ(double z) {
        return translate(0, 0, z);
    }

    public SuperByteBuffer translateX(double x) {
        return translate(x, 0, 0);
    }

    public SuperByteBuffer rotateX(double angleDegrees) {
        Matrix4f next = new Matrix4f(transforms);
        next.rotateX((float) Math.toRadians(angleDegrees));
        return new SuperByteBuffer(model, state, next);
    }

    public SuperByteBuffer rotateY(double angleDegrees) {
        Matrix4f next = new Matrix4f(transforms);
        next.rotateY((float) Math.toRadians(angleDegrees));
        return new SuperByteBuffer(model, state, next);
    }

    public SuperByteBuffer rotateZ(double angleDegrees) {
        Matrix4f next = new Matrix4f(transforms);
        next.rotateZ((float) Math.toRadians(angleDegrees));
        return new SuperByteBuffer(model, state, next);
    }

    public SuperByteBuffer light(int packedLight) {
        this.light = packedLight;
        return this;
    }

    public SuperByteBuffer reset() {
        transforms.identity();
        return this;
    }

    public void renderInto(PoseStack ms, VertexConsumer vb) {
        BakedModel baked = model.get();
        if (baked == null || vb == null)
            return;
        int light = this.light == -1 ? LightTexture.FULL_BRIGHT : this.light;
        Matrix4f local = new Matrix4f(transforms);
        // local transform composes AFTER the caller's current pose
        local.mulLocal(new Matrix4f(ms.last().pose()));
        Matrix3f normal = new Matrix3f(local).normal();
        PoseStack.Pose target = ms.last();
        Matrix4f backup = new Matrix4f(target.pose());
        Matrix3f backupN = new Matrix3f(target.normal());
        target.pose().set(local);
        target.normal().set(normal);
        try {
            RANDOM.setSeed(42L);
            for (var quad : baked.getQuads(state, null, RANDOM)) {
                vb.putBulkData(target, quad, 1.0F, 1.0F, 1.0F, 1.0F, light, OverlayTexture.NO_OVERLAY);
            }
        } finally {
            target.pose().set(backup);
            target.normal().set(backupN);
        }
    }
}
