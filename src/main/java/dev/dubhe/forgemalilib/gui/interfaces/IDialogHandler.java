package dev.dubhe.forgemalilib.gui.interfaces;

import dev.dubhe.forgemalilib.gui.GuiBase;

public interface IDialogHandler
{
    /**
     * Open the provided GUI as a "dialog window"
     * @param gui
     */
    void openDialog(GuiBase gui);

    /**
     * Close the previously opened "dialog window"
     */
    void closeDialog();
}
