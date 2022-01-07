package dev.dubhe.forgemalilib.gui.widgets;

import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.config.options.ConfigColor;
import dev.dubhe.forgemalilib.gui.interfaces.IConfigInfoProvider;
import dev.dubhe.forgemalilib.gui.interfaces.IGuiIcon;
import dev.dubhe.forgemalilib.gui.interfaces.IKeybindConfigGui;
import dev.dubhe.forgemalilib.gui.interfaces.ISliderCallback;
import net.minecraft.client.gui.screens.Screen;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.dubhe.forgemalilib.config.ConfigType;
import dev.dubhe.forgemalilib.config.IConfigBase;
import dev.dubhe.forgemalilib.config.IConfigBoolean;
import dev.dubhe.forgemalilib.config.IConfigDouble;
import dev.dubhe.forgemalilib.config.IConfigInteger;
import dev.dubhe.forgemalilib.config.IConfigOptionList;
import dev.dubhe.forgemalilib.config.IConfigResettable;
import dev.dubhe.forgemalilib.config.IConfigSlider;
import dev.dubhe.forgemalilib.config.IConfigStringList;
import dev.dubhe.forgemalilib.config.IConfigValue;
import dev.dubhe.forgemalilib.config.IStringRepresentable;
import dev.dubhe.forgemalilib.config.gui.ConfigOptionChangeListenerButton;
import dev.dubhe.forgemalilib.config.gui.ConfigOptionChangeListenerKeybind;
import dev.dubhe.forgemalilib.config.gui.ConfigOptionChangeListenerTextField;
import dev.dubhe.forgemalilib.config.gui.ConfigOptionListenerResetConfig;
import dev.dubhe.forgemalilib.config.gui.SliderCallbackDouble;
import dev.dubhe.forgemalilib.config.gui.SliderCallbackInteger;
import dev.dubhe.forgemalilib.gui.GuiBase;
import dev.dubhe.forgemalilib.gui.GuiConfigsBase.ConfigOptionWrapper;
import dev.dubhe.forgemalilib.gui.GuiTextFieldGeneric;
import dev.dubhe.forgemalilib.gui.MaLiLibIcons;
import dev.dubhe.forgemalilib.gui.button.ButtonBase;
import dev.dubhe.forgemalilib.gui.button.ButtonGeneric;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonBoolean;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonKeybind;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonOptionList;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonStringList;
import dev.dubhe.forgemalilib.gui.button.IButtonActionListener;
import dev.dubhe.forgemalilib.hotkeys.IHotkey;
import dev.dubhe.forgemalilib.hotkeys.IKeybind;
import dev.dubhe.forgemalilib.hotkeys.KeybindSettings;
import dev.dubhe.forgemalilib.render.RenderUtils;
import dev.dubhe.forgemalilib.util.GuiUtils;

public class WidgetConfigOption extends WidgetConfigOptionBase<ConfigOptionWrapper>
{
    protected final ConfigOptionWrapper wrapper;
    protected final IKeybindConfigGui host;
    @Nullable protected final KeybindSettings initialKeybindSettings;
    @Nullable protected ImmutableList<String> initialStringList;
    protected int colorDisplayPosX;

    public WidgetConfigOption(int x, int y, int width, int height, int labelWidth, int configWidth,
            ConfigOptionWrapper wrapper, int listIndex, IKeybindConfigGui host, WidgetListConfigOptionsBase<?, ?> parent)
    {
        super(x, y, width, height, parent, wrapper, listIndex);

        this.host = host;
        this.wrapper = wrapper;

        if (wrapper.getType() == ConfigOptionWrapper.Type.CONFIG)
        {
            IConfigBase config = wrapper.getConfig();

            if (wrapper.getConfig() instanceof IStringRepresentable)
            {
                IStringRepresentable configStr = (IStringRepresentable) config;
                this.initialStringValue = configStr.getStringValue();
                this.lastAppliedValue = configStr.getStringValue();
                this.initialKeybindSettings = config.getType() == ConfigType.HOTKEY ? ((IHotkey) config).getKeybind().getSettings() : null;
            }
            else
            {
                this.initialStringValue = null;
                this.lastAppliedValue = null;
                this.initialKeybindSettings = null;

                if (wrapper.getConfig() instanceof IConfigStringList)
                {
                    this.initialStringList = ImmutableList.copyOf(((IConfigStringList) wrapper.getConfig()).getStrings());
                }
            }

            this.addConfigOption(x, y, zLevel, labelWidth, configWidth, config);
        }
        else
        {
            this.initialStringValue = null;
            this.lastAppliedValue = null;
            this.initialKeybindSettings = null;

            this.addLabel(x, y + 7, labelWidth, 8, 0xFFFFFFFF, wrapper.getLabel());
        }
    }

