package dev.dubhe.forgemalilib.gui.interfaces;

import dev.dubhe.forgemalilib.config.IConfigBase;

public interface IConfigInfoProvider
{
    /**
     * Get the mouse-over hover info tooltip for the given config
     * @param config
     * @return
     */
    String getHoverInfo(IConfigBase config);
}
