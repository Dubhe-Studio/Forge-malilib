package dev.dubhe.forgemalilib.gui.interfaces;

import java.io.File;
import javax.annotation.Nullable;

public interface IDirectoryCache
{
    @Nullable
    File getCurrentDirectoryForContext(String context);

    void setCurrentDirectoryForContext(String context, File dir);
}
