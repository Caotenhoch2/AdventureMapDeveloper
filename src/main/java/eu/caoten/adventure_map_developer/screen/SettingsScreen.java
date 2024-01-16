package eu.caoten.adventure_map_developer.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

/**
 * A simple implementation for an options screen. Ideally used with the "Buttons" buttons and a "StandardConfig"
 */
public abstract class SettingsScreen extends ParentScreen {

    /**
     * Creates a simple options screen
     * @param titleKey The translate key for the name of the screen
     * @param parent The screen from which this is opened
     */
    public SettingsScreen(String titleKey, Screen parent) {
        super(titleKey, parent);
    }

    /**
     * Creates a simple options screen
     * @param title The name of the screen
     * @param parent The screen from which this is opened
     */
    public SettingsScreen(Text title, Screen parent) {
        super(title, parent);
    }

    @Override
    public void init() {
        int i = this.width / 2 - 155;
        int k = this.height / 6 - 12;
        k = buttons(i, k);
        k += 25;
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).dimensions(this.width / 2 - 100, k, 200, 20).build());
    }

    /**
     * Add your own buttons in this methode
     * @param i The x position of the buttons, positioned ideally for 2 columns of buttons
     * @param k The y position of the buttons, positioned a bit under the top
     * @return The k value after all buttons are added
     */
    public abstract int buttons(int i, int k);

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
