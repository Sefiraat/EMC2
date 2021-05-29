package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public class Materializer extends SlimefunItem {

    private static final int[] BACKGROUND = new int[]{0, 8, 9, 13, 17, 18, 22, 26};
    private static final int[] INPUT_BORDER = new int[]{1, 2, 3, 10, 12, 19, 20, 21};
    private static final int[] OUTPUT_BORDER = new int[]{5, 6, 7, 14, 16, 23, 24, 25};

    private static final int ACTION_SLOT = 4;
    private static final int ITEM_SLOT = 10;
    private static final int OUTPUT_SLOT = 15;

    private static final ItemStack INPUT_ITEM = new CustomItem(
        Material.BLUE_STAINED_GLASS_PANE,
        "&9Input",
        "",
        "&7Put the item to copy",
        "&7in the adjacent slot"
    );

    private static final ItemStack ACTION_ITEM = new CustomItem(
        Material.NETHER_STAR,
        "&6Copy",
        "",
        "&eLeft Click&7 to copy one item into your inventory",
        "&eShift Left Click&7 to copy a stack into your inventory",
        "&eRight Click&7 to copy one item",
        "&eShift Right Click&7 to copy a stack"
    );

    public Materializer() {
        super(Items.CATEGORY, Items.MATERIALIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9]);

        new BlockMenuPreset(this.getId(), this.getItemName()) {

            @Override
            public void init() {
                setupMenu(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                menu.addMenuClickHandler(ACTION_SLOT, (p, slot, item1, action) -> {
                    Materializer.this.onClick(p, menu, action);
                    return false;
                });
            }

            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") ||
                    SlimefunPlugin.getProtectionManager().hasPermission(
                        p,
                        b.getLocation(),
                        ProtectableAction.INTERACT_BLOCK
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
            preset.addItem(slot, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
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

        long cost = ItemValues.getInstance().getValue(item);
        if (cost == 0) {
            p.sendMessage(ChatColor.RED + "This item is not copyable");
            return;
        }

        Block b = menu.getBlock();
        String s = BlockStorage.getLocationInfo(b.getLocation(), "buffer");
        if (s != null) {
            cost -= Long.parseLong(s);
            cost = Math.max(1, cost);
        }

        long taken = QGPCapacitor.removeAmong(b, cost);
        if (taken < cost) {
            BlockStorage.addBlockInfo(b, "buffer", Long.toString(taken));
            p.sendMessage(ChatColor.RED + "Not enough Quark-Gluon Plasma. Please ensure there is at least " +
                "one QGP Capacitor adjacent to the Materializer and that they have sufficient Quark-Gluon " +
                "Plasma between them"
            );
            return;
        }

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
