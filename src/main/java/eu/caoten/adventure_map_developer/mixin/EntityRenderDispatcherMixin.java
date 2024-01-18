package eu.caoten.adventure_map_developer.mixin;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Shadow public abstract <T extends Entity> EntityRenderer<? super T> getRenderer(T entity);

    @Inject(at = @At("TAIL"), method = "render")
    private <E extends Entity> void render(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity instanceof InteractionEntity && ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get()) {
            EntityRenderer<? super E> entityRenderer = getRenderer(entity);
            Vec3d vec3d = entityRenderer.getPositionOffset(entity, tickDelta);
            double d = x + vec3d.getX();
            double e = y + vec3d.getY();
            double f = z + vec3d.getZ();
            matrices.push();
            matrices.translate(d, e, f);
            matrices.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
            Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
            WorldRenderer.drawBox(matrices, vertexConsumers.getBuffer(RenderLayer.getLines()), box, 128, 0, 255, 1.0F);
            matrices.pop();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderHitbox", cancellable = true)
    private static void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) {
        if (entity instanceof InteractionEntity && ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get()) {
            Vec3d vec3d2 = entity.getRotationVec(tickDelta);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            Matrix3f matrix3f = matrices.peek().getNormalMatrix();
            vertices.vertex(matrix4f, 0.0F, entity.getStandingEyeHeight(), 0.0F).color(0, 0, 255, 255).normal(matrix3f, (float)vec3d2.x, (float)vec3d2.y, (float)vec3d2.z).next();
            vertices.vertex(matrix4f, (float)(vec3d2.x * 2.0), (float)((double)entity.getStandingEyeHeight() + vec3d2.y * 2.0), (float)(vec3d2.z * 2.0)).color(0, 0, 255, 255).normal(matrix3f, (float)vec3d2.x, (float)vec3d2.y, (float)vec3d2.z).next();
            ci.cancel();
        }
    }
}
