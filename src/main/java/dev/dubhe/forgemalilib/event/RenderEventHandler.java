package dev.dubhe.forgemalilib.event;

import java.util.ArrayList;
import java.util.List;

import dev.dubhe.forgemalilib.interfaces.IRenderDispatcher;
import dev.dubhe.forgemalilib.interfaces.IRenderer;
import dev.dubhe.forgemalilib.util.InfoUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

public class RenderEventHandler implements IRenderDispatcher
{
    private static final RenderEventHandler INSTANCE = new RenderEventHandler();

    private final List<IRenderer> overlayRenderers = new ArrayList<>();
    private final List<IRenderer> tooltipLastRenderers = new ArrayList<>();
    private final List<IRenderer> worldLastRenderers = new ArrayList<>();

    public static IRenderDispatcher getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerGameOverlayRenderer(IRenderer renderer)
    {
        if (this.overlayRenderers.contains(renderer) == false)
        {
            this.overlayRenderers.add(renderer);
        }
    }

    @Override
    public void registerTooltipLastRenderer(IRenderer renderer)
    {
        if (this.tooltipLastRenderers.contains(renderer) == false)
        {
            this.tooltipLastRenderers.add(renderer);
        }
    }

    @Override
    public void registerWorldLastRenderer(IRenderer renderer)
    {
        if (this.worldLastRenderers.contains(renderer) == false)
        {
            this.worldLastRenderers.add(renderer);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderGameOverlayPost(PoseStack matrixStack, Minecraft mc, float partialTicks)
    {
        mc.getProfiler().push("forgemalilib_rendergameoverlaypost");

        if (this.overlayRenderers.isEmpty() == false)
        {
            for (IRenderer renderer : this.overlayRenderers)
            {
                mc.getProfiler().push(renderer.getProfilerSectionSupplier());
                renderer.onRenderGameOverlayPost(matrixStack);
                mc.getProfiler().pop();
            }
        }

        mc.getProfiler().push("forgemalilib_ingamemessages");
        InfoUtils.renderInGameMessages(matrixStack);
        mc.getProfiler().pop();

        mc.getProfiler().pop();
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderTooltipLast(PoseStack matrixStack, ItemStack stack, int x, int y)
    {
        if (this.tooltipLastRenderers.isEmpty() == false)
        {
            for (IRenderer renderer : this.tooltipLastRenderers)
            {
                renderer.onRenderTooltipLast(stack, x, y);
            }
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderWorldLast(PoseStack matrixStack, Matrix4f projMatrix, Minecraft mc)
    {
        if (this.worldLastRenderers.isEmpty() == false)
        {
            mc.getProfiler().popPush("forgemalilib_renderworldlast");

            RenderTarget fb = Minecraft.useShaderTransparency() ? mc.levelRenderer.getTranslucentTarget() : null;

            if (fb != null)
            {
                fb.bindWrite(false);
            }

            for (IRenderer renderer : this.worldLastRenderers)
            {
                mc.getProfiler().push(renderer.getProfilerSectionSupplier());
                renderer.onRenderWorldLast(matrixStack, projMatrix);
                mc.getProfiler().pop();
            }

            if (fb != null)
            {
                mc.getMainRenderTarget().bindWrite(false);
            }
        }
    }
}
