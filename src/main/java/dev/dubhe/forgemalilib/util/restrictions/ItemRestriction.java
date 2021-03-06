package dev.dubhe.forgemalilib.util.restrictions;

import java.util.List;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import dev.dubhe.forgemalilib.ForgeMaliLib;
import dev.dubhe.forgemalilib.util.StringUtils;

public class ItemRestriction extends UsageRestriction<Item>
{
    @Override
    protected void setValuesForList(Set<Item> set, List<String> names)
    {
        for (String name : names)
        {
            ResourceLocation rl = null;

            try
            {
                rl = new ResourceLocation(name);
            }
            catch (Exception e)
            {
            }

            Item item = rl != null ? Registry.ITEM.get(rl) : null;

            if (item != null)
            {
                set.add(item);
            }
            else
            {
                ForgeMaliLib.logger.warn(StringUtils.translate("forgemalilib.error.invalid_item_blacklist_entry", name));
            }
        }
    }
}
