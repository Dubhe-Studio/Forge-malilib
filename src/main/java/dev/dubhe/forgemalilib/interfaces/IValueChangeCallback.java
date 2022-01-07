package dev.dubhe.forgemalilib.interfaces;

import dev.dubhe.forgemalilib.config.IConfigBase;

public interface IValueChangeCallback<T extends IConfigBase>
{
    /**
     * Called when (= after) the config's value is changed
     * @param feature
     */
    void onValueChanged(T config);
}
