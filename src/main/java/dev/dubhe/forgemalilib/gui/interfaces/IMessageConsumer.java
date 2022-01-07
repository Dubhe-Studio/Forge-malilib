package dev.dubhe.forgemalilib.gui.interfaces;

import dev.dubhe.forgemalilib.gui.Message.MessageType;

public interface IMessageConsumer
{
    void addMessage(MessageType type, String messageKey, Object... args);

    void addMessage(MessageType type, int lifeTime, String messageKey, Object... args);
}
