package dev.dubhe.forgemalilib.config;

import dev.dubhe.forgemalilib.interfaces.IStringValue;

public interface IStringRepresentable extends IStringValue
{
    String getDefaultStringValue();

    /**
     * Parses the value of this config from a String. Used for example to get the new value from
     * the config GUI textfield.
     * @param value
     */
    void setValueFromString(String value);

    /**
     * Checks whether or not the given value would be modified from the default value.
     * @param newValue
     * @return
     */
    boolean isModified(String newValue);
}
