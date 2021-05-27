package io.github.seggan.emc2;

import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

@UtilityClass
public class Items {

    public static final Category CATEGORY = new Category(
        EMC2.inst().getKey("emc2_category"),
        new CustomItem(Material.QUARTZ_BRICKS, "Emc²")
    );
}
