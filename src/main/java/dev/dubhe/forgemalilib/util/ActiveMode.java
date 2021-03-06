package dev.dubhe.forgemalilib.util;

import dev.dubhe.forgemalilib.config.IConfigOptionListEntry;

public enum ActiveMode implements IConfigOptionListEntry
{
    NEVER       ("never",       "forgemalilib.label.active_mode.never"),
    WITH_KEY    ("with_key",    "forgemalilib.label.active_mode.with_key"),
    ALWAYS      ("always",      "forgemalilib.label.active_mode.always");

    private final String configString;
    private final String translationKey;

    private ActiveMode(String configString, String translationKey)
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
    public ActiveMode fromString(String name)
    {
        return fromStringStatic(name);
    }

    public static ActiveMode fromStringStatic(String name)
    {
        for (ActiveMode mode : ActiveMode.values())
        {
            if (mode.configString.equalsIgnoreCase(name))
            {
                return mode;
            }
        }

        return ActiveMode.NEVER;
    }
}
