package dev.dubhe.forgemalilib.gui.button;

import dev.dubhe.forgemalilib.config.IConfigBoolean;
import dev.dubhe.forgemalilib.gui.GuiBase;

public class ConfigButtonBoolean extends ButtonGeneric
{
    private final IConfigBoolean config;

    public ConfigButtonBoolean(int x, int y, int width, int height, IConfigBoolean config)
    {
        super(x, y, width, height, "");
        this.config = config;

        this.updateDisplayString();
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton)
    {
        this.config.toggleBooleanValue();
        this.updateDisplayString();

        return super.onMouseClickedImpl(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateDisplayString()
    {
        String valueStr = String.valueOf(this.config.getBooleanValue());

        if (this.config.getBooleanValue())
        {
            this.displayString = GuiBase.TXT_DARK_GREEN + valueStr + GuiBase.TXT_RST;
        }
        else
        {
            this.displayString = GuiBase.TXT_DARK_RED + valueStr + GuiBase.TXT_RST;
        }
    }
}
