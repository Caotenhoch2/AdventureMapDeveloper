package eu.caoten.adventure_map_developer.config;

import eu.caoten.adventure_map_developer.screen.ParentScreen;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class InformationScreen extends ParentScreen {

    private MultilineText messageSplit = MultilineText.EMPTY;
    private final Text message = Text.translatable("gui.adventure_map_developer.noWebsites");

    public InformationScreen(Screen parent) {
        super("gui.adventure_map_developer.information", parent);
    }

    @Override
    protected void init() {
        super.init();
        this.messageSplit = MultilineText.create(this.textRenderer, this.message, this.width - 50);
        int i = MathHelper.clamp(this.getMessageY() + this.getMessagesHeight() + 20, this.height / 6 + 96, this.height - 24);
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.adventure_map_developer.openFile"), button -> {
            Util.getOperatingSystem().open(Websites.FILE);
            this.close();
        }).dimensions(this.width / 2 - 100, i, 200, 20).build());
        i+=25;
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.adventure_map_developer.back"), button -> this.close()).dimensions(this.width / 2 - 100, i, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.getTitleY(), 16777215);
        this.messageSplit.drawCenterWithShadow(context, this.width / 2, this.getMessageY());
    }

    private int getTitleY() {
        int i = (this.height - this.getMessagesHeight()) / 2;
        int j = i - 20;
        Objects.requireNonNull(this.textRenderer);
        return MathHelper.clamp(j - 9, 10, 80);
    }

    private int getMessageY() {
        return this.getTitleY() + 20;
    }

    private int getMessagesHeight() {
        int height = this.messageSplit.count();
        Objects.requireNonNull(this.textRenderer);
        return height * 9;
    }
}
