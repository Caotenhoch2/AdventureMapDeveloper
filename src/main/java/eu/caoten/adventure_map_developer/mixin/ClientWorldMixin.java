package eu.caoten.adventure_map_developer.mixin;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Unique
    private static final Set<Item> BLOCK_MARKER_ITEMS;

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void randomBlockDisplayTick(int centerX, int centerY, int centerZ, int radius, Random random, @Nullable Block block, BlockPos.Mutable pos);


    @Inject(at = @At("HEAD"), method = "doRandomBlockDisplayTicks", cancellable = true)
    private void getBlockParticle(int centerX, int centerY, int centerZ, CallbackInfo ci) {
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS).get()) {
            GameMode gameMode = this.client.interactionManager.getCurrentGameMode();
            if (gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR) {
                for (Item item : BLOCK_MARKER_ITEMS) {
                    Random random = Random.create();
                    BlockItem blockItem = (BlockItem)item;
                    Block block = blockItem.getBlock();
                    BlockPos.Mutable mutable = new BlockPos.Mutable();
                    for(int j = 0; j < 667; ++j) {
                        this.randomBlockDisplayTick(centerX, centerY, centerZ, 16, random, block, mutable);
                        this.randomBlockDisplayTick(centerX, centerY, centerZ, ClientConfigValues.getIntegerOption(ClientConfig.NAME, ClientConfig.BLOCK_MAX_DISTANCE).get(), random, block, mutable);
                    }
                }
            }
            ci.cancel();
        }
    }

    static {
        BLOCK_MARKER_ITEMS = Set.of(Items.BARRIER, Items.LIGHT, Items.STRUCTURE_VOID);
    }
}
