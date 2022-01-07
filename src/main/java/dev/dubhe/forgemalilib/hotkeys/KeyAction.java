package dev.dubhe.forgemalilib.hotkeys;

import dev.dubhe.forgemalilib.config.IConfigOptionListEntry;
import dev.dubhe.forgemalilib.util.StringUtils;

public enum KeyAction implements IConfigOptionListEntry
{
    PRESS   ("press",   "forgemalilib.label.key_action.press"),
    RELEASE ("release", "forgemalilib.label.key_action.release"),
    BOTH    ("both",    "forgemalilib.label.key_action.both");

    private final String configString;
    private final String translationKey;

    private KeyAction(String configString, String translationKey)
    {
        this.configString = configString;
        this.translationKey = translationKey;
    }

    @Override
    public String getStringValue()
    {
        return this.configString;
    }

    @Override
    public String getDisplayName()
    {
        return StringUtils.translate(this.translationKey);
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward)
    {
        int id = this.ordinal();

        if (forward)
        {
            if (++id >= values().length)
            {
                id = 0;
            }
        }
        else
        {
            if (--id < 0)
            {
                id = values().length - 1;
            }
        }

        return values()[id % values().length];
    }

    @Override
    public KeyAction fromString(String name)
    {
        return fromStringStatic(name);
    }

    public static KeyAction fromStringStatic(String name)
    {
        for (KeyAction action : KeyAction.values())
        {
            if (action.configString.equalsIgnoreCase(name))
            {
                return action;
            }
        }

        return KeyAction.PRESS;
    }
}
