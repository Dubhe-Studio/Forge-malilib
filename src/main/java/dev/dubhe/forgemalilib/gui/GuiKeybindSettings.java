package dev.dubhe.forgemalilib.gui;

import java.util.List;
import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.config.options.ConfigBase;
import dev.dubhe.forgemalilib.config.options.ConfigBoolean;
import dev.dubhe.forgemalilib.config.options.ConfigOptionList;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonBoolean;
import dev.dubhe.forgemalilib.gui.button.ConfigButtonOptionList;
import dev.dubhe.forgemalilib.gui.interfaces.IDialogHandler;
import dev.dubhe.forgemalilib.gui.widgets.WidgetHoverInfo;
import dev.dubhe.forgemalilib.hotkeys.IKeybind;
import dev.dubhe.forgemalilib.hotkeys.KeyAction;
import dev.dubhe.forgemalilib.hotkeys.KeybindSettings;
import dev.dubhe.forgemalilib.render.RenderUtils;
import dev.dubhe.forgemalilib.util.KeyCodes;
import dev.dubhe.forgemalilib.util.StringUtils;
import net.minecraft.client.gui.screens.Screen;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;

public class GuiKeybindSettings extends GuiDialogBase
{
    protected final IKeybind keybind;
    protected final String keybindName;
    protected final ConfigOptionList cfgActivateOn  = new ConfigOptionList("forgemalilib.gui.label.keybind_settings.activate_on", KeyAction.PRESS, "forgemalilib.config.comment.keybind_settings.activate_on");
    protected final ConfigOptionList cfgContext     = new ConfigOptionList("forgemalilib.gui.label.keybind_settings.context", KeybindSettings.Context.INGAME, "forgemalilib.config.comment.keybind_settings.context");
    protected final ConfigBoolean cfgAllowEmpty     = new ConfigBoolean("forgemalilib.gui.label.keybind_settings.allow_empty_keybind", false, "forgemalilib.config.comment.keybind_settings.allow_empty_keybind");
    protected final ConfigBoolean cfgAllowExtra     = new ConfigBoolean("forgemalilib.gui.label.keybind_settings.allow_extra_keys", false, "forgemalilib.config.comment.keybind_settings.allow_extra_keys");
    protected final ConfigBoolean cfgOrderSensitive = new ConfigBoolean("forgemalilib.gui.label.keybind_settings.order_sensitive", false, "forgemalilib.config.comment.keybind_settings.order_sensitive");
    protected final ConfigBoolean cfgExclusive      = new ConfigBoolean("forgemalilib.gui.label.keybind_settings.exclusive", false, "forgemalilib.config.comment.keybind_settings.exclusive");
    protected final ConfigBoolean cfgCancel         = new ConfigBoolean("forgemalilib.gui.label.keybind_settings.cancel_further", false, "forgemalilib.config.comment.keybind_settings.cancel_further");
    protected final List<ConfigBase<?>> configList;
    @Nullable protected final IDialogHandler dialogHandler;
    protected int labelWidth;
    protected int configWidth;

