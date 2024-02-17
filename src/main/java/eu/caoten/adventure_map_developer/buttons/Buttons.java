package eu.caoten.adventure_map_developer.buttons;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class Buttons {

    /**
     * Creates a simple normal option button
     * @param i The x position of the button
     * @param k The y position of the button
     * @param isInSecondRow Whether the button is in the second row. If so, add 160 to the x position
     * @param translateKey The translate key of the button
     * @param pressAction What the button is supposed to do when clicked
     * @return Your button
     */
    public static ButtonWidget normalButtonWidget(int i, int k, boolean isInSecondRow, String translateKey, ButtonWidget.PressAction pressAction) {
        if (isInSecondRow) {
            i += 160;
        }
        return ButtonWidget.builder(Text.translatable(translateKey), pressAction).dimensions(i,k,150,20).build();
    }

    /**
     * Creates a simple toggle option button
     * @param i The x position of the button
     * @param k The y position of the button
     * @param isInSecondRow Whether the button is in the second row. If so, add 160 to the x position
     * @param translateKey The translate key of the button with one variable
     * @param pressAction What the button is supposed to do when clicked. Remember to change the button text and the value in here
     * @param booleanVariable The value the button is supposed to have
     * @return Your button
     */
    public static ButtonWidget toggleButtonWidget(int i, int k, boolean isInSecondRow, String translateKey, boolean booleanVariable, ButtonWidget.PressAction pressAction) {
        if (isInSecondRow) {
            i += 160;
        }
        return ButtonWidget.builder(toggle(translateKey, booleanVariable), pressAction).dimensions(i,k,150,20).build();
    }



    /**
     * Creates a simple toggle option button
     * @param i The x position of the button
     * @param k The y position of the button
     * @param isInSecondRow Whether the button is in the second row. If so, add 160 to the x position
     * @param translateKey The translate key of the button with one variable
     * @param pressAction What the button is supposed to do when clicked. Remember to change the button text and the value in here
     * @param booleanVariable The value the button is supposed to have
     * @param tooltip The tooltip of the button
     * @return Your button
     */
    public static ButtonWidget toggleButtonWidget(int i, int k, boolean isInSecondRow, String translateKey, boolean booleanVariable, String tooltip, ButtonWidget.PressAction pressAction) {
        if (isInSecondRow) {
            i += 160;
        }
        return ButtonWidget.builder(toggle(translateKey, booleanVariable), pressAction).dimensions(i,k,150,20).tooltip(Tooltip.of(Text.translatable(tooltip))).build();
    }

    /**
     * Toggles the text for a toggle button
     * @param translateKey The translate key
     * @param aBoolean The current value
     * @return The text with the correct value added
     */
    public static Text toggle(String translateKey, boolean aBoolean) {
        return Text.translatable(translateKey, aBoolean ? Text.translatable("gui.adventure_map_developer.on") : Text.translatable("gui.adventure_map_developer.off"));
    }
}
