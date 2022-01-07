package dev.dubhe.forgemalilib.config.gui;

import java.util.List;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.forgemalilib.config.IConfigBase;
import dev.dubhe.forgemalilib.gui.GuiConfigsBase;

public class GuiModConfigs extends GuiConfigsBase
{
    protected final List<ConfigOptionWrapper> configs;

    public GuiModConfigs(String modId, List<? extends IConfigBase> configs, String titleKey, Object... args)
    {
        this(modId, ConfigOptionWrapper.createFor(configs), false, titleKey, args);
    }

    public GuiModConfigs(String modId, List<ConfigOptionWrapper> wrappers, boolean unused, String titleKey, Object... args)
    {
        super(10, 0, modId, null, titleKey, args);

        this.configs = wrappers;
    }

    @Override
    protected int getBrowserHeight()
    {
        return this.height - 70;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs()
    {
        return this.configs;
    }

    @Override
    protected void drawTitle(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        // NO-OP
    }
}
