package dev.dubhe.forgemalilib.gui.widgets;

import dev.dubhe.forgemalilib.gui.interfaces.IGuiIcon;
import net.minecraft.SharedConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.forgemalilib.gui.GuiBase;
import dev.dubhe.forgemalilib.gui.GuiTextFieldGeneric;
import dev.dubhe.forgemalilib.gui.LeftRight;
import dev.dubhe.forgemalilib.render.RenderUtils;
import dev.dubhe.forgemalilib.util.KeyCodes;

public class WidgetSearchBar extends WidgetBase
{
    protected final WidgetIcon iconSearch;
    protected final LeftRight iconAlignment;
    protected final GuiTextFieldGeneric searchBox;
    protected boolean searchOpen;

    public WidgetSearchBar(int x, int y, int width, int height,
                           int searchBarOffsetX, IGuiIcon iconSearch, LeftRight iconAlignment)
    {
        super(x, y, width, height);

        int iw = iconSearch.getWidth();
        int ix = iconAlignment == LeftRight.RIGHT ? x + width - iw - 1 : x + 2;
        int tx = iconAlignment == LeftRight.RIGHT ? x - searchBarOffsetX + 1 : x + iw + 6 + searchBarOffsetX;
        this.iconSearch = new WidgetIcon(ix, y + 1, iconSearch);
        this.iconAlignment = iconAlignment;
        this.searchBox = new GuiTextFieldGeneric(tx, y, width - iw - 7 - Math.abs(searchBarOffsetX), height, this.textRenderer);
        this.searchBox.setZLevel(this.zLevel);
    }

    public String getFilter()
    {
        return this.searchOpen ? this.searchBox.getValue() : "";
    }

    public boolean hasFilter()
    {
        return this.searchOpen && this.searchBox.getValue().isEmpty() == false;
    }

    public boolean isSearchOpen()
    {
        return this.searchOpen;
    }

    public void setSearchOpen(boolean isOpen)
    {
        this.searchOpen = isOpen;

        if (this.searchOpen)
        {
            this.searchBox.setFocused(true);
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton)
    {
        if (this.searchOpen && this.searchBox.mouseClicked(mouseX, mouseY, mouseButton))
        {
            return true;
        }
        else if (this.iconSearch.isMouseOver(mouseX, mouseY))
        {
            this.setSearchOpen(! this.searchOpen);
            return true;
        }

        return false;
    }

    @Override
    protected boolean onKeyTypedImpl(int keyCode, int scanCode, int modifiers)
    {
        if (this.searchOpen)
        {
            if (this.searchBox.keyPressed(keyCode, scanCode, modifiers))
            {
                return true;
            }
            else if (keyCode == KeyCodes.KEY_ESCAPE)
            {
                if (GuiBase.isShiftDown())
                {
                    this.mc.screen.onClose();
                }

                this.searchOpen = false;
                this.searchBox.setFocused(false);
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers)
    {
        if (this.searchOpen)
        {
            if (this.searchBox.charTyped(charIn, modifiers))
            {
                return true;
            }
        }
        else if (SharedConstants.isAllowedChatCharacter(charIn))
        {
            this.searchOpen = true;
            this.searchBox.setFocused(true);
            this.searchBox.setValue("");
            this.searchBox.charTyped(charIn, modifiers);

            return true;
        }

        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, PoseStack matrixStack)
    {
        RenderUtils.color(1f, 1f, 1f, 1f);
        this.iconSearch.render(false, this.iconSearch.isMouseOver(mouseX, mouseY));

        if (this.searchOpen)
        {
            this.searchBox.render(matrixStack, mouseX, mouseY, 0);
        }
    }
}
