package dev.dubhe.forgemalilib.config.gui;

import dev.dubhe.forgemalilib.config.IStringRepresentable;
import dev.dubhe.forgemalilib.gui.GuiTextFieldGeneric;
import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.interfaces.ITextFieldListener;

public class ConfigOptionChangeListenerTextField implements ITextFieldListener<GuiTextFieldGeneric>
{
    protected final IStringRepresentable config;
    protected final GuiTextFieldGeneric textField;
    protected final ButtonBase buttonReset;

    public ConfigOptionChangeListenerTextField(IStringRepresentable config, GuiTextFieldGeneric textField, ButtonBase buttonReset)
    {
        this.config = config;
        this.textField = textField;
        this.buttonReset = buttonReset;
    }

    @Override
    public boolean onTextChange(GuiTextFieldGeneric textField)
    {
        this.buttonReset.setEnabled(this.config.isModified(this.textField.getValue()));
        return false;
    }
}
