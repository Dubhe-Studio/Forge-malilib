package dev.dubhe.forgemalilib.util;

import dev.dubhe.forgemalilib.config.IConfigOptionListEntry;

public enum LayerMode implements IConfigOptionListEntry
{
    ALL             ("all",             "forgemalilib.gui.label.layer_mode.all"),
    SINGLE_LAYER    ("single_layer",    "forgemalilib.gui.label.layer_mode.single_layer"),
    LAYER_RANGE     ("layer_range",     "forgemalilib.gui.label.layer_mode.layer_range"),
    ALL_BELOW       ("all_below",       "forgemalilib.gui.label.layer_mode.all_below"),
    ALL_ABOVE       ("all_above",       "forgemalilib.gui.label.layer_mode.all_above");

    private final String configString;
    private final String translationKey;

    private LayerMode(String configString, String translationKey)
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
    public LayerMode fromString(String name)
    {
        return fromStringStatic(name);
    }

    public static LayerMode fromStringStatic(String name)
    {
        for (LayerMode mode : LayerMode.values())
        {
            if (mode.configString.equalsIgnoreCase(name))
            {
                return mode;
            }
        }

        return LayerMode.ALL;
    }
}
