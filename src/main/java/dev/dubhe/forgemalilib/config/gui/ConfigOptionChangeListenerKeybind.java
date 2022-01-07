package dev.dubhe.forgemalilib.config.gui;

import dev.dubhe.forgemalilib.gui.interfaces.IKeybindConfigGui;
import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.button.ButtonGeneric;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonKeybind;
import dev.dubhe.forgemalilib.gui.button.IButtonActionListener;
import dev.dubhe.forgemalilib.hotkeys.IKeybind;

public class ConfigOptionChangeListenerKeybind implements IButtonActionListener
{
    private final IKeybindConfigGui host;
    private final ConfigButtonKeybind buttonHotkey;
    private final ButtonGeneric button;
    private final IKeybind keybind;

    public ConfigOptionChangeListenerKeybind(IKeybind keybind, ConfigButtonKeybind buttonHotkey, ButtonGeneric button, IKeybindConfigGui host)
    {
        this.buttonHotkey = buttonHotkey;
        this.button = button;
        this.keybind = keybind;
        this.host = host;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton)
    {
        this.keybind.resetToDefault();
        this.updateButtons();
        this.host.getButtonPressListener().actionPerformedWithButton(button, mouseButton);
    }

    public void updateButtons()
    {
        this.button.setEnabled(this.keybind.isModified());
        this.buttonHotkey.updateDisplayString();
    }
}
