package dev.dubhe.forgemalilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.dubhe.forgemalilib.ForgeMaliLib;
import dev.dubhe.forgemalilib.config.ConfigType;
import dev.dubhe.forgemalilib.config.IConfigValue;

public class ConfigString extends ConfigBase<ConfigString> implements IConfigValue
{
    private final String defaultValue;
    private String value;
    private String previousValue;

    public ConfigString(String name, String defaultValue, String comment)
    {
        super(ConfigType.STRING, name, comment);

        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.previousValue = defaultValue;
    }

    @Override
    public String getStringValue()
    {
        return this.value;
    }

    @Override
    public String getDefaultStringValue()
    {
        return this.defaultValue;
    }

    public String getOldStringValue()
    {
        return this.previousValue;
    }

    @Override
    public void setValueFromString(String value)
    {
        this.previousValue = this.value;
        this.value = value;

        if (this.previousValue.equals(this.value) == false)
        {
            this.onValueChanged();
        }
    }

    @Override
    public void resetToDefault()
    {
        this.setValueFromString(this.defaultValue);
    }

    @Override
    public boolean isModified()
    {
        return this.value.equals(this.defaultValue) == false;
    }

    @Override
    public boolean isModified(String newValue)
    {
        return this.defaultValue.equals(newValue) == false;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                this.value = element.getAsString();
                this.previousValue = this.value;
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
