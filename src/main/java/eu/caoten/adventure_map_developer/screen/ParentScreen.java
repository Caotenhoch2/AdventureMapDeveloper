package eu.caoten.adventure_map_developer.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class ParentScreen extends Screen {
    protected final Screen parent;

    /**
     * Creates a simple screen with a parent screen
     * @param translateKey The translate key for the name of the screen
     * @param parent The screen from which this is opened aka the parent
     */
    public ParentScreen(String translateKey, Screen parent) {
        this(Text.translatable(translateKey), parent);
    }

    /**
     * Creates a simple screen with a parent screen
     * @param title The name of the screen
     * @param parent The screen from which this is opened aka the parent
     */
    public ParentScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
