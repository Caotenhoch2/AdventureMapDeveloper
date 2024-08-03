package eu.caoten.adventure_map_developer.command;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class EntityTypeSuggestionProvider {
    public static final SuggestionProvider<ServerCommandSource> ENTITY_TYPE;

    static {
        ENTITY_TYPE = SuggestionProviders.register(Identifier.of("adventure_map_developer","summonable_entities"), (context, builder) -> {
            return CommandSource.suggestFromIdentifier(Registries.ENTITY_TYPE.stream().filter((entityType) -> {
                return entityType.isEnabled(context.getSource().getEnabledFeatures());
            }), builder, EntityType::getId, (entityType) -> {
                return Text.translatable(Util.createTranslationKey("entity", EntityType.getId(entityType)));
            });
        });
    }
}
