package dev.dubhe.forgemalilib.event;

import dev.dubhe.forgemalilib.interfaces.IWorldLoadListener;

public interface IWorldLoadManager
{
    /**
     * Registers a handler for listening to client world changes.
     * @param listener
     */
    void registerWorldLoadPreHandler(IWorldLoadListener listener);

    /**
     * Un-registers a previously registered client world change handler.
     * @param listener
     */
    void unregisterWorldLoadPreHandler(IWorldLoadListener listener);

    /**
     * Registers a handler for listening to client world changes.
     * @param listener
     */
    void registerWorldLoadPostHandler(IWorldLoadListener listener);

    /**
     * Un-registers a previously registered client world change handler.
     * @param listener
     */
    void unregisterWorldLoadPostHandler(IWorldLoadListener listener);
}
