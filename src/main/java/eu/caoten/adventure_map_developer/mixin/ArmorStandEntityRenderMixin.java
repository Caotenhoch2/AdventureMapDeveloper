package eu.caoten.adventure_map_developer.mixin;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.state.ArmorStandEntityRenderState;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntityRenderer.class)
public abstract class ArmorStandEntityRenderMixin {
    @Shadow @Final public static Identifier TEXTURE;

    @Inject(at = @At("HEAD"), method = "getRenderLayer(Lnet/minecraft/client/render/entity/state/ArmorStandEntityRenderState;ZZZ)Lnet/minecraft/client/render/RenderLayer;", cancellable = true)
    private void getRenderLayer(ArmorStandEntityRenderState armorStandEntityRenderState, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<RenderLayer> cir) {
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get()) {
            if (!showBody) {
                cir.setReturnValue(RenderLayer.getItemEntityTranslucentCull(TEXTURE));
            }
        }
    }
}
