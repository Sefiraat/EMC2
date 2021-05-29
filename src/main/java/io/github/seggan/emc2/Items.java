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

    public static final SlimefunItemStack MEDIUM_CAPACITOR = new SlimefunItemStack(
        "MEDIUM_QGP_CAPACITOR",
        Material.QUARTZ_BLOCK,
        "&fMedium QGP Capacitor",
        "",
        "&6Stores 50,000 Quark-Gluon Plasma"
    );

    public static final SlimefunItemStack LARGE_CAPACITOR = new SlimefunItemStack(
        "LARGE_QGP_CAPACITOR",
        Material.QUARTZ_BLOCK,
        "&fLarge QGP Capacitor",
        "",
        "&6Stores 2,500,000 Quark-Gluon Plasma"
    );

    public static final SlimefunItemStack DEMATERIALIZER = new SlimefunItemStack(
        "DEMATERIALIZER",
        Material.QUARTZ_BRICKS,
        "&fDematerializer",
        "",
        "&7Disintegrates items into Quark-Gluon Plasma"
    );

    public static final SlimefunItemStack MATERIALIZER = new SlimefunItemStack(
        "MATERIALIZER",
        Material.QUARTZ_PILLAR,
        "&fMaterializer",
        "",
        "&7Copies an item using Quark-Gluon Plasma"
    );

    public static final SlimefunItemStack ROUTER = new SlimefunItemStack(
        "QGP_ROUTER",
        Material.CHISELED_QUARTZ_BLOCK,
        "&fQGP Router",
        "",
        "&7Attempts to evenly distribute",
        "&7Quark-Gluon Plasma among adjacent capacitors.",
        "&7Can be used for making storage cells or wires"
    );
}
