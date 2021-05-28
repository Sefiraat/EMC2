package io.github.seggan.emc2;

import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class Setup {

    public static void setup(EMC2 addon) {
        new SlimefunItem(Items.CATEGORY, Items.SMALL_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9])
            .register(addon);
    }
}
