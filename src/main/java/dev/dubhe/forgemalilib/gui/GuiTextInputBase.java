package dev.dubhe.forgemalilib.gui;

import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.button.ButtonGeneric;
import dev.dubhe.forgemalilib.gui.button.IButtonActionListener;
import dev.dubhe.forgemalilib.render.RenderUtils;
import dev.dubhe.forgemalilib.util.KeyCodes;
import dev.dubhe.forgemalilib.util.StringUtils;
import net.minecraft.client.gui.screens.Screen;
import com.mojang.blaze3d.vertex.PoseStack;

public abstract class GuiTextInputBase extends GuiDialogBase
{
    protected final GuiTextFieldGeneric textField;
    protected final String originalText;

    public GuiTextInputBase(int maxTextLength, String titleKey, String defaultText, @Nullable Screen parent)
    {
        this.setParent(parent);
        this.title = StringUtils.translate(titleKey);
        this.useTitleHierarchy = false;
        this.originalText = defaultText;

        this.setWidthAndHeight(260, 100);
        this.centerOnScreen();

        int width = Math.min(maxTextLength * 10, 240);
        this.textField = new GuiTextFieldGeneric(this.dialogLeft + 12, this.dialogTop + 40, width, 20, this.font);
        this.textField.setMaxLength(maxTextLength);
        this.textField.setFocused(true);
        this.textField.setValue(this.originalText);
        this.setBlitOffset(1);
    }

    @Override
    public void initGui()
    {
        int x = this.dialogLeft + 10;
        int y = this.dialogTop + 70;

        x += this.createButton(x, y, ButtonType.OK) + 2;
        x += this.createButton(x, y, ButtonType.RESET) + 2;
        this.createButton(x, y, ButtonType.CANCEL);

        this.mc.keyboardHandler.setSendRepeatsToGui(true);
    }

    protected int createButton(int x, int y, ButtonType type)
    {
        ButtonGeneric button = new ButtonGeneric(x, y, -1, 20, type.getDisplayName());
        button.setWidth(Math.max(40, button.getWidth()));
        return this.addButton(button, this.createActionListener(type)).getWidth();
    }

    @Override
    public boolean isPauseScreen()
    {
        return this.getParent() != null && this.getParent().isPauseScreen();
    }

    @Override
    public void drawContents(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        if (this.getParent() != null)
        {
            this.getParent().render(matrixStack, mouseX, mouseY, partialTicks);
        }

        matrixStack.pushPose();
        matrixStack.translate(0, 0, this.getBlitOffset());

        RenderUtils.drawOutlinedBox(this.dialogLeft, this.dialogTop, this.dialogWidth, this.dialogHeight, 0xE0000000, COLOR_HORIZONTAL_BAR);

        // Draw the title
        this.drawStringWithShadow(matrixStack, this.getTitleString(), this.dialogLeft + 10, this.dialogTop + 4, COLOR_WHITE);

        //super.drawScreen(mouseX, mouseY, partialTicks);
        this.textField.render(matrixStack, mouseX, mouseY, partialTicks);

        this.drawButtons(mouseX, mouseY, partialTicks, matrixStack);
        matrixStack.popPose();
    }

    @Override
    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == KeyCodes.KEY_ENTER)
        {
            // Only close the GUI if the value was successfully applied
            if (this.applyValue(this.textField.getValue()))
            {
                GuiBase.openGui(this.getParent());
            }

            return true;
        }
        else if (keyCode == KeyCodes.KEY_ESCAPE)
        {
            GuiBase.openGui(this.getParent());
            return true;
        }

        if (this.textField.isFocused())
        {
            return this.textField.keyPressed(keyCode, scanCode, modifiers);
        }

        return super.onKeyTyped(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean onCharTyped(char charIn, int modifiers)
    {
        if (this.textField.isFocused())
        {
            return this.textField.charTyped(charIn, modifiers);
        }

        return super.onCharTyped(charIn, modifiers);
    }

    @Override
    public boolean onMouseClicked(int mouseX, int mouseY, int button)
    {
        if (this.textField.mouseClicked(mouseX, mouseY, button))
        {
            return true;
        }

        return super.onMouseClicked(mouseX, mouseY, button);
    }

    protected ButtonListener createActionListener(ButtonType type)
    {
        return new ButtonListener(type, this);
    }

    protected abstract boolean applyValue(String string);

    protected static class ButtonListener implements IButtonActionListener
    {
        private final GuiTextInputBase gui;
        private final ButtonType type;

        public ButtonListener(ButtonType type, GuiTextInputBase gui)
        {
            this.type = type;
            this.gui = gui;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton)
        {
            if (this.type == ButtonType.OK)
            {
                // Only close the GUI if the value was successfully applied
                if (this.gui.applyValue(this.gui.textField.getValue()))
                {
                    GuiBase.openGui(this.gui.getParent());
                }
            }
            else if (this.type == ButtonType.CANCEL)
            {
                GuiBase.openGui(this.gui.getParent());
            }
            else if (this.type == ButtonType.RESET)
            {
                this.gui.textField.setValue(this.gui.originalText);
                this.gui.textField.setFocused(true);
            }
        }
    }

    protected enum ButtonType
    {
        OK      ("forgemalilib.gui.button.ok"),
        CANCEL  ("forgemalilib.gui.button.cancel"),
        RESET   ("forgemalilib.gui.button.reset");

        private final String labelKey;

        ButtonType(String labelKey)
        {
            this.labelKey = labelKey;
        }

        public String getDisplayName()
        {
            return StringUtils.translate(this.labelKey);
        }
    }
}
