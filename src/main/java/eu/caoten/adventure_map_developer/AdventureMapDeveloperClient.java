package eu.caoten.adventure_map_developer;

import eu.caoten.adventure_map_developer.event.EntityRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class AdventureMapDeveloperClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(new EntityRender());
    }
}
