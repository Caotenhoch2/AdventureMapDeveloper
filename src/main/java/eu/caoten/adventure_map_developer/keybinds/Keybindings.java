package eu.caoten.adventure_map_developer.keybinds;

import eu.caoten.adventure_map_developer.config.ClientConfig;
import eu.caoten.adventure_map_developer.config.api.ClientConfigSystem;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class Keybindings {
    private static KeyBinding showInvisibleBlocks;
    private static KeyBinding showInvisibleEntities;
    private static KeyBinding keyBinding1;
    private static KeyBinding keyBinding2;
    private static KeyBinding keyBinding3;
    private static KeyBinding keyBinding4;
    private static KeyBinding keyBinding5;
    private static KeyBinding keyBinding6;
    private static KeyBinding keyBinding7;
    private static KeyBinding keyBinding8;
    private static KeyBinding keyBinding9;
    private static KeyBinding keyBinding10;
    public static final String CATEGORY = "category.adventure_map_developer.keybindings";

    public static void register() {
        showInvisibleBlocks = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.showInvisibleBlocks",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        showInvisibleEntities = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.showInvisibleEntities",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.1",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.2",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.3",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding4 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.4",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding5 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.5",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding6 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.6",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding7 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.7",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding8 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.8",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding9 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.9",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
        keyBinding10 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.adventure_map_developer.10",
                InputUtil.Type.KEYSYM,
                -1,
                CATEGORY
        ));
    }

    public static void onPress(MinecraftClient client) {
        while (showInvisibleBlocks.wasPressed()) {
            MinecraftClient.getInstance().worldRenderer.reload();
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS);
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
            buttonSound();
            ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_FEEDBACK).ifPresent(aBoolean -> {
                if (aBoolean) {
                    boolean status = ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_BLOCKS).get();
                    MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.literal("[AMD] ").append(Text.translatable("message.adventure_map_developer.blocks", status ? Text.translatable("message.adventure_map_developer.shown") : Text.translatable("message.adventure_map_developer.hidden"))), false);
                }
            });
        }
        while (showInvisibleEntities.wasPressed()) {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES);
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
            buttonSound();
            ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_FEEDBACK).ifPresent(aBoolean -> {
                if (aBoolean) {
                    boolean status = ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.SHOW_INVISIBLE_ENTITIES).get();
                    MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.literal("[AMD] ").append(Text.translatable("message.adventure_map_developer.entities", status ? Text.translatable("message.adventure_map_developer.shown") : Text.translatable("message.adventure_map_developer.hidden"))), false);
                }
            });
        }
        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_1_ENABLED).get()) {
            while (keyBinding1.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_1).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_2_ENABLED).get()) {
            while (keyBinding2.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_2).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_3_ENABLED).get()) {
            while (keyBinding3.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_3).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_4_ENABLED).get()) {
            while (keyBinding4.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_4).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_5_ENABLED).get()) {
            while (keyBinding5.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_5).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_6_ENABLED).get()) {
            while (keyBinding6.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_6).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_7_ENABLED).get()) {
            while (keyBinding7.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_7).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_8_ENABLED).get()) {
            while (keyBinding8.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_8).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_9_ENABLED).get()) {
            while (keyBinding9.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_9).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
        if (ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_10_ENABLED).get()) {
            while (keyBinding10.wasPressed()) {
                String command = ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_10).get();
                if (!command.isEmpty()) {
                    networkHandler.sendCommand(command);
                }
            }
        }
    }

    public static void buttonSound() {
        ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.GIVE_SOUND_FEEDBACK).ifPresent(aBoolean -> {
            if (aBoolean) {
                MinecraftClient.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
            }
        });
    }
}
