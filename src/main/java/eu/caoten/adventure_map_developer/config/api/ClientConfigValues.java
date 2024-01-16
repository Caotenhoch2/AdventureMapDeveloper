package eu.caoten.adventure_map_developer.config.api;

import eu.caoten.adventure_map_developer.register.Registries;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.Optional;

public class ClientConfigValues {


    /**
     * Gets the current value associated to a string option
     * @param configID The id (name) of the config
     * @param optionID The name of the option
     * @return The option, if it exists
     */
    public static Optional<String> getStringOption(String configID, String optionID) {
        ClientConfigBuilder.Option option = Registries.CLIENT_CONFIGS.get(new Identifier(configID)).get().clientConfigOptionList.get(optionID);
        if (option instanceof ClientConfigBuilder.StringOption stringOption) {
            return Optional.of(Objects.requireNonNullElse(stringOption.value, stringOption.DEFAULT));
        }
        return Optional.empty();
    }

    /**
     * Gets the current value associated to an integer option
     * @param configID The id (name) of the config
     * @param optionID The name of the option
     * @return The option, if it exists
     */
    public static Optional<Integer> getIntegerOption(String configID, String optionID) {
        ClientConfigBuilder.Option option = Registries.CLIENT_CONFIGS.get(new Identifier(configID)).get().clientConfigOptionList.get(optionID);
        if (option instanceof ClientConfigBuilder.IntegerOption integerOption) {
            return Optional.of(Objects.requireNonNullElse(integerOption.value, integerOption.DEFAULT));
        }
        return Optional.empty();
    }

    /**
     * Gets the current value associated to a boolean option
     * @param configID The id (name) of the config
     * @param optionID The name of the option
     * @return The option, if it exists
     */
    public static Optional<Boolean> getBooleanOption(String configID, String optionID) {
        ClientConfigBuilder.Option option = Registries.CLIENT_CONFIGS.get(new Identifier(configID)).get().clientConfigOptionList.get(optionID);
        if (option instanceof ClientConfigBuilder.BooleanOption booleanOption) {
            return Optional.of(Objects.requireNonNullElse(booleanOption.value, booleanOption.DEFAULT));
        }
        return Optional.empty();
    }

    /**
     * Write the value of a string option
     * @param configID The name of the config
     * @param optionID The name of the option
     * @param value The new value of the option
     */
    public static void writeStringOption(String configID, String optionID, String value) {
        Optional<ClientConfigBuilder> optionalBuilder = Registries.CLIENT_CONFIGS.get(new Identifier(configID));
        if (optionalBuilder.isPresent()) {
            ClientConfigBuilder.Option option = optionalBuilder.get().clientConfigOptionList.get(optionID);
            if (option instanceof ClientConfigBuilder.StringOption stringOption) {
                stringOption.value = value;
            }
        }
    }

    /**
     * Write the value of an integer option
     * @param configID The name of the config
     * @param optionID The name of the option
     * @param value The new value of the option
     */
    public static void writeIntegerOption(String configID, String optionID, Integer value) {
        Optional<ClientConfigBuilder> optionalBuilder = Registries.CLIENT_CONFIGS.get(new Identifier(configID));
        if (optionalBuilder.isPresent()) {
            ClientConfigBuilder.Option option = optionalBuilder.get().clientConfigOptionList.get(optionID);
            if (option instanceof ClientConfigBuilder.IntegerOption integerOption) {
                integerOption.value = value;
            }
        }
    }

    /**
     * Write the value of a boolean option
     * @param configID The name of the config
     * @param optionID The name of the option
     * @param value The new value of the option
     */
    public static void writeBooleanOption(String configID, String optionID, Boolean value) {
        Optional<ClientConfigBuilder> optionalBuilder = Registries.CLIENT_CONFIGS.get(new Identifier(configID));
        if (optionalBuilder.isPresent()) {
            ClientConfigBuilder.Option option = optionalBuilder.get().clientConfigOptionList.get(optionID);
            if (option instanceof ClientConfigBuilder.BooleanOption booleanOption) {
                booleanOption.value = value;
            }
        }
    }

    /**
     * Toggle a boolean option (true to false, false to true)
     * @param configID The name of the config
     * @param optionID The name of the boolean option
     */
    public static void toggleBooleanOption(String configID, String optionID) {
        Optional<ClientConfigBuilder> optionalBuilder = Registries.CLIENT_CONFIGS.get(new Identifier(configID));
        if (optionalBuilder.isPresent()) {
            ClientConfigBuilder.Option option = optionalBuilder.get().clientConfigOptionList.get(optionID);
            if (option instanceof ClientConfigBuilder.BooleanOption booleanOption) {
                if (booleanOption.value != null) {
                    booleanOption.value = !booleanOption.value;
                } else {
                    booleanOption.value = !booleanOption.DEFAULT;
                }
            }
        }
    }
}
