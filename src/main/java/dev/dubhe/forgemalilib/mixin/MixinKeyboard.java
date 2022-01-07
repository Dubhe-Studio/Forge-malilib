package dev.dubhe.forgemalilib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.dubhe.forgemalilib.event.InputEventHandler;
import dev.dubhe.forgemalilib.util.IF3KeyStateSetter;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;

@Mixin(KeyboardHandler.class)
public abstract class MixinKeyboard implements IF3KeyStateSetter
{
    @Shadow
    @Final
    private Minecraft client;

    @Shadow
    private boolean switchF3State;

    @Override
    public void setF3KeyState(boolean value)
    {
        this.switchF3State = value;
    }

    @Inject(method = "keyPress", cancellable = true,
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/KeyboardHandler;debugCrashKeyTime:J", ordinal = 0))
    private void onKeyboardInput(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci)
    {
        if (((InputEventHandler) InputEventHandler.getInputManager()).onKeyInput(key, scanCode, modifiers, action != 0))
        {
            ci.cancel();
        }
    }
}
