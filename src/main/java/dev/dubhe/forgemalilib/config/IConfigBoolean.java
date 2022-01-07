package dev.dubhe.forgemalilib.config;

public interface IConfigBoolean extends IConfigValue
{
    boolean getBooleanValue();

    boolean getDefaultBooleanValue();

    void setBooleanValue(boolean value);

    default void toggleBooleanValue()
    {
        this.setBooleanValue(! this.getBooleanValue());
    }
}
