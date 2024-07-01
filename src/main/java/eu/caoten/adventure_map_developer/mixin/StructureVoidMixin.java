package eu.caoten.adventure_map_developer.mixin;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.StructureVoidBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(StructureVoidBlock.class)
public class StructureVoidMixin {

    @Inject(at = @At("RETURN"), method = "getRenderType", cancellable = true)
    private void getRenderTyp(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        Optional<Boolean> on = ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS);
        on.ifPresent(onBool -> {
            if (onBool) {
                cir.setReturnValue(BlockRenderType.MODEL);
            }
        });
    }
}
