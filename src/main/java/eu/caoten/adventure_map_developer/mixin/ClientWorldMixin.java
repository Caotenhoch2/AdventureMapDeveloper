package eu.caoten.adventure_map_developer.mixin;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Inject(at = @At("HEAD"), method = "doRandomBlockDisplayTicks", cancellable = true)
    private void getBlockParticle(int centerX, int centerY, int centerZ, CallbackInfo ci) {
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS).get()) {
            ci.cancel();
        }
    }
}
