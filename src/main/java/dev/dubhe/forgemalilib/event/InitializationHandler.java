package dev.dubhe.forgemalilib.event;

import java.util.ArrayList;
import java.util.List;

import dev.dubhe.forgemalilib.config.ConfigManager;
import dev.dubhe.forgemalilib.interfaces.IInitializationDispatcher;
import dev.dubhe.forgemalilib.interfaces.IInitializationHandler;

public class InitializationHandler implements IInitializationDispatcher
{
    private static final InitializationHandler INSTANCE = new InitializationHandler();

    private final List<IInitializationHandler> handlers = new ArrayList<>();

    public static IInitializationDispatcher getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerInitializationHandler(IInitializationHandler handler)
    {
        if (this.handlers.contains(handler) == false)
        {
            this.handlers.add(handler);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onGameInitDone()
    {
        if (this.handlers.isEmpty() == false)
        {
            for (IInitializationHandler handler : this.handlers)
            {
                handler.registerModHandlers();
            }
        }

        ((ConfigManager) ConfigManager.getInstance()).loadAllConfigs();
        InputEventHandler.getKeybindManager().updateUsedKeys();
    }
}
