package dev.dubhe.forgemalilib;

import java.util.Collections;
import java.util.List;

import dev.dubhe.forgemalilib.config.IConfigBase;
import dev.dubhe.forgemalilib.gui.GuiConfigsBase;
import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.button.ButtonGeneric;
import dev.dubhe.forgemalilib.gui.button.IButtonActionListener;
import dev.dubhe.forgemalilib.util.StringUtils;

public class MaLiLibConfigGui extends GuiConfigsBase
{
    private static ConfigGuiTab tab = ConfigGuiTab.GENERIC;

    public MaLiLibConfigGui()
    {
        super(10, 50, MaLiLibReference.MOD_ID, null, "forgemalilib.gui.title.configs");
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values())
        {
            x += this.createButton(x, y, -1, tab) + 2;
        }
    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab)
    {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(MaLiLibConfigGui.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth();
    }

    @Override
    protected int getConfigWidth()
    {
        ConfigGuiTab tab = MaLiLibConfigGui.tab;

        if (tab == ConfigGuiTab.GENERIC)
        {
            return 200;
        }

        return super.getConfigWidth();
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs()
    {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = MaLiLibConfigGui.tab;

        if (tab == ConfigGuiTab.GENERIC)
        {
            configs = MaLiLibConfigs.Generic.OPTIONS;
        }
        else if (tab == ConfigGuiTab.DEBUG)
        {
            configs = MaLiLibConfigs.Debug.OPTIONS;
        }
        else
        {
            return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    private static class ButtonListener implements IButtonActionListener
    {
        private final MaLiLibConfigGui parent;
        private final ConfigGuiTab tab;

        public ButtonListener(ConfigGuiTab tab, MaLiLibConfigGui parent)
        {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton)
        {
            MaLiLibConfigGui.tab = this.tab;

            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }

    public enum ConfigGuiTab
    {
        GENERIC ("forgemalilib.gui.title.generic"),
        DEBUG   ("forgemalilib.gui.title.debug");

        private final String translationKey;

        private ConfigGuiTab(String translationKey)
        {
            this.translationKey = translationKey;
        }

        public String getDisplayName()
        {
            return StringUtils.translate(this.translationKey);
        }
    }
}
