package dev.dubhe.forgemalilib.hotkeys;

import dev.dubhe.forgemalilib.config.IConfigBoolean;
import dev.dubhe.forgemalilib.util.InfoUtils;

public class KeyCallbackToggleBooleanConfigWithMessage extends KeyCallbackToggleBoolean
{
    public KeyCallbackToggleBooleanConfigWithMessage(IConfigBoolean config)
    {
        super(config);
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key)
    {
        super.onKeyAction(action, key);

        InfoUtils.printBooleanConfigToggleMessage(this.config.getPrettyName(), this.config.getBooleanValue());

        return true;
    }
}
