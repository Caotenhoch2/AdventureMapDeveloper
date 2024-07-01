package eu.caoten.adventure_map_developer.config.api;

import eu.caoten.adventure_map_developer.register.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.util.*;

public class ClientConfigBuilder {
    final String ID;
    final Map<String, Option> clientConfigOptionList = new HashMap<>();
    final List<String> optionNameList = new ArrayList<>();
    String save_directory = "config";
    @Nullable Code code = null;
    String file;

    /**
     * Create a new clientside config
     * @param id The id that the config is supposed to have
     * @param clientConfigOptions The options that are supposed to be saved in the config
     */
    public ClientConfigBuilder(String id, Option... clientConfigOptions) {
        this.ID = id;
        for (Option option : clientConfigOptions) {
            this.optionNameList.add(option.NAME);
            this.clientConfigOptionList.put(option.NAME, option);
        }
    }

    /**
     * Create the config
     */
    public void build() {
        file = save_directory + "/" + ID + ".properties";
        Registries.CLIENT_CONFIGS.register(Identifier.of(ID), this);
    }

    /**
     * Get the option associated with a name
     * @param name The name
     * @return The option
     */
    public Option getOption(String name) {
        return clientConfigOptionList.get(name);
    }

    /**
     * The standard implementation of an option, implement this if you want to create your own
     */
    public abstract static class Option {
        public final String NAME;
        public final Typ TYP;
        @Nullable
        public String comment = null;

        private Option(String name, Typ typ) {
            this.NAME = name;
            this.TYP = typ;
        }

        abstract void write(BufferedWriter writer);
        abstract void read(String file);
    }

    public static class StringOption extends Option {
        /**
         * The default value of the option
         */
        public final String DEFAULT;
        /**
         * The current value of the option
         */
        @Nullable
        public String value = null;

        /**
         * Creates a string option
         * @param name The name of the option
         * @param defaultValue The default value of the option
         */
        public StringOption(String name, String defaultValue) {
            super(name, Typ.STRING);
            this.DEFAULT = defaultValue;
        }

        @Override
        void write(BufferedWriter writer) {
            StandardConfig.writeStringProperties(writer, NAME, Objects.requireNonNullElse(value, DEFAULT), comment);
        }

        @Override
        void read(String file) {
            value = StandardConfig.readStringValue(file, NAME).orElse(DEFAULT);
        }
    }

    public static class IntegerOption extends Option {
        /**
         * The default value of the option
         */
        public final Integer DEFAULT;
        /**
         * The current value of the option
         */
        @Nullable
        public Integer value = null;
        @Nullable Integer min = null;
        @Nullable Integer max = null;

        /**
         * Creates an integer option
         * @param name The name of the Option
         * @param defaultValue The default value of the option
         */
        public IntegerOption(String name, Integer defaultValue) {
            super(name, Typ.INTEGER);
            this.DEFAULT = defaultValue;
        }

        /**
         * Limit's the option by a minimum value
         * @param min The minimum value
         * @return The integer option
         */
        public IntegerOption setMin(@Nullable Integer min) {
            this.min = min;
            return this;
        }

        /**
         * Limit's the option by a maximum value
         * @param max The maximum value
         * @return The integer option
         */
        public IntegerOption setMax(@Nullable Integer max) {
            this.max = max;
            return this;
        }


        @Override
        void write(BufferedWriter writer) {
            StandardConfig.writeIntProperties(writer, NAME, Objects.requireNonNullElse(value, DEFAULT), comment);
        }

        @Override
        void read(String file) {
            if (min != null && max != null) {
                value = StandardConfig.readIntValue(file, NAME, min, max).orElse(DEFAULT);
            } else if (min != null) {
                value = StandardConfig.readIntValueWithMin(file, NAME, min).orElse(DEFAULT);
            } else if (max != null) {
                value = StandardConfig.readIntValueWithMax(file, NAME, max).orElse(DEFAULT);
            } else {
                value = StandardConfig.readIntValue(file, NAME).orElse(DEFAULT);
            }
        }
    }

    public static class BooleanOption extends Option {
        /**
         * The default value of the option
         */
        public final Boolean DEFAULT;
        /**
         * The current value of the option
         */
        @Nullable
        public Boolean value = null;

        /**
         * Creates a boolean option
         * @param name The name of the option
         * @param defaultValue The default value of the option
         */
        public BooleanOption(String name, Boolean defaultValue) {
            super(name, Typ.BOOLEAN);
            this.DEFAULT = defaultValue;
        }

        @Override
        void write(BufferedWriter writer) {
            StandardConfig.writeBooleanProperties(writer, NAME, Objects.requireNonNullElse(value, DEFAULT), comment);
        }

        @Override
        void read(String file) {
            value = StandardConfig.readBooleanValue(file, NAME).orElse(DEFAULT);
        }
    }

    public static class Comment extends Option {
        final String COMMENT;

        /**
         * Creates a comment
         * @param name The name identifying the option (Not displayed anywhere)
         * @param comment The comment that gets written
         */
        public Comment(String name, String comment) {
            super(name, Typ.COMMENT);
            this.COMMENT = comment;
        }

        @Override
        void write(BufferedWriter writer) {
            StandardConfig.writeComment(writer, COMMENT, false);
        }

        @Override
        void read(String file) {
        }
    }

    public static class EnumOption extends Option {
        /**
         * The default value of the option
         */
        public final String DEFAULT;
        /**
         * All possible values of the option
         */
        public final String[] possibleValues;
        /**
         * The current value of the option
         */
        public String value = null;

        /**
         * Creates an enum option
         * @param name The name of the option
         * @param defaultValue The default value of the option
         * @param possibleValues All possible values of the option
         */
        public EnumOption(String name, String defaultValue, String... possibleValues) {
            super(name, Typ.ENUM);
            this.DEFAULT = defaultValue;
            this.possibleValues = possibleValues;
        }

        @Override
        void write(BufferedWriter writer) {
            StandardConfig.writeEnumProperties(writer, NAME, Objects.requireNonNullElse(value, DEFAULT), comment);
        }

        @Override
        void read(String file) {
            value = StandardConfig.readEnumValue(file, NAME, possibleValues).orElse(DEFAULT);
        }
    }

    enum Typ {
        STRING,
        BOOLEAN,
        INTEGER,
        ENUM,
        COMMENT
    }

    public interface Code {
        void onRead(ClientConfigBuilder builder);
    }
}
