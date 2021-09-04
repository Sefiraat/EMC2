package io.github.seggan.emc2.items;

import io.github.seggan.emc2.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class Router extends SlimefunItem {

    public Router() {
        super(Items.CATEGORY, Items.ROUTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.DAMASCUS_STEEL_INGOT,
            Items.QGP_CONTAINMENT_FIELD, Items.QGP_CONTAINMENT_CELL, Items.QGP_CONTAINMENT_FIELD,
            SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.DAMASCUS_STEEL_INGOT
        });

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                Router.this.tick(b);
            }
        });
    }

    private void tick(Block b) {
        long total = 0;
        for (BlockFace face : QGPCapacitor.ADJACENT_BLOCK_FACES) {
            Block cap = b.getRelative(face);
            if (BlockStorage.check(cap) instanceof QGPCapacitor) {
                total += QGPCapacitor.get(cap);
                QGPCapacitor.set(cap, 0);
            }
        }
        QGPCapacitor.distributeAmong(b, total);
    }
}
