package eu.caoten.adventure_map_developer.config.api;

import eu.caoten.adventure_map_developer.AdventureMapDeveloper;
import eu.caoten.adventure_map_developer.register.Registries;
import net.minecraft.util.Identifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import static eu.caoten.adventure_map_developer.config.api.StandardConfig.prepareConfig;

public class ClientConfigSystem {

    /**
     * Read a specific client config identified by its name
     * @param name The name of the config
     */
    public static void read(String name) {
        Optional<ClientConfigBuilder> builder = Registries.CLIENT_CONFIGS.get(new Identifier(name));
        builder.ifPresent(ClientConfigSystem::read);
    }

    /**
     * Read a specific client config identified by its builder
     * @param builder The builder of the config
     */
    public static void read(ClientConfigBuilder builder) {
        File config = new File(builder.file);
        if (!(config.isFile() && config.exists())) {
            write(builder);
        } else {
            for (String optionName : builder.optionNameList) {
                ClientConfigBuilder.Option option = builder.getOption(optionName);
                option.read(builder.file);
                if (builder.code != null) {
                    builder.code.onRead(builder);
                }
            }
            writeFull(builder);
            AdventureMapDeveloper.LOGGER.info(AdventureMapDeveloper.LOGGER_PREFIX + "Read client config " + builder.ID);
        }
    }

    /**
     * Read all client configs
     */
    public static void read() {
        for (ClientConfigBuilder builder : Registries.CLIENT_CONFIGS.getAll()) {
            read(builder);
        }
    }

    /**
     * Writes the config for a specific id
     * @param configID The id (name) of the config that is supposed to be written
     */
    public static void writeConfig(String configID) {
        if (Registries.CLIENT_CONFIGS.contains(new Identifier(configID))) {
            ClientConfigSystem.write(Registries.CLIENT_CONFIGS.get(new Identifier(configID)).get());
        }
    }

    /**
     * Write a specific client config
     * @param builder The specific client config that is supposed to be written
     */
    public static void write(ClientConfigBuilder builder) {
        prepareConfig();
        writeFull(builder);
        AdventureMapDeveloper.LOGGER.info(AdventureMapDeveloper.LOGGER_PREFIX + "Wrote client config " + builder.ID);
    }

    private static void writeFull(ClientConfigBuilder builder) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(builder.file));
            for (String optionName : builder.optionNameList) {
                ClientConfigBuilder.Option option = builder.getOption(optionName);
                option.write(writer);
            }
            writer.close();
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error has occurred while writing the config for the file " + builder.ID);
        }
    }
}
