package dev.dubhe.forgemalilib.network;

public interface IClientPacketChannelHandler
{
    void registerClientChannelHandler(IPluginChannelHandler handler);

    void unregisterClientChannelHandler(IPluginChannelHandler handler);
}
