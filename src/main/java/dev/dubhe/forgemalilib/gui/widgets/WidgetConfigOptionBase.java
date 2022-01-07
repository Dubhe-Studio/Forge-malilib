package dev.dubhe.forgemalilib.gui.widgets;

import javax.annotation.Nullable;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.forgemalilib.config.gui.ConfigOptionChangeListenerTextField;
import dev.dubhe.forgemalilib.gui.wrappers.TextFieldWrapper;
import dev.dubhe.forgemalilib.config.IConfigResettable;
import dev.dubhe.forgemalilib.gui.GuiTextFieldGeneric;
import dev.dubhe.forgemalilib.gui.button.ButtonGeneric;
import dev.dubhe.forgemalilib.util.KeyCodes;
import dev.dubhe.forgemalilib.util.StringUtils;

public abstract class WidgetConfigOptionBase<TYPE> extends WidgetListEntryBase<TYPE>
{
    protected final WidgetListConfigOptionsBase<?, ?> parent;
    @Nullable protected TextFieldWrapper<? extends GuiTextFieldGeneric> textField = null;
    @Nullable protected String initialStringValue;
    protected int maxTextfieldTextLength = 65535;
    /**
     * The last applied value for any textfield-based configs.
     * Button based (boolean, option-list) values get applied immediately upon clicking the button.
     */
    protected String lastAppliedValue;

    public WidgetConfigOptionBase(int x, int y, int width, int height,
            WidgetListConfigOptionsBase<?, ?> parent, TYPE entry, int listIndex)
    {
        super(x, y, width, height, entry, listIndex);

        this.parent = parent;
    }

    public abstract boolean wasConfigModified();

    public boolean hasPendingModifications()
    {
        if (this.textField != null)
        {
            return this.textField.getTextField().getValue().equals(this.lastAppliedValue) == false;
        }

        return false;
    }

    public abstract void applyNewValueToConfig();

    protected GuiTextFieldGeneric createTextField(int x, int y, int width, int height)
    {
        return new GuiTextFieldGeneric(x + 2, y, width, height, this.textRenderer);
    }

    protected void addTextField(GuiTextFieldGeneric field, ConfigOptionChangeListenerTextField listener)
    {
        TextFieldWrapper<? extends GuiTextFieldGeneric> wrapper = new TextFieldWrapper<>(field, listener);
        this.textField = wrapper;
        this.parent.addTextField(wrapper);
    }

    protected ButtonGeneric createResetButton(int x, int y, IConfigResettable config)
    {
        String labelReset = StringUtils.translate("forgemalilib.gui.button.reset.caps");
        ButtonGeneric resetButton = new ButtonGeneric(x, y, -1, 20, labelReset);
        resetButton.setEnabled(config.isModified());

        return resetButton;
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton)
    {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton))
        {
            return true;
        }

        boolean ret = false;

        if (this.textField != null)
        {
            ret |= this.textField.getTextField().mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (this.subWidgets.isEmpty() == false)
        {
            for (WidgetBase widget : this.subWidgets)
            {
                ret |= widget.isMouseOver(mouseX, mouseY) && widget.onMouseClicked(mouseX, mouseY, mouseButton);
            }
        }

        return ret;
    }

    @Override
    public boolean onKeyTypedImpl(int keyCode, int scanCode, int modifiers)
    {
        if (this.textField != null && this.textField.isFocused())
        {
            if (keyCode == KeyCodes.KEY_ENTER)
            {
                this.applyNewValueToConfig();
                return true;
            }
            else
            {
                return this.textField.onKeyTyped(keyCode, scanCode, modifiers);
            }
        }

        return false;
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers)
    {
        if (this.textField != null && this.textField.onCharTyped(charIn, modifiers))
        {
            return true;
        }

        return super.onCharTypedImpl(charIn, modifiers);
    }

    @Override
    public boolean canSelectAt(int mouseX, int mouseY, int mouseButton)
    {
        return false;
    }

    protected void drawTextFields(int mouseX, int mouseY, PoseStack matrixStack)
    {
        if (this.textField != null)
        {
            this.textField.getTextField().render(matrixStack, mouseX, mouseY, 0f);
        }
    }
}
