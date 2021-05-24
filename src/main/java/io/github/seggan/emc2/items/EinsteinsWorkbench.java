package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class EinsteinsWorkbench extends AbstractContainer {

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 20, 24, 26, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_SLOTS = new int[]{3, 5, 12, 14, 21, 22, 23};
    private static final int[] OUTPUT_SLOTS = new int[]{30, 32, 39, 41, 48, 49, 50};

    private static final int ENABLED_SLOT = 19;
    private static final int AMOUNT_SLOT = 25;

    private static final int COPY_SLOT = 13;
    private static final int ITEM_SLOT = 40;
    private static final int MODE_SLOT = 31;

    private static final ItemStack ENABLED_ITEM = new CustomItem(
        Material.STRUCTURE_VOID,
        "&aEnabled",
        "",
        "&7Click to disable"
    );

    private static final ItemStack DISABLED_ITEM = new CustomItem(
        Material.BARRIER,
        "&cDisabled",
        "",
        "&7Click to enable"
    );

    private static final ItemStack COPY_ITEM = new CustomItem(
        Material.ENCHANTED_BOOK,
        "&fPlace the Copy Item Below"
    );

    private static final ItemStack AMOUNT_ITEM = new CustomItem(
        Material.ENCHANTED_BOOK,
        "&fAmount: 0 EGM"
    );

    public EinsteinsWorkbench(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        return new int[0];
    }

    @Override
    protected void tick(@Nonnull Block b) {

    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int slot : INPUT_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(COPY_SLOT, COPY_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(AMOUNT_SLOT, AMOUNT_ITEM);
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (Boolean.parseBoolean(BlockStorage.getLocationInfo(b.getLocation(), "enabled"))) {
            menu.replaceExistingItem(ENABLED_SLOT, ENABLED_ITEM);
            menu.addMenuClickHandler(ENABLED_SLOT, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, "enabled", Boolean.toString(false));
                this.onNewInstance(menu, b);
                return false;
            });
        } else {
            menu.replaceExistingItem(ENABLED_SLOT, DISABLED_ITEM);
            menu.addMenuClickHandler(ENABLED_SLOT, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, "enabled", Boolean.toString(true));
                this.onNewInstance(menu, b);
                return false;
            });
        }
    }
}
