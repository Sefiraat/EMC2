package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class Rematerializer extends SlimefunItem {

    private static final int[] BACKGROUND = new int[]{0, 8, 9, 13, 17, 18, 22, 26};
    private static final int[] INPUT_BORDER = new int[]{1, 2, 3, 10, 12, 19, 20, 21};
    private static final int[] OUTPUT_BORDER = new int[]{5, 6, 7, 14, 16, 23, 24, 25};

    private static final int ACTION_SLOT = 4;
    private static final int ITEM_SLOT = 11;
    private static final int OUTPUT_SLOT = 15;

    private static final ItemStack INPUT_ITEM = new CustomItemStack(
        Material.BLUE_STAINED_GLASS_PANE,
        "&9Input",
        "",
        "&7Put the item to copy",
        "&7in the adjacent slot"
    );

    private static final ItemStack ACTION_ITEM = new CustomItemStack(
        Material.NETHER_STAR,
        "&6Copy",
        "",
        "&eLeft Click&7 to copy one item into your inventory",
        "&eShift Left Click&7 to copy a stack into your inventory",
        "&eRight Click&7 to copy one item",
        "&eShift Right Click&7 to copy a stack"
    );

    public Rematerializer() {
        super(Items.CATEGORY, Items.REMATERIALIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            Items.QGP_CONTAINMENT_CELL, Items.SUPERCONDUCTING_WIRE, Items.QGP_CONTAINMENT_CELL,
            Items.SUPERCONDUCTING_WIRE, Items.ATOMIZER, Items.SUPERCONDUCTING_WIRE,
            Items.QGP_CONTAINMENT_CELL, Items.SUPERCONDUCTING_WIRE, Items.QGP_CONTAINMENT_CELL
        });

        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                setupMenu(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                menu.addMenuClickHandler(ACTION_SLOT, (p, slot, item1, action) -> {
                    Rematerializer.this.onClick(p, menu, action);
                    return false;
                });
            }

            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") ||
                    Slimefun.getProtectionManager().hasPermission(
                        p,
                        b.getLocation(),
                        Interaction.INTERACT_BLOCK
                    );
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[]{OUTPUT_SLOT};
            }
        };
    }

    private void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int slot : INPUT_BORDER) {
            preset.addItem(slot, INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_BORDER) {
            preset.addItem(slot, MenuBlock.OUTPUT_BORDER, ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(ACTION_SLOT, ACTION_ITEM);
    }

    @ParametersAreNonnullByDefault
    private void onClick(Player p, BlockMenu menu, ClickAction action) {
        ItemStack copyItem = menu.getItemInSlot(ITEM_SLOT);
        if (copyItem == null || copyItem.getType().isAir()) {
            p.sendMessage(ChatColor.RED + "Please input an item to copy");
            return;
        }

        ItemStack item = copyItem.clone();
        item.setAmount(action.isShiftClicked() ? 64 : 1);

        long cost = ItemValues.getInstance().getValue(item, true); // get the cost of the item
        if (cost == 0) {
            p.sendMessage(ChatColor.RED + "This item is not copyable");
            return;
        }

        Block b = menu.getBlock();
        String s = BlockStorage.getLocationInfo(b.getLocation(), "buffer");
        if (s != null) {
            cost -= Long.parseLong(s); // reduce the cost by the buffer
            cost = Math.max(1, cost);
        }

        long taken = QGPCapacitor.removeAmong(b, cost); // take any more qgp needed
        if (taken < cost) { // too little qgp taken
            BlockStorage.addBlockInfo(b, "buffer", Long.toString(taken)); // store to buffer
            p.sendMessage(ChatColor.RED + "Not enough Quark-Gluon Plasma. Please ensure there is at least " +
                "one QGP Capacitor adjacent to the Rematerializer and that they have sufficient Quark-Gluon " +
                "Plasma between them"
            );
            return;
        }

        BlockStorage.addBlockInfo(b, "buffer", Long.toString(0)); // clear buffer on successful copy

        if (action.isRightClicked()) {
            menu.pushItem(item, OUTPUT_SLOT);
        } else {
            Map<Integer, ItemStack> notFit = p.getInventory().addItem(item);
            if (!notFit.isEmpty()) {
                menu.pushItem(item, OUTPUT_SLOT);
            }
        }
    }
}
