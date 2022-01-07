package dev.dubhe.forgemalilib.gui;

import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.gui.interfaces.IMessageConsumer;
import dev.dubhe.forgemalilib.interfaces.ICompletionListener;
import dev.dubhe.forgemalilib.interfaces.IStringConsumer;
import dev.dubhe.forgemalilib.interfaces.IStringConsumerFeedback;
import net.minecraft.client.gui.screens.Screen;

public class GuiTextInput extends GuiTextInputBase implements ICompletionListener
{
    protected final IStringConsumer consumer;
    protected final IStringConsumerFeedback consumerFeedback;

    public GuiTextInput(int maxTextLength, String titleKey, String defaultText, @Nullable Screen parent, IStringConsumer consumer)
    {
        super(maxTextLength, titleKey, defaultText, parent);

        this.consumer = consumer;
        this.consumerFeedback = null;
    }

    public GuiTextInput(int maxTextLength, String titleKey, String defaultText, @Nullable Screen parent, IStringConsumerFeedback consumer)
    {
        super(maxTextLength, titleKey, defaultText, parent);

        this.consumer = null;
        this.consumerFeedback = consumer;
    }

    @Override
    protected boolean applyValue(String string)
    {
        if (this.consumerFeedback != null)
        {
            return this.consumerFeedback.setString(string);
        }

        this.consumer.setString(string);
        return true;
    }

    @Override
    public void onTaskCompleted()
    {
        if (this.getParent() instanceof ICompletionListener)
        {
            ((ICompletionListener) this.getParent()).onTaskCompleted();
        }
    }

    @Override
    public void onTaskAborted()
    {
        if (this.getParent() instanceof ICompletionListener)
        {
            ((ICompletionListener) this.getParent()).onTaskAborted();
        }
    }

    @Override
    public void addMessage(Message.MessageType type, int lifeTime, String messageKey, Object... args)
    {
        if (this.getParent() instanceof IMessageConsumer)
        {
            ((IMessageConsumer) this.getParent()).addMessage(type, lifeTime, messageKey, args);
        }
        else
        {
            super.addMessage(type, lifeTime, messageKey, args);
        }
    }
}
