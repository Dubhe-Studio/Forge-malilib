package dev.dubhe.forgemalilib.config.options;

import dev.dubhe.forgemalilib.config.IHotkeyTogglable;
import dev.dubhe.forgemalilib.hotkeys.IKeybind;
import dev.dubhe.forgemalilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import dev.dubhe.forgemalilib.hotkeys.KeybindMulti;
import dev.dubhe.forgemalilib.hotkeys.KeybindSettings;
import dev.dubhe.forgemalilib.util.StringUtils;

public class ConfigBooleanHotkeyed extends ConfigBoolean implements IHotkeyTogglable
{
    protected final IKeybind keybind;

    public ConfigBooleanHotkeyed(String name, boolean defaultValue, String defaultHotkey, String comment)
    {
        this(name, defaultValue, defaultHotkey, comment, StringUtils.splitCamelCase(name));
    }

    public ConfigBooleanHotkeyed(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName)
    {
        this(name, defaultValue, defaultHotkey, KeybindSettings.DEFAULT, comment, prettyName);
    }

    public ConfigBooleanHotkeyed(String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings, String comment, String prettyName)
    {
        super(name, defaultValue, comment, prettyName);

        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }

    @Override
    public IKeybind getKeybind()
    {
        return this.keybind;
    }

    @Override
    public void resetToDefault()
    {
        super.resetToDefault();

        this.keybind.resetToDefault();
    }
}
