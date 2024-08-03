package eu.caoten.adventure_map_developer.config;

import eu.caoten.adventure_map_developer.config.api.ClientConfigBuilder;

public class ClientConfig {
    public static final String NAME = "adventure_map_developer";
    public static final String SHOW_INVISIBLE_BLOCKS = "ShowInvisibleBlocks";
    public static final String SHOW_INVISIBLE_ENTITIES = "ShowInvisibleEntities";
    public static final String GIVE_FEEDBACK = "GiveFeedback";
    public static final String GIVE_SOUND_FEEDBACK = "GiveSoundFeedback";
    public static final String KEYBIND_1 = "Keybinding1";
    public static final String KEYBIND_2 = "Keybinding2";
    public static final String KEYBIND_3 = "Keybinding3";
    public static final String KEYBIND_4 = "Keybinding4";
    public static final String KEYBIND_5 = "Keybinding5";
    public static final String KEYBIND_6 = "Keybinding6";
    public static final String KEYBIND_7 = "Keybinding7";
    public static final String KEYBIND_8 = "Keybinding8";
    public static final String KEYBIND_9 = "Keybinding9";
    public static final String KEYBIND_10 = "Keybinding10";
    public static final String KEYBIND_1_ENABLED = "Keybinding1Enabled";
    public static final String KEYBIND_2_ENABLED = "Keybinding2Enabled";
    public static final String KEYBIND_3_ENABLED = "Keybinding3Enabled";
    public static final String KEYBIND_4_ENABLED = "Keybinding4Enabled";
    public static final String KEYBIND_5_ENABLED = "Keybinding5Enabled";
    public static final String KEYBIND_6_ENABLED = "Keybinding6Enabled";
    public static final String KEYBIND_7_ENABLED = "Keybinding7Enabled";
    public static final String KEYBIND_8_ENABLED = "Keybinding8Enabled";
    public static final String KEYBIND_9_ENABLED = "Keybinding9Enabled";
    public static final String KEYBIND_10_ENABLED = "Keybinding10Enabled";

    public static void register() {
        new ClientConfigBuilder(NAME,
                new ClientConfigBuilder.Comment("comment1", "This is the config for the Adventure Map Developer mod"),
                new ClientConfigBuilder.BooleanOption(SHOW_INVISIBLE_BLOCKS, false),
                new ClientConfigBuilder.BooleanOption(SHOW_INVISIBLE_ENTITIES, false),
                new ClientConfigBuilder.BooleanOption(GIVE_FEEDBACK, true),
                new ClientConfigBuilder.BooleanOption(GIVE_SOUND_FEEDBACK, true),
                new ClientConfigBuilder.Comment("comment2", "These are the commands executed when pressing the associated keybinding"),
                new ClientConfigBuilder.StringOption(KEYBIND_1, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_1_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_2, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_2_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_3, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_3_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_4, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_4_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_5, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_5_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_6, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_6_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_7, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_7_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_8, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_8_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_9, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_9_ENABLED, true),
                new ClientConfigBuilder.StringOption(KEYBIND_10, ""),
                new ClientConfigBuilder.BooleanOption(KEYBIND_10_ENABLED, true)
        ).build();
    }
}
