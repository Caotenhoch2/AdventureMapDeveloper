package eu.caoten.adventure_map_developer.config;

import eu.caoten.adventure_map_developer.AdventureMapDeveloper;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Websites {
    public static final String FILE_PATH = FabricLoader.getInstance().getConfigDir() + "/AMD-Links.properties";
    public static final File FILE = new File(FILE_PATH);

    public static List<String> getWebsites() {
        List<String> strings = new ArrayList<>();
        if (FILE.exists() && FILE.isFile()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FILE));
                reader.lines().forEach(string -> {
                    if (!string.startsWith("#")) {
                        if (string.contains("://")) {
                            strings.add(string);
                        } else {
                            strings.add("https://" + string);
                        }
                    }
                });
                reader.close();
            } catch (IOException e) {
                AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while reading the websites file ({}): {}", FILE_PATH, e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            createNewFile();
        }
        return strings;
    }

    public static void createNewFile() {
        if (FILE.exists() && FILE.isFile()) {
            return;
        }
        try {
            FileWriter writer = new FileWriter(FILE);
            writer.write("#Just add the websites you want to open with the button in here\n");
            writer.write("#Each website should get its one line\n");
            writer.write("#Lines starting in '#' are comments and will be ignored\n");
            writer.close();
        } catch (IOException e) {
            AdventureMapDeveloper.LOGGER.error(AdventureMapDeveloper.LOGGER_PREFIX + "An error occurred while writing the websites file ({}): {}", FILE_PATH, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
