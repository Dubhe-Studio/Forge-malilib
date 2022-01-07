package dev.dubhe.forgemalilib.gui.widgets;

import dev.dubhe.forgemalilib.gui.interfaces.IGuiIcon;
import dev.dubhe.forgemalilib.render.RenderUtils;

public class WidgetIcon extends WidgetBase
{
    protected final IGuiIcon icon;

    public WidgetIcon(int x, int y, IGuiIcon icon)
    {
        super(x, y, icon.getWidth(), icon.getHeight());

        this.icon = icon;
    }

    public void render(boolean enabled, boolean selected)
    {
        RenderUtils.color(1f, 1f, 1f, 1f);
        this.bindTexture(this.icon.getTexture());
        this.icon.renderAt(this.x, this.y, this.zLevel, enabled, selected);

        if (selected)
        {
            RenderUtils.drawOutlinedBox(this.x, this.y, this.width, this.height, 0x20C0C0C0, 0xE0FFFFFF);
        }
    }
}
