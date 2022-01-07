package dev.dubhe.forgemalilib.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.forgemalilib.gui.interfaces.IGuiIcon;
import dev.dubhe.forgemalilib.render.RenderUtils;

public class WidgetInfoIcon extends WidgetHoverInfo
{
    protected final IGuiIcon icon;

    public WidgetInfoIcon(int x, int y, IGuiIcon icon, String key, Object... args)
    {
        super(x, y, icon.getWidth(), icon.getHeight(), key, args);

        this.icon = icon;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, PoseStack matrixStack)
    {
        RenderUtils.color(1f, 1f, 1f, 1f);
        this.bindTexture(this.icon.getTexture());
        this.icon.renderAt(this.x, this.y, this.zLevel, false, selected);
    }
}
