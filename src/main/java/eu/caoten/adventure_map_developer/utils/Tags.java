package eu.caoten.adventure_map_developer.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Tags {

    public static final TagKey<Block> LOCKABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c","lockable"));
    public static final TagKey<Block> LOCKABLE_BLOCK_ENTITIES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c","lockable_block_entities"));
    public static final TagKey<EntityType<?>> LOCKABLE_ENTITIES = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("c","lockable"));
}
