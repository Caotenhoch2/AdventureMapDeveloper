package eu.caoten.adventure_map_developer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import eu.caoten.adventure_map_developer.utils.Tags;
import eu.caoten.adventure_map_developer.utils.Utils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;


public class UnlockCommand {
    public static final SimpleCommandExceptionType INVALID_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.entity.valid"));
    public static final SimpleCommandExceptionType SELECT_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.entity"));
    public static final SimpleCommandExceptionType SELECT_VALID_BLOCK_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.block"));
    public static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.unlock.fail"));

    public static void register() {
        CommandRegistrationCallback.EVENT.register(UnlockCommand::registerCommand);
    }

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment)  {
        dispatcher.register(CommandManager.literal("unlock").then(CommandManager.literal("coordinate").then(CommandManager.argument("pos", BlockPosArgumentType.blockPos()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runSingleBlockUnlock(context, BlockPosArgumentType.getLoadedBlockPos(context, "pos"))
        ))));
        dispatcher.register(CommandManager.literal("unlock").then(CommandManager.literal("entities").then(CommandManager.argument("entity", EntityArgumentType.entities()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runSingleEntityUnlock(context, EntityArgumentType.getEntities(context, "entity"))
        ))));
        dispatcher.register(CommandManager.literal("unlock").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeUnlock(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), RangeType.SIMPLE, null)
        )))));
        dispatcher.register(CommandManager.literal("unlock").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.literal("only").then(CommandManager.argument("selection", SelectionArgument.selection()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeUnlock(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), RangeType.ONLY, SelectionArgument.getSelection(context,"selection"))
        )))))));
        dispatcher.register(CommandManager.literal("unlock").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.literal("except").then(CommandManager.argument("selection", SelectionArgument.selection()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeUnlock(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), RangeType.EXCEPT, SelectionArgument.getSelection(context,"selection"))
        )))))));
    }

    private static int runSingleBlockUnlock(CommandContext<ServerCommandSource> context, BlockPos pos) throws CommandSyntaxException {
        BlockState state = context.getSource().getWorld().getBlockState(pos);
        Block block = state.getBlock();
        ServerWorld world = context.getSource().getWorld();
        if (block instanceof BedBlock) {
            world.setBlockState(pos, world.getBlockState(pos).with(BedBlock.OCCUPIED, false));
        } else if (state.isIn(Tags.LOCKABLE_BLOCK_ENTITIES)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LockableContainerBlockEntity) {
                unlockBlockEntity(blockEntity, pos);
            } else {
                throw SELECT_VALID_BLOCK_EXCEPTION.create();
            }
        } else {
            throw SELECT_VALID_BLOCK_EXCEPTION.create();
        }
        context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.unlock.block.single", pos.toShortString()), true);
        return 1;
    }

    private static int runSingleEntityUnlock(CommandContext<ServerCommandSource> context, Collection<? extends Entity> entities) throws CommandSyntaxException {
        int total = 0;
        int unlocked = 0;
        if (entities.isEmpty()) {
            throw SELECT_ENTITY_EXCEPTION.create();
        }
        if (entities.size() == 1) {
            for (Entity entity : entities) {
                if (!entity.getType().isIn(Tags.LOCKABLE_ENTITIES)) {
                    throw INVALID_ENTITY_EXCEPTION.create();
                }
            }
        }
        for (Entity entity : entities) {
            total++;
            if (unlockEntity(entity)) unlocked++;
        }
        int finalTotal = total;
        int finalUnlocked = unlocked;
        if (unlocked==0) throw FAILED_EXCEPTION.create();
        else if (unlocked != total) context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.unlock.entities.selection", finalUnlocked, finalUnlocked), true);
        else context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.unlock.entities", finalTotal), true);
        return 1;
    }

    private static int runRangeUnlock(CommandContext<ServerCommandSource> context, BlockPos pos1, BlockPos pos2, RangeType type, @Nullable SelectionArgument.Selection selection) throws CommandSyntaxException {
        BlockBox box = BlockBox.create(pos1, pos2);
        ServerWorld serverWorld = context.getSource().getWorld();
        int k = 0;
        Set<Block> blocks = null;
        Set<EntityType<?>> entities = null;
        if (selection!=null) {
            blocks = selection.getSelectedBlocks();
            entities = selection.getSelectedEntities();
        }
        for (BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            BlockState blockState = context.getSource().getWorld().getBlockState(pos);
            switch (type) {
                case SIMPLE -> k += unlockBlock(blockState, pos, serverWorld);
                case EXCEPT -> {
                    if (blocks != null && !blocks.contains(blockState.getBlock())) {
                        k += unlockBlock(blockState, pos, serverWorld);
                    }
                }
                case ONLY -> {
                    if (blocks != null && blocks.contains(blockState.getBlock())) {
                        k += unlockBlock(blockState, pos, serverWorld);
                    }
                }
            }

        }
        for (Entity entity : serverWorld.getOtherEntities(null, Box.from(box))) {
            if (box.contains(entity.getBlockPos())) {
                EntityType<?> entityType = entity.getType();
                if (entityType.isIn(Tags.LOCKABLE_ENTITIES)) {
                    switch (type) {
                        case SIMPLE -> {
                            if (unlockEntity(entity)) k++;
                        }
                        case EXCEPT -> {
                            if (entities != null && !entities.contains(entityType)) {
                                if (unlockEntity(entity)) k++;
                            }
                        }
                        case ONLY -> {
                            if (entities != null && entities.contains(entityType)) {
                                if (unlockEntity(entity)) k++;
                            }
                        }
                    }
                }
            }
        }
        if (k == 0) {
            throw FAILED_EXCEPTION.create();
        }
        int finalK = k;
        context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.unlock.range", finalK), true);
        return finalK;
    }

    private static int unlockBlock(BlockState state, BlockPos pos, World world) {
        Block block = state.getBlock();
        int i = 0;
        if (block instanceof BedBlock) {
            i++;
            world.setBlockState(pos, world.getBlockState(pos).with(BedBlock.OCCUPIED, false));
        } else if (state.isIn(Tags.LOCKABLE_BLOCK_ENTITIES)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LockableContainerBlockEntity) {
                if (unlockBlockEntity(blockEntity, pos)) i++;
            }
        }
        return i;
    }

    private static boolean unlockEntity(Entity entity) {
        if (entity instanceof PlayerEntity) {
            return false;
        }
        boolean unlocked = false;
        NbtCompound nbtCompound = NbtPredicate.entityToNbt(entity);
        if (nbtCompound.getBoolean("Invulnerable")) {
            nbtCompound.putBoolean("Invulnerable", false);
            unlocked = true;
        }
        if (nbtCompound.contains("DisabledSlots")) {
            nbtCompound.remove("DisabledSlots");
            unlocked = true;
        }
        if (nbtCompound.getBoolean("Fixed")) {
            nbtCompound.putBoolean("Fixed", false);
            unlocked = true;
        }
        if (entity instanceof PlayerEntity) {
            return false;
        }
        if (unlocked) {
            UUID uUID = entity.getUuid();
            entity.readNbt(nbtCompound);
            entity.setUuid(uUID);
        }
        return unlocked;
    }

    private static boolean unlockBlockEntity(BlockEntity blockEntity, BlockPos pos) {
        NbtCompound compound1 = blockEntity.createNbtWithIdentifyingData(blockEntity.getWorld().getRegistryManager());
        if (compound1.contains("Lock")) {
            compound1.remove("Lock");
            Utils.writeBlockNBT(compound1, blockEntity, pos);
            return true;
        }
        return false;
    }
}