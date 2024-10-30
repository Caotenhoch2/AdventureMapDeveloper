package eu.caoten.adventure_map_developer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import eu.caoten.adventure_map_developer.utils.Utils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class HideCommand {
    public static final SimpleCommandExceptionType SELECT_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.entity"));
    public static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.adventure_map_developer.hide.fail"));


    public static void register() {
        CommandRegistrationCallback.EVENT.register(HideCommand::registerCommand);
    }

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment)  {
        dispatcher.register(CommandManager.literal("hide").then(CommandManager.literal("entities").then(CommandManager.argument("entity", EntityArgumentType.entities()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runEntityHide(context, EntityArgumentType.getEntities(context, "entity"))
        ))));
        dispatcher.register(CommandManager.literal("hide").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeHide(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), RangeType.SIMPLE, null)
        )))));
        dispatcher.register(CommandManager.literal("hide").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.literal("only").then(CommandManager.argument("entity", RegistryEntryReferenceArgumentType.registryEntry(commandRegistryAccess, RegistryKeys.ENTITY_TYPE)).suggests(EntityTypeSuggestionProvider.ENTITY_TYPE).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeHide(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), RangeType.ONLY, RegistryEntryReferenceArgumentType.getEntityType(context, "entity"))
        )))))));
        dispatcher.register(CommandManager.literal("hide").then(CommandManager.literal("range").then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos()).then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos()).then(CommandManager.literal("except").then(CommandManager.argument("entity", RegistryEntryReferenceArgumentType.registryEntry(commandRegistryAccess, RegistryKeys.ENTITY_TYPE)).suggests(EntityTypeSuggestionProvider.ENTITY_TYPE).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(context ->
                runRangeHide(context, BlockPosArgumentType.getLoadedBlockPos(context,"pos1"), BlockPosArgumentType.getLoadedBlockPos(context, "pos2"), RangeType.EXCEPT, RegistryEntryReferenceArgumentType.getEntityType(context, "entity"))
        )))))));
    }

    private static int runRangeHide(CommandContext<ServerCommandSource> context, BlockPos pos1, BlockPos pos2, RangeType rangeType, @Nullable RegistryEntry.Reference<EntityType<?>> entityTypeAddition) throws CommandSyntaxException {
        EntityType<?> entityTypeA = null;
        if (entityTypeAddition != null) entityTypeA = entityTypeAddition.value();
        BlockBox box = BlockBox.create(pos1, pos2);
        ServerWorld serverWorld = context.getSource().getWorld();
        int hidden = 0;
        int total = 0;
        for (Entity entity : serverWorld.getOtherEntities(null, Box.from(box))) {
            if (box.contains(entity.getBlockPos())) {
                EntityType<?> entityType = entity.getType();
                switch (rangeType) {
                    case SIMPLE -> {
                        total++;
                        if (hideEntity(entity, context)) hidden++;
                    }
                    case EXCEPT -> {
                        if (!entityType.equals(entityTypeA)) {
                            total++;
                            if (hideEntity(entity, context)) hidden++;
                        }
                    }
                    case ONLY -> {
                        if (entityType.equals(entityTypeA)) {
                            total++;
                            if (hideEntity(entity, context)) hidden++;
                        }
                    }
                }
            }
        }
        int finalHidden = hidden;
        int finalTotal = total;
        if (hidden == 0) {
            throw FAILED_EXCEPTION.create();
        }
        else if (hidden != total) context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.hide.entities.selection", finalTotal, finalHidden), true);
        else context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.hide.entities", finalTotal), true);
        return finalHidden;
    }

    private static int runEntityHide(CommandContext<ServerCommandSource> context, Collection<? extends Entity> entities) throws CommandSyntaxException {
        int total = entities.size();
        int hidden = 0;
        if (entities.isEmpty()) {
            throw SELECT_ENTITY_EXCEPTION.create();
        }
        for (Entity entity : entities) {
            if (hideEntity(entity, context)) hidden++;
        }
        int finalHidden = hidden;
        if (hidden == 0) {
            throw FAILED_EXCEPTION.create();
        }
        else if (hidden != total) context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.hide.entities.selection", total, finalHidden), true);
        else context.getSource().sendFeedback(() -> Text.translatable("commands.adventure_map_developer.hide.entities", total), true);
        return 1;
    }

    private static boolean hideEntity(Entity entity, CommandContext<ServerCommandSource> context) {
        if (entity instanceof ArmorStandEntity || entity instanceof ItemFrameEntity) {
            NbtCompound compound = new NbtCompound();
            compound.putBoolean("Invisible", true);
            Utils.writeEntityNBT(compound, entity);
            return true;
        } else if (entity instanceof LivingEntity livingEntity) {
            StatusEffectInstance instance = new StatusEffectInstance(StatusEffects.INVISIBILITY, -1, 255, false, false);
            livingEntity.addStatusEffect(instance, context.getSource().getEntity());
            return true;
        }
        return false;
    }
}
