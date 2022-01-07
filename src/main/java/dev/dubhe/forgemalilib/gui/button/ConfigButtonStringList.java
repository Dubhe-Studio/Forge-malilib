package dev.dubhe.forgemalilib.gui.button;

import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.gui.interfaces.IConfigGui;
import dev.dubhe.forgemalilib.gui.interfaces.IDialogHandler;
import dev.dubhe.forgemalilib.config.IConfigStringList;
import dev.dubhe.forgemalilib.gui.GuiBase;
import dev.dubhe.forgemalilib.gui.GuiStringListEdit;
import dev.dubhe.forgemalilib.util.GuiUtils;
import dev.dubhe.forgemalilib.util.StringUtils;

public class ConfigButtonStringList extends ButtonGeneric
{
    private final IConfigStringList config;
    private final IConfigGui configGui;
    @Nullable private final IDialogHandler dialogHandler;

    public ConfigButtonStringList(int x, int y, int width, int height, IConfigStringList config, IConfigGui configGui, @Nullable IDialogHandler dialogHandler)
    {
        super(x, y, width, height, "");

        this.config = config;
        this.configGui = configGui;
        this.dialogHandler = dialogHandler;

        this.updateDisplayString();
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton)
    {
        super.onMouseClickedImpl(mouseX, mouseY, mouseButton);

        if (this.dialogHandler != null)
        {
            this.dialogHandler.openDialog(new GuiStringListEdit(this.config, this.configGui, this.dialogHandler, null));
        }
        else
        {
            GuiBase.openGui(new GuiStringListEdit(this.config, this.configGui, null, GuiUtils.getCurrentScreen()));
        }

        return true;
    }

    @Override
    public void updateDisplayString()
    {
        this.displayString = StringUtils.getClampedDisplayStringRenderlen(this.config.getStrings(), this.width - 10, "[ ", " ]");
    }
}
