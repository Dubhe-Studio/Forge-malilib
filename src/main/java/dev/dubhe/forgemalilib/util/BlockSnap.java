package dev.dubhe.forgemalilib.util;

import dev.dubhe.forgemalilib.config.IConfigOptionListEntry;

public enum BlockSnap implements IConfigOptionListEntry
{
    NONE        ("none",    "forgemalilib.gui.label.block_snap.none"),
    CENTER      ("center",  "forgemalilib.gui.label.block_snap.center"),
    CORNER      ("corner",  "forgemalilib.gui.label.block_snap.corner");

    private final String configString;
    private final String translationKey;

    BlockSnap(String configString, String translationKey)
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

    public BlockSnap fromString(String name)
    {
        return fromStringStatic(name);
    }

    public static BlockSnap fromStringStatic(String name)
    {
        for (BlockSnap val : BlockSnap.values())
        {
            if (val.name().equalsIgnoreCase(name))
            {
                return val;
            }
        }

        return BlockSnap.NONE;
    }
}