    protected void addConfigOption(int x, int y, float zLevel, int labelWidth, int configWidth, IConfigBase config)
    {
        ConfigType type = config.getType();

        y += 1;
        int configHeight = 20;

        this.addLabel(x, y + 7, labelWidth, 8, 0xFFFFFFFF, config.getConfigGuiDisplayName());

        String comment = null;
        IConfigInfoProvider infoProvider = this.host.getHoverInfoProvider();

        if (infoProvider != null)
        {
            comment = infoProvider.getHoverInfo(config);
        }
        else
        {
            comment = config.getComment();
        }

        if (comment != null)
        {
            this.addConfigComment(x, y + 5, labelWidth, 12, comment);
        }

        x += labelWidth + 10;

        if (type == ConfigType.BOOLEAN)
        {
            ConfigButtonBoolean optionButton = new ConfigButtonBoolean(x, y, configWidth, configHeight, (IConfigBoolean) config);
            this.addConfigButtonEntry(x + configWidth + 4, y, (IConfigResettable) config, optionButton);
        }
        else if (type == ConfigType.OPTION_LIST)
        {
            ConfigButtonOptionList optionButton = new ConfigButtonOptionList(x, y, configWidth, configHeight, (IConfigOptionList) config);
            this.addConfigButtonEntry(x + configWidth + 4, y, (IConfigResettable) config, optionButton);
        }
        else if (type == ConfigType.STRING_LIST)
        {
            ConfigButtonStringList optionButton = new ConfigButtonStringList(x, y, configWidth, configHeight, (IConfigStringList) config, this.host, this.host.getDialogHandler());
            this.addConfigButtonEntry(x + configWidth + 4, y, (IConfigResettable) config, optionButton);
        }
        else if (type == ConfigType.HOTKEY)
        {
            configWidth -= 25; // adjust the width to match other configs due to the settings widget
            IKeybind keybind = ((IHotkey) config).getKeybind();
            ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, configHeight, keybind, this.host);
            x += configWidth + 4;

            this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, config.getName(), this.parent, this.host.getDialogHandler()));
            x += 25;

            this.addButton(keybindButton, this.host.getButtonPressListener());
            this.addKeybindResetButton(x, y, keybind, keybindButton);
        }
        else if (type == ConfigType.STRING ||
                 type == ConfigType.COLOR ||
                 type == ConfigType.INTEGER ||
                 type == ConfigType.DOUBLE)
        {
            int resetX = x + configWidth + 4;

            if (type == ConfigType.COLOR)
            {
                configWidth -= 24; // adjust the width to match other configs due to the color display
                this.colorDisplayPosX = x + configWidth + 4;
            }
            else if (type == ConfigType.INTEGER || type == ConfigType.DOUBLE)
            {
                configWidth -= 18;
                this.colorDisplayPosX = x + configWidth + 2;
            }

            if ((type == ConfigType.INTEGER || type == ConfigType.DOUBLE) &&
                 config instanceof IConfigSlider && ((IConfigSlider) config).shouldUseSlider())
            {
                this.addConfigSliderEntry(x, y, resetX, configWidth, configHeight, (IConfigSlider) config);
            }
            else
            {
                this.addConfigTextFieldEntry(x, y, resetX, configWidth, configHeight, (IConfigValue) config);
            }

            if (config instanceof IConfigSlider)
            {
                IGuiIcon icon = ((IConfigSlider) config).shouldUseSlider() ? MaLiLibIcons.BTN_TXTFIELD : MaLiLibIcons.BTN_SLIDER;
                ButtonGeneric toggleBtn = new ButtonGeneric(this.colorDisplayPosX, y + 2, icon);
                this.addButton(toggleBtn, new ListenerSliderToggle((IConfigSlider) config));
            }
        }
    }

    @Override
    public boolean wasConfigModified()
    {
        if (this.wrapper.getType() == ConfigOptionWrapper.Type.CONFIG)
        {
            IConfigBase config = this.wrapper.getConfig();
            boolean modified = false;

            if (this.wrapper.getConfig() instanceof IStringRepresentable)
            {
                if (this.textField != null)
                {
                    modified |= this.initialStringValue.equals(this.textField.getTextField().getValue()) == false;
                }

                if (this.initialKeybindSettings != null && this.initialKeybindSettings.equals(((IHotkey) config).getKeybind().getSettings()) == false)
                {
                    modified = true;
                }

                return modified || this.initialStringValue.equals(((IStringRepresentable) config).getStringValue()) == false;
            }
            else if (this.initialStringList != null && this.wrapper.getConfig() instanceof IConfigStringList)
            {
                return this.initialStringList.equals(((IConfigStringList) this.wrapper.getConfig()).getStrings()) == false;
            }
        }

        return false;
    }

    public void applyNewValueToConfig()
    {
        if (this.wrapper.getType() == ConfigOptionWrapper.Type.CONFIG && this.wrapper.getConfig() instanceof IStringRepresentable)
        {
            IStringRepresentable config = (IStringRepresentable) this.wrapper.getConfig();

            if (this.textField != null && this.hasPendingModifications())
            {
                config.setValueFromString(this.textField.getTextField().getValue());
            }

            this.lastAppliedValue = config.getStringValue();
        }
    }

    protected void addConfigComment(int x, int y, int width, int height, String comment)
    {
        this.addWidget(new WidgetHoverInfo(x, y, width, height, comment));
    }

    protected void addConfigButtonEntry(int xReset, int yReset, IConfigResettable config, ButtonBase optionButton)
    {
        ButtonGeneric resetButton = this.createResetButton(xReset, yReset, config);
        ConfigOptionChangeListenerButton listenerChange = new ConfigOptionChangeListenerButton(config, resetButton, null);
        ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, new ConfigOptionListenerResetConfig.ConfigResetterButton(optionButton), resetButton, null);

        this.addButton(optionButton, listenerChange);
        this.addButton(resetButton, listenerReset);
    }

    protected void addConfigTextFieldEntry(int x, int y, int resetX, int configWidth, int configHeight, IConfigValue config)
    {
        GuiTextFieldGeneric field = this.createTextField(x, y + 1, configWidth - 4, configHeight - 3);
        field.setMaxLength(this.maxTextfieldTextLength);
        field.setValue(config.getStringValue());

        ButtonGeneric resetButton = this.createResetButton(resetX, y, config);
        ConfigOptionChangeListenerTextField listenerChange = new ConfigOptionChangeListenerTextField(config, field, resetButton);
        ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, new ConfigOptionListenerResetConfig.ConfigResetterTextField(config, field), resetButton, null);

        this.addTextField(field, listenerChange);
        this.addButton(resetButton, listenerReset);
    }

    protected void addConfigSliderEntry(int x, int y, int resetX, int configWidth, int configHeight, IConfigSlider config)
    {
        ButtonGeneric resetButton = this.createResetButton(resetX, y, config);
        ISliderCallback callback;

        if (config instanceof IConfigDouble)
        {
            callback = new SliderCallbackDouble((IConfigDouble) config, resetButton);
        }
        else if (config instanceof IConfigInteger)
        {
            callback = new SliderCallbackInteger((IConfigInteger) config, resetButton);
        }
        else
        {
            return;
        }

        WidgetSlider slider = new WidgetSlider(x, y, configWidth, configHeight, callback);
        ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, null, resetButton, null);

        this.addWidget(slider);
        this.addButton(resetButton, listenerReset);
    }

    protected void addKeybindResetButton(int x, int y, IKeybind keybind, ConfigButtonKeybind buttonHotkey)
    {
        ButtonGeneric button = this.createResetButton(x, y, keybind);

        ConfigOptionChangeListenerKeybind listener = new ConfigOptionChangeListenerKeybind(keybind, buttonHotkey, button, this.host);
        this.host.addKeybindChangeListener(listener);
        this.addButton(button, listener);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, PoseStack matrixStack)
    {
        RenderUtils.color(1f, 1f, 1f, 1f);

        this.drawSubWidgets(mouseX, mouseY, matrixStack);

        if (this.wrapper.getType() == ConfigOptionWrapper.Type.CONFIG)
        {
            IConfigBase config = this.wrapper.getConfig();
            this.drawTextFields(mouseX, mouseY, matrixStack);
            super.render(mouseX, mouseY, selected, matrixStack);

            if (config.getType() == ConfigType.COLOR)
            {
                int y = this.y + 1;
                RenderUtils.drawRect(this.colorDisplayPosX    , y + 0, 19, 19, 0xFFFFFFFF);
                RenderUtils.drawRect(this.colorDisplayPosX + 1, y + 1, 17, 17, 0xFF000000);
                RenderUtils.drawRect(this.colorDisplayPosX + 2, y + 2, 15, 15, 0xFF000000 | ((ConfigColor) config).getIntegerValue());
            }
        }
    }

    public static class ListenerSliderToggle implements IButtonActionListener
    {
        protected final IConfigSlider config;

        public ListenerSliderToggle(IConfigSlider config)
        {
            this.config = config;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton)
        {
            this.config.toggleUseSlider();

            Screen gui = GuiUtils.getCurrentScreen();

            if (gui instanceof GuiBase)
            {
                ((GuiBase) gui).initGui();
            }
        }
    }
}
