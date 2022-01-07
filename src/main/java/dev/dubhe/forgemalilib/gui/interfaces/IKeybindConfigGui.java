package dev.dubhe.forgemalilib.gui.interfaces;

import javax.annotation.Nullable;
import dev.dubhe.forgemalilib.config.gui.ConfigOptionChangeListenerKeybind;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonKeybind;

public interface IKeybindConfigGui extends IConfigGui
{
    /**
     * Adds a hotkey keybind change listener. These will be used for updating the used
     * keys in the keybind handler after the hotkeys were changed, and also
     * for updating the hotkey config buttons in the GUI.
     * @param listener
     */
    void addKeybindChangeListener(ConfigOptionChangeListenerKeybind listener);

    /**
     * Sets or clears the currently active/selected hotkey config button.
     * The active button reference is used for capturing the new hotkey combination,
     * and for updating the button appearance when gaining or losing the focus.
     * @param button
     */
    void setActiveKeybindButton(@Nullable ConfigButtonKeybind button);
}
