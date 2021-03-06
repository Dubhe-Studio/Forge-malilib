package dev.dubhe.forgemalilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.dubhe.forgemalilib.ForgeMaliLib;
import dev.dubhe.forgemalilib.config.ConfigType;
import dev.dubhe.forgemalilib.util.Color4f;
import dev.dubhe.forgemalilib.util.StringUtils;

public class ConfigColor extends ConfigInteger
{
    private Color4f color;

    public ConfigColor(String name, String defaultValue, String comment)
    {
        super(name, StringUtils.getColor(defaultValue, 0), comment);

        this.color = Color4f.fromColor(this.getIntegerValue());
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.COLOR;
    }

    public Color4f getColor()
    {
        return this.color;
    }

    @Override
    public String getStringValue()
    {
        return String.format("#%08X", this.getIntegerValue());
    }

    @Override
    public String getDefaultStringValue()
    {
        return String.format("#%08X", this.getDefaultIntegerValue());
    }

    @Override
    public void setValueFromString(String value)
    {
        this.setIntegerValue(StringUtils.getColor(value, 0));
    }

    @Override
    public void setIntegerValue(int value)
    {
        this.color = Color4f.fromColor(value);

        super.setIntegerValue(value); // This also calls the callback, if set
    }

    @Override
    public boolean isModified(String newValue)
    {
        try
        {
            return StringUtils.getColor(newValue, 0) != this.getDefaultIntegerValue();
        }
        catch (Exception e)
        {
        }

        return true;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                this.value = this.getClampedValue(StringUtils.getColor(element.getAsString(), 0));
                this.color = Color4f.fromColor(this.value);
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
        return new JsonPrimitive(this.getStringValue());
    }
}
