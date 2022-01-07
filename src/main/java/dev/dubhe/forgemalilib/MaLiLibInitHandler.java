package dev.dubhe.forgemalilib;

import dev.dubhe.forgemalilib.config.ConfigManager;
import dev.dubhe.forgemalilib.event.InputEventHandler;
import dev.dubhe.forgemalilib.gui.GuiBase;
import dev.dubhe.forgemalilib.hotkeys.IHotkeyCallback;
import dev.dubhe.forgemalilib.hotkeys.IKeybind;
import dev.dubhe.forgemalilib.hotkeys.KeyAction;
import dev.dubhe.forgemalilib.interfaces.IInitializationHandler;

public class MaLiLibInitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        ConfigManager.getInstance().registerConfigHandler(MaLiLibReference.MOD_ID, new MaLiLibConfigs());
        InputEventHandler.getKeybindManager().registerKeybindProvider(MaLiLibInputHandler.getInstance());

        MaLiLibConfigs.Generic.OPEN_GUI_CONFIGS.getKeybind().setCallback(new CallbackOpenConfigGui());
    }

    private static class CallbackOpenConfigGui implements IHotkeyCallback
    {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key)
        {
            GuiBase.openGui(new MaLiLibConfigGui());
            return true;
        }
    }
}
