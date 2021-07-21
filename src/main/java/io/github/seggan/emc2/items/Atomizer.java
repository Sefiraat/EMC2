package io.github.seggan.emc2.items;

import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.ChatColor;
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
            e.cancel();

            ItemStack item = e.getItem();
            if (item.getType().isAir()) return;

            long value = ItemValues.getInstance().getValue(item, false);

            if (value > 0) {
                e.getPlayer().sendMessage(String.format(
                    "This %s" + ChatColor.RESET + " is worth %d Quark-Gluon Plasma in the Dematerializer",
                    ItemUtils.getItemName(item),
                    value
                ));

                e.getPlayer().sendMessage(String.format(
                    "This %s" + ChatColor.RESET + " costs %d Quark-Gluon Plasma in the Rematerializer",
                    ItemUtils.getItemName(item),
                    ItemValues.getInstance().getValue(item, true)
                ));
            } else {
                e.getPlayer().sendMessage(String.format(
                    "This %s" + ChatColor.RESET + " is not copyable",
                    ItemUtils.getItemName(item)
                ));
            }
        };
    }
}
