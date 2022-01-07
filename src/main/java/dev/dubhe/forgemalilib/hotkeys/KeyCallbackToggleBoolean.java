package dev.dubhe.forgemalilib.hotkeys;

import dev.dubhe.forgemalilib.config.IConfigBoolean;

public class KeyCallbackToggleBoolean implements IHotkeyCallback
{
    protected final IConfigBoolean config;

    public KeyCallbackToggleBoolean(IConfigBoolean config)
    {
        this.config = config;
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key)
    {
        this.config.toggleBooleanValue();
        return true;
    }
}
