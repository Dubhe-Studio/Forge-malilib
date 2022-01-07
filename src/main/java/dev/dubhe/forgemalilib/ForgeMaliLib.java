package dev.dubhe.forgemalilib;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dev.dubhe.forgemalilib.event.InitializationHandler;

@Mod("forgemalilib")
public class ForgeMaliLib {
    public static final Logger logger = LogManager.getLogger(MaLiLibReference.MOD_ID);
    public ForgeMaliLib()
    {
        InitializationHandler.getInstance().registerInitializationHandler(new MaLiLibInitHandler());
    }
}
