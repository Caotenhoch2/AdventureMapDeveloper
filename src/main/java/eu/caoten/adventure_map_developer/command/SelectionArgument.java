package eu.caoten.adventure_map_developer.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import eu.caoten.adventure_map_developer.AdventureMapDeveloper;
import eu.caoten.adventure_map_developer.utils.Tags;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SelectionArgument implements ArgumentType<SelectionArgument.Selection> {
    public static final DynamicCommandExceptionType INVALID_SELECTION = new DynamicCommandExceptionType(o -> Text.literal("Invalid selection: " + o));

    public static SelectionArgument selection() {
        return new SelectionArgument();
    }

    @Override
    public Selection parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor();
        if (!reader.canRead()) {
            reader.skip();
        }
        while (reader.canRead() && !Character.isWhitespace(reader.peek())) {
            reader.skip();
        }
        Selection selection = new Selection();
        String[] selections = reader.getString().split(" ");
        selections = Arrays.copyOfRange(selections,10, selections.length);
        System.out.println(Arrays.toString(selections));
        for (String string : selections) {
            Optional<EntityType<?>> type = EntityType.get(string);
            if (type.isPresent() && type.get().isIn(Tags.LOCKABLE_ENTITIES)) {
                selection.addToSelection(type.get());
            } else {
                Block block = Registries.BLOCK.get(Identifier.tryParse(string));
                if (!(block instanceof AirBlock) && block.getDefaultState().isIn(Tags.LOCKABLE_BLOCKS)) {
                    selection.addToSelection(block);
                } else {
                    reader.setCursor(argBeginning);
                    throw INVALID_SELECTION.createWithContext(reader, Text.literal(string));
                }
            }
        }
        return selection;
    }

    public static Selection getSelection(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Selection.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Registries.ENTITY_TYPE.forEach(entityType -> {
            if (entityType.isIn(Tags.LOCKABLE_ENTITIES)) builder.suggest(EntityType.getId(entityType).toString());
        });
        Registries.BLOCK.forEach(block -> {
            if (block.getDefaultState().isIn(Tags.LOCKABLE_BLOCKS)) builder.suggest(Registries.BLOCK.getId(block).toString());
        });
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return Set.of("minecraft:armor_stand","minecraft:red_bed","minecraft:chest");
    }

    public static class Selection {
        private final Set<EntityType<?>> selectedEntities = new HashSet<>();
        private final Set<Block> selectedBlocks = new HashSet<>();

        public <T> void addToSelection(T addition) {
            if (addition instanceof EntityType<?> entityType) {
                selectedEntities.add(entityType);
            } else if (addition instanceof Block block) {
                selectedBlocks.add(block);
            } else {
                AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "Invalid addition to the selection!");
            }
        }

        public Set<EntityType<?>> getSelectedEntities() {
            return selectedEntities;
        }

        public Set<Block> getSelectedBlocks() {
            return selectedBlocks;
        }
    }
}
