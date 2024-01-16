package eu.caoten.adventure_map_developer.config.api;

import eu.caoten.adventure_map_developer.AdventureMapDeveloper;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class StandardConfig {
    /**
     * The default config folder
     */
    public static final Path core_config_folder = Path.of("config");

    /**
     * Writes a normal properties config
     * @param properties The properties writer
     * @param file The file where it's supposed to be written to
     * @param comment A comment
     */
    public static void write(Properties properties, String file, String comment) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, comment);
        } catch (FileNotFoundException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "Could not find the file: " + file + "!");
            e.printStackTrace();
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing to the file " + file + "!");
            e.printStackTrace();
        }
    }

    /**
     * Writing a string option to a file using a normal properties writer
     * @param properties The properties writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the option
     */
    public static void writeStringProperties(Properties properties, String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Writing an integer option to a file using a normal properties writer
     * @param properties The properties writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the option
     */
    public static void writeIntProperties(Properties properties, String key, Integer value) {
        properties.setProperty(key, String.valueOf(value));
    }

    /**
     * Writing a boolean option to a file using a normal properties writer
     * @param properties The properties writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the option
     */
    public static void writeBooleanProperties(Properties properties, String key, Boolean value) {
        properties.setProperty(key, String.valueOf(value));
    }

    /**
     * Writes a string option to a file using a custom properties writer
     * @param writer The custom writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the option
     * @param comment A comment written in front of the option. Set to null if no comment is supposed to be written
     */
    public static void writeStringProperties(BufferedWriter writer, String key, String value, @Nullable String comment) {
        try {
            if (comment != null) {
                writer.write("#" + comment + "\n");
            }
            writer.write(key + "=" + value + "\n");
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing the key " + key + " with the value " + value + " and the comment " + comment + "!");
            e.printStackTrace();
        }
    }

    /**
     * Writes an integer option to a file using a custom properties writer
     * @param writer The custom writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the option
     * @param comment A comment written in front of the option. Set to null if no comment is supposed to be written
     */
    public static void writeIntProperties(BufferedWriter writer, String key, Integer value, @Nullable String comment) {
        try {
            if (comment != null) {
                writer.write("#" + comment + "\n");
            }
            writer.write(key + "=" + value  + "\n");
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing the key " + key + " with the value " + value + " and the comment " + comment + "!");
            e.printStackTrace();
        }
    }

    /**
     * Writes a boolean option to a file using a custom properties writer
     * @param writer The custom writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the option
     * @param comment A comment written in front of the option. Null if no comment is supposed to be written
     */
    public static void writeBooleanProperties(BufferedWriter writer, String key, Boolean value, @Nullable String comment) {
        try {
            if (comment != null) {
                writer.write("#" + comment + "\n");
            }
            writer.write(key + "=" + value + "\n");
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing the key " + key + " with the value " + value + " and the comment " + comment + "!");
            e.printStackTrace();
        }
    }

    /**
     * Writes an enum option to a file using a custom properties writer
     * @param writer The custom writer
     * @param key The key the option is supposed to have aka. its name
     * @param value The current value of the enum that you want to write
     * @param comment A comment written in front of the option. Null if no comment is supposed to be written
     */
    public static void writeEnumProperties(BufferedWriter writer, String key, String value, @Nullable String comment) {
        try {
            if (comment != null) {
                writer.write("#" + comment + "\n");
            }
            writer.write(key + "=" + value + "\n");
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing the key " + key + " with the value " + value + " and the comment " + comment + "!");
            e.printStackTrace();
        }
    }

    /**
     * Reads a string value from a config
     * @param file The file in which the value is saved
     * @param key The key(name) of the option
     * @return The saved value, if it exists
     */
    public static Optional<String> readStringValue(String file, String key) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            return Optional.ofNullable(properties.getProperty(key));
        } catch (FileNotFoundException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "Could not find the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while reading the key " + key + " on the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Reads an integer value from a config
     * @param file The file in which the value is saved
     * @param key The key(name) of the option
     * @return The saved value, null if it does not exist
     */
    public static Optional<Integer> readIntValue(String file, String key) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            return Optional.ofNullable(properties.getProperty(key)).map(Integer::valueOf);
        } catch (FileNotFoundException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "Could not find the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }  catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while reading the key " + key + " on the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Reads an integer value from a config, but it's limited by a maximum value
     * @param file The file in which the value is saved
     * @param key The key(name) of the option
     * @param max The maximum value of the option
     * @return The saved value, if it exists, the maximum value if it's larger than the maximum value
     */
    public static Optional<Integer> readIntValueWithMax(String file, String key, int max) {
        Optional<Integer> value = readIntValue(file, key);
        if (value.isPresent() && value.get()>max) {
            AdventureMapDeveloper.LOGGER.warn(AdventureMapDeveloper.LOGGER_PREFIX + "Invalid integer value " + value + " for the key " + key + "!" + "It can't be larger than " + max + "!");
            return Optional.of(max);
        }
        return value;
    }

    /**
     * Reads an integer value from a config, but it's limited by a minimum value
     * @param file The file in which the value is saved
     * @param key The key(name) of the option
     * @param min The maximum value of the option
     * @return The saved value, if it exists, the minimum value if it's smaller than the minimum value
     */
    public static Optional<Integer> readIntValueWithMin(String file, String key, int min) {
        Optional<Integer> value = readIntValue(file, key);
        if (value.isPresent() && value.get()<min) {
            AdventureMapDeveloper.LOGGER.warn(AdventureMapDeveloper.LOGGER_PREFIX + "Invalid integer value " + value + " for the key " + key + "!" + "It can't be smaller than " + min + "!");
            return Optional.of(min);
        }
        return value;
    }

    /**
     * Reads an integer value from a config, but it's limited by a minimum and maximum value
     * @param file The file in which the value is saved
     * @param key The key(name) of the option
     * @param min The maximum value of the option
     * @return The saved value, if it exists, the minimum value if it's smaller than the minimum value, the maximum value if it's larger than the maximum value
     */
    public static Optional<Integer> readIntValue(String file, String key, int min, int max) {
        Optional<Integer> value = readIntValue(file, key);
        if (value.isPresent()) {
            if (value.get() > max) {
                AdventureMapDeveloper.LOGGER.warn(AdventureMapDeveloper.LOGGER_PREFIX + "Invalid integer value " + value + " for the key " + key + "!" + "It can't be larger than " + max + "!");
                return Optional.of(max);
            }
            if (value.get() < min) {
                AdventureMapDeveloper.LOGGER.warn(AdventureMapDeveloper.LOGGER_PREFIX + "Invalid integer value " + value + " for the key " + key + "!" + "It can't be smaller than " + min + "!");
                return Optional.of(min);
            }
        }
        return value;
    }

    /**
     * Reads a boolean value from a config
     * @param file The file where the value is saved
     * @param key The key(name) of the option
     * @return The saved boolean value, if it exists
     */
    public static Optional<Boolean> readBooleanValue(String file, String key) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            return Optional.ofNullable(properties.getProperty(key)).map(Boolean::valueOf);
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while reading the key " + key + " on the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Reads an enum value that has previously been saved to a config
     * @param file The file where the value is saved
     * @param key The key(name) of the option
     * @param possibleValues All possible options of the enum
     * @return The value of the enum, if valid
     */
    public static Optional<String> readEnumValue(String file, String key, String[] possibleValues) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            String s = properties.getProperty(key);
            for (String t : possibleValues) {
                if (t.equals(s)) {
                    return Optional.of(t);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while reading the key " + key + " on the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Checks whether a key exists in a file
     * @param file The file that gets searched for the key
     * @param key The key
     * @return true if the key does not exist, otherwise false
     */
    public static boolean doesNotExist(String file, String key) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key) == null;
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while checking for the key " + key + " on the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Tests if all keys exist
     * @param file The file that gets searched for the keys
     * @param keys A list of all keys
     * @return true if all keys exist, otherwise false
     */
    public static boolean existLoop(String file, List<String> keys) {
        boolean not_exited = true;
        for (String key : keys) {
            if (StandardConfig.doesNotExist(file, key)) {
                not_exited = false;
            }
        }
        return not_exited;
    }

    /**
     * Tests whether the config is of a specific version
     * @param string The version of the config
     * @param file The config that is supposed to be checked for the version
     * @return true if the config is for the specific version, otherwise false
     */
    public static boolean testForVersion(String string, String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            reader.close();
            return line1.contains(string) || line2.contains(string);
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while checking for the version " + string + " on the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Creates a file writer for a file
     * @param file That the file writer is supposed to write in
     * @return The file writer
     */
    public static FileWriter fileWriter(String file) {
        try {
            return new FileWriter(file);
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while creating the file writer for the file " + file + "!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Closes the passed in writer
     * @param writer The writer
     */
    public static void close(BufferedWriter writer) {
        try {
            writer.close();
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while closing the file writer " + writer + "!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes a comment
     * @param writer The writer for the file
     * @param comment The comment that is supposed to be written
     * @param emptyLine Whether an empty line is supposed to be added in front of the comment
     */
    public static void writeComment(BufferedWriter writer, String comment, boolean emptyLine) {
        try {
            if (emptyLine) {
                writer.write("\n");
            }
            writer.write("#" + comment + "\n");
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing the comment " + comment + "!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Call this before writing a config so that the folder exists and is prepared
     */
    public static void prepareConfig() {
        FabricLoader.getInstance().getConfigDir();
        if (!Files.exists(core_config_folder)) {
            try {
                Files.createDirectories(core_config_folder);
            } catch (IOException e) {
                AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while creating the config directory");
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    /**
     * Gets the next value in a settings enum
     * @param current The current value of the enum
     * @param e Code executed to get the next value
     * @return The next enum option
     */
    public static String getNextEnumValue(String current, Enum e) {
        return e.getNextValue(current);
    }

    public interface Enum {
        String getNextValue(String current);
    }
}
