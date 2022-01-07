package dev.dubhe.forgemalilib.mixin;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModLoader.class)
public interface MixinModLoader {
    @Accessor
    ModList getModList();
}
