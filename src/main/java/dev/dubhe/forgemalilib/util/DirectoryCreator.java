package dev.dubhe.forgemalilib.util;

import java.io.File;
import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.gui.interfaces.IDirectoryNavigator;
import dev.dubhe.forgemalilib.interfaces.IStringConsumerFeedback;
import dev.dubhe.forgemalilib.gui.Message.MessageType;

public class DirectoryCreator implements IStringConsumerFeedback
{
    protected final File dir;
    @Nullable protected final IDirectoryNavigator navigator;

    public DirectoryCreator(File dir, @Nullable IDirectoryNavigator navigator)
    {
        this.dir = dir;
        this.navigator = navigator;
    }

    @Override
    public boolean setString(String string)
    {
        if (string.isEmpty())
        {
            InfoUtils.showGuiOrActionBarMessage(MessageType.ERROR, "forgemalilib.error.invalid_directory", string);
            return false;
        }

        File file = new File(this.dir, string);

        if (file.exists())
        {
            InfoUtils.showGuiOrActionBarMessage(MessageType.ERROR, "forgemalilib.error.file_or_directory_already_exists", file.getAbsolutePath());
            return false;
        }

        if (file.mkdirs() == false)
        {
            InfoUtils.showGuiOrActionBarMessage(MessageType.ERROR, "forgemalilib.error.failed_to_create_directory", file.getAbsolutePath());
            return false;
        }

        if (this.navigator != null)
        {
            this.navigator.switchToDirectory(file);
        }

        InfoUtils.showGuiOrActionBarMessage(MessageType.SUCCESS, "forgemalilib.message.directory_created", string);

        return true;
    }
}
