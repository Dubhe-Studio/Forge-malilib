package dev.dubhe.forgemalilib.mixin;

import javax.annotation.Nullable;

import dev.dubhe.forgemalilib.network.ClientPacketChannelHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.dubhe.forgemalilib.event.WorldLoadHandler;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPlayNetworkHandler
{
    @Shadow @Final private Minecraft minecraft;
    @Shadow private ClientLevel level;

    @Nullable private ClientLevel levelBefore;

    @Inject(method = "handleLogin", at = @At("HEAD"))
    private void onPreJoinGameHead(ClientboundLoginPacket packet, CallbackInfo ci)
    {
        // Need to grab the old level reference at the start of the method,
        // because the next injection point is right after the level has been assigned,
        // since we need the new level reference for the callback.
        this.levelBefore = this.level;
    }

    @Inject(method = "handleLogin", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V"))
    private void onPreGameJoin(ClientboundLoginPacket packet, CallbackInfo ci)
    {
        ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPre(this.levelBefore, this.level, this.minecraft);
    }

    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void onPostGameJoin(ClientboundLoginPacket packet, CallbackInfo ci)
    {
        ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPost(this.levelBefore, this.level, this.minecraft);
        this.levelBefore = null;
    }

    @Inject(method = "handleCustomPayload", cancellable = true,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;getIdentifier()Lnet/minecraft/resources/ResourceLocation;"))
    private void onCustomPayload(ClientboundCustomPayloadPacket packet, CallbackInfo ci)
    {
        if (((ClientPacketChannelHandler) ClientPacketChannelHandler.getInstance()).processPacketFromServer(packet, (ClientPacketListener)(Object) this))
        {
            ci.cancel();
        }
    }
}
