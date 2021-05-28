package io.github.seggan.emc2;

import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

@UtilityClass
public class Items {

    public static final Category CATEGORY = new Category(
        EMC2.inst().getKey("emc2_category"),
        new CustomItem(Material.QUARTZ_BRICKS, "Emc²")
    );

    public static final SlimefunItemStack SMALL_CAPACITOR = new SlimefunItemStack(
        "SMALL_QGP_CAPACITOR",
        Material.QUARTZ_BLOCK,
        "&fSmall QGP Capacitor",
        "",
        "&6Stores 1,000 Quark-Gluon Plasma"
    );
}
