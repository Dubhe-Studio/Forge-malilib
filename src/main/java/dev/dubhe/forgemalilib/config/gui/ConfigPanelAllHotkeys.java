package dev.dubhe.forgemalilib.config.gui;

import java.util.List;
import com.google.common.collect.ImmutableList;
import dev.dubhe.forgemalilib.MaLiLibReference;
import dev.dubhe.forgemalilib.config.ConfigManager;
import dev.dubhe.forgemalilib.event.InputEventHandler;
import dev.dubhe.forgemalilib.hotkeys.IHotkey;
import dev.dubhe.forgemalilib.hotkeys.KeybindCategory;

public class ConfigPanelAllHotkeys extends GuiModConfigs
{
    public ConfigPanelAllHotkeys()
    {
        super(MaLiLibReference.MOD_ID, createWrappers(), false, "forgemalilib.gui.title.all_hotkeys");
    }

    protected static List<ConfigOptionWrapper> createWrappers()
    {
        List<KeybindCategory> categories = InputEventHandler.getKeybindManager().getKeybindCategories();
        ImmutableList.Builder<ConfigOptionWrapper> builder = ImmutableList.builder();
        boolean first = true;

        for (KeybindCategory category : categories)
        {
            // Category header
            String header = category.getModName() + " - " + category.getCategory();

            if (first == false)
            {
                builder.add(new ConfigOptionWrapper(""));
            }

            builder.add(new ConfigOptionWrapper(header));
            builder.add(new ConfigOptionWrapper("-------------------------------------------------------------------"));
            first = false;

            for (IHotkey hotkey : category.getHotkeys())
            {
                builder.add(new ConfigOptionWrapper(hotkey));
            }
        }

        return builder.build();
    }

    @Override
    protected void onSettingsChanged()
    {
        ((ConfigManager) ConfigManager.getInstance()).saveAllConfigs();
        InputEventHandler.getKeybindManager().updateUsedKeys();
    }
}
