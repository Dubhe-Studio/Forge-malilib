package dev.dubhe.forgemalilib.config;

public interface IConfigOptionList
{
    IConfigOptionListEntry getOptionListValue();

    IConfigOptionListEntry getDefaultOptionListValue();

    void setOptionListValue(IConfigOptionListEntry value);
}
