package eu.caoten.adventure_map_developer.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.UUID;

public class Utils {

    public static void writeBlockNBT(NbtCompound nbt, BlockEntity blockEntity, BlockPos pos) {
        BlockState blockState = blockEntity.getWorld().getBlockState(pos);
        blockEntity.read(nbt, blockEntity.getWorld().getRegistryManager());
        blockEntity.markDirty();
        blockEntity.getWorld().updateListeners(pos, blockState, blockState, 3);
    }

    public static void writeEntityNBT(NbtCompound nbt, Entity entity) {
        NbtCompound nbtCompound = NbtPredicate.entityToNbt(entity);
        NbtCompound compound = nbtCompound.copy().copyFrom(nbt);
        if (entity instanceof PlayerEntity) {
            return;
        }
        UUID uUID = entity.getUuid();
        entity.readNbt(compound);
        entity.setUuid(uUID);
    }

    public static String randomString(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] symbols = (upper + upper.toLowerCase() + "0123456789").toCharArray();
        char[] buf = new char[length];
        Random random = new Random();
        for (int i = 0; i < buf.length; ++i)
            buf[i] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}
