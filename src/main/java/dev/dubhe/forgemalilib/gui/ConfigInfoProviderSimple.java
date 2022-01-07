package dev.dubhe.forgemalilib.gui;

import dev.dubhe.forgemalilib.config.IConfigBase;
import dev.dubhe.forgemalilib.gui.interfaces.IConfigInfoProvider;

public class ConfigInfoProviderSimple implements IConfigInfoProvider
{
    protected final String prefix;
    protected final String suffix;

    public ConfigInfoProviderSimple(String prefix, String suffix)
    {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String getHoverInfo(IConfigBase config)
    {
        return this.prefix + config.getPrettyName() + this.suffix;
    }
}
