package eu.caoten.adventure_map_developer.event;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityRender implements WorldRenderEvents.AfterEntities {

    @Override
    public void afterEntities(WorldRenderContext context) {
        if (!ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get()) {
            return;
        }
        MatrixStack matrices = context.matrixStack();
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server.isSingleplayer()) {
            server.getWorlds().forEach(serverWorld -> serverWorld.iterateEntities().forEach(entity -> {
                if (entity instanceof MarkerEntity) {
                    double x = MathHelper.lerp(context.tickCounter().getTickDelta(true), entity.lastRenderX, entity.getX()) - context.camera().getPos().getX();
                    double y = MathHelper.lerp(context.tickCounter().getTickDelta(true), entity.lastRenderY, entity.getY()) - context.camera().getPos().getY();
                    double z = MathHelper.lerp(context.tickCounter().getTickDelta(true), entity.lastRenderZ, entity.getZ()) - context.camera().getPos().getZ();
                    Vec3d vec3d = MinecraftClient.getInstance().player.isInSneakingPose() ? new Vec3d(0.0, -0.125, 0.0) : Vec3d.ZERO;
                    double d = x + vec3d.getX();
                    double e = y + vec3d.getY();
                    double f = z + vec3d.getZ();
                    VertexConsumer vertexConsumer = context.consumers().getBuffer(RenderLayer.getLines());
                    matrices.push();
                    matrices.translate(d, e, f);
                    matrices.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
                    Box box = new Box(0,0,0,0.1,1,0.1);
                    WorldRenderer.drawBox(matrices, vertexConsumer, box, 0, 128, 255, 1.0F);
                    matrices.pop();
                }
            }));
        }
    }
}
