package eu.caoten.adventure_map_developer.config;

import eu.caoten.adventure_map_developer.buttons.Buttons;
import eu.caoten.adventure_map_developer.config.api.ClientConfigSystem;
import eu.caoten.adventure_map_developer.config.api.ClientConfigValues;
import eu.caoten.adventure_map_developer.screen.SettingsScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class KeyBindingsScreen extends SettingsScreen {
    private TextFieldWidget key1;
    private TextFieldWidget key2;
    private TextFieldWidget key3;
    private TextFieldWidget key4;
    private TextFieldWidget key5;
    private TextFieldWidget key6;
    private TextFieldWidget key7;
    private TextFieldWidget key8;
    private TextFieldWidget key9;
    private TextFieldWidget key10;

    public KeyBindingsScreen(Screen parent) {
        super("gui.adventure_map_developer.keybinding", parent);
    }

    @Override
    public int buttons(int i, int k) {
        int j = i + 55;
        int l = j + 210;
        k+=10;
        this.key1 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 1"));
        this.key1.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_1).get());
        this.key1.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_1, this.key1.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key1);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_1_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_1_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_1_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key2 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 2"));
        this.key2.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_2).get());
        this.key2.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_2, this.key2.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key2);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_2_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_2_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_2_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key3 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 3"));
        this.key3.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_3).get());
        this.key3.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_3, this.key3.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key3);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_3_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_3_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_3_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key4 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 4"));
        this.key4.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_4).get());
        this.key4.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_4, this.key4.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key4);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_4_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_4_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_4_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key5 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 5"));
        this.key5.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_5).get());
        this.key5.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_5, this.key5.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key5);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_5_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_5_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_5_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key6 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 6"));
        this.key6.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_6).get());
        this.key6.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_6, this.key6.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key6);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_6_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_6_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_6_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key7 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 7"));
        this.key7.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_7).get());
        this.key7.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_7, this.key7.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key7);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_7_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_7_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_7_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key8 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 8"));
        this.key8.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_8).get());
        this.key8.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_8, this.key8.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key8);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_8_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_8_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_8_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key9 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 9"));
        this.key9.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_9).get());
        this.key9.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_9, this.key9.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key9);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_9_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_9_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_9_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        k+=35;
        this.key10 = new TextFieldWidget(this.textRenderer, j, k, 200, 20, Text.literal("Keybinding 01"));
        this.key10.setText(ClientConfigValues.getStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_10).get());
        this.key10.setChangedListener(string -> {
            ClientConfigValues.writeStringOption(ClientConfig.NAME, ClientConfig.KEYBIND_10, this.key10.getText());
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        });
        this.addDrawableChild(this.key10);
        this.addDrawableChild(ButtonWidget.builder(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_10_ENABLED).get()), button -> {
            ClientConfigValues.toggleBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_10_ENABLED);
            button.setMessage(Buttons.toggle("gui.adventure_map_developer.%s", ClientConfigValues.getBooleanOption(ClientConfig.NAME, ClientConfig.KEYBIND_10_ENABLED).get()));
            ClientConfigSystem.writeConfig(ClientConfig.NAME);
        }).dimensions(l, k, 50, 20).build());
        return k;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int j = this.width / 2 - 100;
        int k = this.height / 6 - 12;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.1"), j, k, 0xFFFFFF, true);
        context.drawText(this.textRenderer, Text.translatable("gui.adventure_map_developer.enabled"), j + 211, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.2"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.3"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.4"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.5"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.6"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.7"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.8"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.9"), j, k, 0xFFFFFF, true);
        k+=35;
        context.drawText(this.textRenderer, Text.translatable("keybinding.adventure_map_developer.10"), j, k, 0xFFFFFF, true);
        super.render(context, mouseX, mouseY, delta);
    }

    /*
    @Override
    public void tick() {
        this.key1.tick();
    }*/
}
