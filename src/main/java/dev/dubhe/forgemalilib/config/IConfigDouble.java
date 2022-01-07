package dev.dubhe.forgemalilib.config;

public interface IConfigDouble extends IConfigValue, IConfigSlider
{
    double getDoubleValue();

    double getDefaultDoubleValue();

    void setDoubleValue(double value);

    double getMinDoubleValue();

    double getMaxDoubleValue();
}
