package eu.caoten.adventure_map_developer.config;


import eu.caoten.adventure_map_developer.buttons.Buttons;
import eu.caoten.adventure_map_developer.config.api.ClientConfigSystem;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import eu.caoten.adventure_map_developer.screen.SettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ConfigScreen extends SettingsScreen {
    public ConfigScreen(Screen parent) {
        super("gui.adventure_map_developer.config", parent);
    }

    @Override
    public int buttons(int i, int k) {
        this.addDrawableChild(Buttons.toggleButtonWidget(i, k, false, "gui.adventure_map_developer.showInvisibleBlocks", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS).get(), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS);
            MinecraftClient.getInstance().worldRenderer.reload();
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.showInvisibleBlocks", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }));
        this.addDrawableChild(Buttons.toggleButtonWidget(i, k, true, "gui.adventure_map_developer.showInvisibleEntities", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get(), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.showInvisibleEntities", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }));
        k+=25;
        this.addDrawableChild(Buttons.toggleButtonWidget(i, k, false, "gui.adventure_map_developer.sendFeedback", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_FEEDBACK).get(), "gui.adventure_map_developer.sendFeedback.tooltip", button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_FEEDBACK);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.sendFeedback", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_FEEDBACK).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }));
        this.addDrawableChild(Buttons.toggleButtonWidget(i, k, true, "gui.adventure_map_developer.sendSoundFeedback", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_SOUND_FEEDBACK).get(), "gui.adventure_map_developer.sendSoundFeedback.tooltip", button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_SOUND_FEEDBACK);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.sendSoundFeedback", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_SOUND_FEEDBACK).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }));
        k+=25;
        this.addDrawableChild(Buttons.normalButtonWidget(i, k, false, "gui.adventure_map_developer.keybinding", button -> {
            this.client.setScreen(new KeyBindingsScreen(this));
        }));
        return k;
    }

}