    public GuiKeybindSettings(IKeybind keybind, String name, @Nullable IDialogHandler dialogHandler, Screen parent)
    {
        this.keybind = keybind;
        this.keybindName = name;
        this.dialogHandler = dialogHandler;

        // When we have a dialog handler, then we are inside the Liteloader config menu.
        // In there we don't want to use the normal "GUI replacement and render parent first" trick.
        // The "dialog handler" stuff is used within the Liteloader config menus,
        // because there we can't change the mc.currentScreen reference to this GUI,
        // because otherwise Liteloader will freak out.
        // So instead we are using a weird wrapper "sub panel" thingy in there, and thus
        // we can NOT try to render the parent GUI here in that case, otherwise it will
        // lead to an infinite recursion loop and a StackOverflowError.
        if (this.dialogHandler == null)
        {
            this.setParent(parent);
        }

        this.title = TXT_BOLD + StringUtils.translate("forgemalilib.gui.title.keybind_settings.advanced", this.keybindName) + TXT_RST;
        KeybindSettings settings = this.keybind.getSettings();

        this.cfgActivateOn.setOptionListValue(settings.getActivateOn());
        this.cfgContext.setOptionListValue(settings.getContext());
        this.cfgAllowEmpty.setBooleanValue(settings.getAllowEmpty());
        this.cfgAllowExtra.setBooleanValue(settings.getAllowExtraKeys());
        this.cfgOrderSensitive.setBooleanValue(settings.isOrderSensitive());
        this.cfgExclusive.setBooleanValue(settings.isExclusive());
        this.cfgCancel.setBooleanValue(settings.shouldCancel());

        this.configList = ImmutableList.of(this.cfgActivateOn, this.cfgContext, this.cfgAllowEmpty, this.cfgAllowExtra, this.cfgOrderSensitive, this.cfgExclusive, this.cfgCancel);
        this.labelWidth = this.getMaxPrettyNameLength(this.configList);
        this.configWidth = 100;

        int totalWidth = this.labelWidth + this.configWidth + 30;
        totalWidth = Math.max(totalWidth, this.getStringWidth(this.title) + 20);

        this.setWidthAndHeight(totalWidth, this.configList.size() * 22 + 30);
        this.centerOnScreen();

        this.init(this.mc, this.dialogWidth, this.dialogHeight);
    }

    @Override
    public void initGui()
    {
        this.clearElements();

        int x = this.dialogLeft + 10;
        int y = this.dialogTop + 24;

        for (ConfigBase<?> config : this.configList)
        {
            this.addConfig(x, y, this.labelWidth, this.configWidth, config);
            y += 22;
        }
    }

    protected void addConfig(int x, int y, int labelWidth, int configWidth, ConfigBase<?> config)
    {
        this.addLabel(x, y + 4, labelWidth, 10, 0xFFFFFFFF, StringUtils.translate(config.getPrettyName()));
        this.addWidget(new WidgetHoverInfo(x, y + 2, labelWidth, 12, config.getComment()));
        x += labelWidth + 10;

        if (config instanceof ConfigBoolean)
        {
            this.addWidget(new ConfigButtonBoolean(x, y, configWidth, 20, (ConfigBoolean) config));
        }
        else if (config instanceof ConfigOptionList)
        {
            this.addWidget(new ConfigButtonOptionList(x, y, configWidth, 20, (ConfigOptionList) config));
        }
    }

    @Override
    public void removed()
    {
        KeyAction activateOn = (KeyAction) this.cfgActivateOn.getOptionListValue();
        KeybindSettings.Context context = (KeybindSettings.Context) this.cfgContext.getOptionListValue();
        boolean allowEmpty = this.cfgAllowEmpty.getBooleanValue();
        boolean allowExtraKeys = this.cfgAllowExtra.getBooleanValue();
        boolean orderSensitive = this.cfgOrderSensitive.getBooleanValue();
        boolean exclusive = this.cfgExclusive.getBooleanValue();
        boolean cancel = this.cfgCancel.getBooleanValue();

        KeybindSettings settingsNew = KeybindSettings.create(context, activateOn, allowExtraKeys, orderSensitive, exclusive, cancel, allowEmpty);
        this.keybind.setSettings(settingsNew);

        super.removed();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        if (this.getParent() != null)
        {
            this.getParent().render(matrixStack, mouseX, mouseY, partialTicks);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawScreenBackground(int mouseX, int mouseY)
    {
        RenderUtils.drawOutlinedBox(this.dialogLeft, this.dialogTop, this.dialogWidth, this.dialogHeight, 0xFF000000, COLOR_HORIZONTAL_BAR);
    }

    @Override
    protected void drawTitle(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.drawStringWithShadow(matrixStack, this.title, this.dialogLeft + 10, this.dialogTop + 6, COLOR_WHITE);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        return this.onKeyTyped(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == KeyCodes.KEY_ESCAPE && this.dialogHandler != null)
        {
            this.dialogHandler.closeDialog();
            return true;
        }
        else
        {
            return super.onKeyTyped(keyCode, scanCode, modifiers);
        }
    }
}
