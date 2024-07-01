package eu.caoten.adventure_map_developer.mixin;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LightBlock.class)
public class LightBlockMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static AbstractBlock.Settings init(AbstractBlock.Settings settings) {
        return settings.noCollision();
    }

    @Inject(at = @At("RETURN"), method = "getOutlineShape", cancellable = true)
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        Optional<Boolean> on = ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS);
        on.ifPresent(aBoolean -> cir.setReturnValue(aBoolean ? VoxelShapes.fullCube() : cir.getReturnValue()));
    }

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
