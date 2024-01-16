package eu.caoten.adventure_map_developer;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigSystem;
import eu.caoten.adventure_map_developer.keybinds.Keybindings;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventureMapDeveloper implements ModInitializer {
    public static final String MOD_ID = "adventure_map_developer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);;
    public static final String LOGGER_PREFIX = "[AMD]: ";

    /**
     * For anybody interested, all classes found in the packages "buttons", "config/api", "screen" and "register" are a stripped down version, copied from a private, self-written API I am currently working on.
     * They will eventually be replaced with a reference to the API once I release it.
     */
    @Override
    public void onInitialize() {
        ClientConfig.register();
        ClientConfigSystem.read(ClientConfig.NAME);
        Keybindings.register();
        ClientTickEvents.END_CLIENT_TICK.register(Keybindings::onPress);
    }
}
