package dev.dubhe.forgemalilib.config;

import dev.dubhe.forgemalilib.interfaces.IValueChangeCallback;

public interface IConfigNotifiable<T extends IConfigBase>
{
    void onValueChanged();

    void setValueChangeCallback(IValueChangeCallback<T> callback);
}
