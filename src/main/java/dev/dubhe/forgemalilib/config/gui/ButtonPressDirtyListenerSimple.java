package dev.dubhe.forgemalilib.config.gui;

import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.button.IButtonActionListener;

public class ButtonPressDirtyListenerSimple implements IButtonActionListener
{
    private boolean dirty;

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton)
    {
        this.dirty = true;
    }

    public boolean isDirty()
    {
        return this.dirty;
    }

    public void resetDirty()
    {
        this.dirty = false;
    }
}
