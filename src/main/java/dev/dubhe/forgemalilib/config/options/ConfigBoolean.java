package dev.dubhe.forgemalilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.dubhe.forgemalilib.ForgeMaliLib;
import dev.dubhe.forgemalilib.config.ConfigType;
import dev.dubhe.forgemalilib.config.IConfigBoolean;

public class ConfigBoolean extends ConfigBase<ConfigBoolean> implements IConfigBoolean
{
    private final boolean defaultValue;
    private boolean value;

    public ConfigBoolean(String name, boolean defaultValue, String comment)
    {
        this(name, defaultValue, comment, name);
    }

    public ConfigBoolean(String name, boolean defaultValue, String comment, String prettyName)
    {
        super(ConfigType.BOOLEAN, name, comment, prettyName);

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public boolean getBooleanValue()
    {
        return this.value;
    }

    @Override
    public boolean getDefaultBooleanValue()
    {
        return this.defaultValue;
    }

    @Override
    public void setBooleanValue(boolean value)
    {
        boolean oldValue = this.value;
        this.value = value;

        if (oldValue != this.value)
        {
            this.onValueChanged();
        }
    }

    @Override
    public boolean isModified()
    {
        return this.value != this.defaultValue;
    }

    @Override
    public boolean isModified(String newValue)
    {
        return Boolean.parseBoolean(newValue) != this.defaultValue;
    }

    @Override
    public void resetToDefault()
    {
        this.setBooleanValue(this.defaultValue);
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(this.value);
    }

    @Override
    public String getDefaultStringValue()
    {
        return String.valueOf(this.defaultValue);
    }

    @Override
    public void setValueFromString(String value)
    {
        this.value = Boolean.parseBoolean(value);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                this.value = element.getAsBoolean();
            }
            else
            {
                ForgeMaliLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        }
        catch (Exception e)
        {
            ForgeMaliLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }

    @Override
    public JsonElement getAsJsonElement()
    {
        return new JsonPrimitive(this.value);
    }
}
