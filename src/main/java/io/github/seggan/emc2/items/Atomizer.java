package io.github.seggan.emc2.items;

import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public class Atomizer extends SlimefunItem {

    public Atomizer() {
        super(Items.CATEGORY, Items.ATOMIZER, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
            SlimefunItems.AIR_RUNE, Items.QGP_CONTAINMENT_FIELD, SlimefunItems.WATER_RUNE,
            Items.QGP_CONTAINMENT_FIELD, Items.QGP_CONTAINMENT_CELL, Items.QGP_CONTAINMENT_FIELD,
            SlimefunItems.EARTH_RUNE, Items.QGP_CONTAINMENT_FIELD, SlimefunItems.FIRE_RUNE
        });

        addItemHandler(onRightClick());
    }

    private BlockUseHandler onRightClick() {
        return e -> {
            ItemStack item = e.getItem();
            if (item.getType().isAir()) return;

            e.getPlayer().sendMessage(String.format(
                "This item is worth %d Quark-Gluon Plasma",
                ItemValues.getInstance().getValue(item)
            ));
        };
    }
}
