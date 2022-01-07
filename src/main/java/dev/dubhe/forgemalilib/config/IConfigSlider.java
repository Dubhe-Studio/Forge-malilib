package dev.dubhe.forgemalilib.config;

public interface IConfigSlider extends IConfigValue
{
    default boolean shouldUseSlider()
    {
        return false;
    }

    default void toggleUseSlider()
    {
    }
}
