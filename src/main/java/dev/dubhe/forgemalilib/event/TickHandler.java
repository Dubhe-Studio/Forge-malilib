package dev.dubhe.forgemalilib.event;

import java.util.ArrayList;
import java.util.List;

import dev.dubhe.forgemalilib.interfaces.IClientTickHandler;
import net.minecraft.client.Minecraft;

public class TickHandler
{
    private static final TickHandler INSTANCE = new TickHandler();

    private final List<IClientTickHandler> clientTickHandlers = new ArrayList<>();

    public static TickHandler getInstance()
    {
        return INSTANCE;
    }

    public void registerClientTickHandler(IClientTickHandler handler)
    {
        if (this.clientTickHandlers.contains(handler) == false)
        {
            this.clientTickHandlers.add(handler);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onClientTick(Minecraft mc)
    {
        if (this.clientTickHandlers.isEmpty() == false)
        {
            for (IClientTickHandler handler : this.clientTickHandlers)
            {
                handler.onClientTick(mc);
            }
        }
    }
}
