package eu.caoten.adventure_map_developer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
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
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.nbt.NbtCompound;
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


public class LockCommand {
    public static final SimpleCommandExceptionType INVALID_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.entity.valid"));
    public static final SimpleCommandExceptionType SELECT_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.entity"));
    public static final SimpleCommandExceptionType SELECT_VALID_BLOCK_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.block"));
    public static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.fail"));

    public static void register() {
        CommandRegistrationCallback.EVENT.register(LockCommand::registerCommand);
    }

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment)  {
        dispatcher.register(CommandManager.literal("lock").then(CommandManager.literal("coordinate").then(CommandManager.argument("pos", BlockPosArgumentType.blockPos()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).then(CommandManager.argument("lock", StringArgumentType.word()).executes(context ->
                runSingleBlockLock(context, BlockPosArgumentType.getLoadedBlockPos(context, "pos"), StringArgumentType.getString(context, "lock"))
        )))));
        dispatcher.register(CommandManager.literal("lock").then(CommandManager.literal("coordinate").then(CommandManager.argument("pos", BlockPosArgumentType.blockPos()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runSingleBlockLock(context, BlockPosArgumentType.getLoadedBlockPos(context, "pos"), Utils.randomString(10))
        ))));
        dispatcher.register(CommandManager.literal("lock").then(CommandManager.literal("entities").then(CommandManager.argument("entity", EntityArgumentType.entities()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runSingleEntityLock(context, EntityArgumentType.getEntities(context, "entity"))
        ))));
        dispatcher.register(CommandManager.literal("lock").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.argument("lock", StringArgumentType.word()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeLock(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), StringArgumentType.getString(context, "lock"), RangeType.SIMPLE, null)
        ))))));
        dispatcher.register(CommandManager.literal("lock").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.argument("lock", StringArgumentType.word()).then(CommandManager.literal("only").then(CommandManager.argument("selection", SelectionArgument.selection()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeLock(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), StringArgumentType.getString(context, "lock"), RangeType.ONLY, SelectionArgument.getSelection(context,"selection"))
        ))))))));
        dispatcher.register(CommandManager.literal("lock").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.argument("lock", StringArgumentType.word()).then(CommandManager.literal("except").then(CommandManager.argument("selection", SelectionArgument.selection()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeLock(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), StringArgumentType.getString(context, "lock"), RangeType.EXCEPT, SelectionArgument.getSelection(context,"selection"))
        ))))))));
    }

    private static int runSingleBlockLock(CommandContext<ServerCommandSource> context, BlockPos pos, String lock) throws CommandSyntaxException {
        BlockState state = context.getSource().getWorld().getBlockState(pos);
        Block block = state.getBlock();
        ServerWorld world = context.getSource().getWorld();
        boolean isLockUsed = false;
        if (block instanceof BedBlock) {
            world.setBlockState(pos, world.getBlockState(pos).with(BedBlock.OCCUPIED, true));
        } else if (state.isIn(Tags.LOCKABLE_BLOCK_ENTITIES)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LockableContainerBlockEntity) {
                lockBlockEntity(blockEntity, pos, lock);
                isLockUsed = true;
            } else {
                throw SELECT_VALID_BLOCK_EXCEPTION.create();
            }
        } else {
            throw SELECT_VALID_BLOCK_EXCEPTION.create();
        }
        if (isLockUsed) context.getSource().sendFeedback(() ->Text.translatable("commands.adventure_map_developer.block.lock.single.lock", pos.toShortString(), lock), true);
        else context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.block.lock.single", pos.toShortString()), true);
        return 1;
    }

    private static int runSingleEntityLock(CommandContext<ServerCommandSource> context, Collection<? extends Entity> entities) throws CommandSyntaxException {
        int total = 0;
        int locked = 0;
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
            if (entity.getType().isIn(Tags.LOCKABLE_ENTITIES)) {
                locked++;
                NbtCompound compound = new NbtCompound();
                compound.putBoolean("Invulnerable", true);
                if (entity instanceof ArmorStandEntity) {
                    compound.putInt("DisabledSlots", 4144959);
                } else if (entity instanceof ItemFrameEntity) {
                    compound.putBoolean("Fixed", true);
                }
                Utils.writeEntityNBT(compound, entity);
            }
        }
        int finalTotal = total;
        int finalLocked = locked;
        if (locked==0) throw FAILED_EXCEPTION.create();
        else if (locked != total) context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.entities.selection", finalLocked, finalLocked), true);
        else context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.entities", finalTotal), true);
        return 1;
    }

    private static int runRangeLock(CommandContext<ServerCommandSource> context, BlockPos pos1, BlockPos pos2, String lock, RangeType type, @Nullable SelectionArgument.Selection selection) throws CommandSyntaxException {
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
                case SIMPLE -> k += lockBlock(blockState, pos, serverWorld, lock);
                case EXCEPT -> {
                    if (blocks != null && !blocks.contains(blockState.getBlock())) {
                        k += lockBlock(blockState, pos, serverWorld, lock);
                    }
                }
                case ONLY -> {
                    if (blocks != null && blocks.contains(blockState.getBlock())) {
                        k += lockBlock(blockState, pos, serverWorld, lock);
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
                            k++;
                            lockEntity(entity);
                        }
                        case EXCEPT -> {
                            if (entities != null && !entities.contains(entityType)) {
                                k++;
                                lockEntity(entity);
                            }
                        }
                        case ONLY -> {
                            if (entities != null && entities.contains(entityType)) {
                                k++;
                                lockEntity(entity);
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
        context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.range", finalK), true);
        return finalK;
    }

    private static int lockBlock(BlockState state, BlockPos pos, World world, String lock) {
        Block block = state.getBlock();
        int i = 0;
        if (block instanceof BedBlock) {
            i++;
            world.setBlockState(pos, world.getBlockState(pos).with(BedBlock.OCCUPIED, true));
        } else if (state.isIn(Tags.LOCKABLE_BLOCK_ENTITIES)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LockableContainerBlockEntity) {
                i++;
                lockBlockEntity(blockEntity, pos, lock);
            }
        }
        return i;
    }

    private static void lockEntity(Entity entity) {
        NbtCompound compound = new NbtCompound();
        compound.putBoolean("Invulnerable", true);
        if (entity instanceof ArmorStandEntity) {
            compound.putInt("DisabledSlots", 4144959);
        } else if (entity instanceof ItemFrameEntity) {
            compound.putBoolean("Fixed", true);
        }
        Utils.writeEntityNBT(compound, entity);
    }

    private static NbtCompound createLock(String string) {
        NbtCompound compound = new NbtCompound();
        compound.putString("Lock",string);
        return compound;
    }

    private static void lockBlockEntity(BlockEntity blockEntity, BlockPos pos, String lock) {
        NbtCompound compound1 = blockEntity.createNbtWithIdentifyingData(blockEntity.getWorld().getRegistryManager());
        NbtCompound compound2 = compound1.copy().copyFrom(createLock(lock));
        Utils.writeBlockNBT(compound2, blockEntity, pos);
    }

    private enum RangeType {
        SIMPLE,
        ONLY,
        EXCEPT
    }
}