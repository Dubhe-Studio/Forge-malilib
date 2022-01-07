package dev.dubhe.forgemalilib.config.gui;

import javax.annotation.Nullable;
import dev.dubhe.forgemalilib.config.IConfigResettable;
import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.button.IButtonActionListener;

public class ConfigOptionChangeListenerButton implements IButtonActionListener
{
    private final IConfigResettable config;
    private final ButtonBase buttonReset;
    @Nullable
    private final ButtonPressDirtyListenerSimple dirtyListener;

    public ConfigOptionChangeListenerButton(IConfigResettable config, ButtonBase buttonReset, @Nullable ButtonPressDirtyListenerSimple dirtyListener)
    {
        this.config = config;
        this.dirtyListener = dirtyListener;
        this.buttonReset = buttonReset;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton)
    {
        this.buttonReset.setEnabled(this.config.isModified());

        if (this.dirtyListener != null)
        {
            // Call the dirty listener to know if the configs should be saved when the GUI is closed
            this.dirtyListener.actionPerformedWithButton(button, mouseButton);
        }
    }
}
