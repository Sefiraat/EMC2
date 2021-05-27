package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.TickingContainer;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Dematerializer extends TickingContainer {

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 6, 7, 8};
    private static final int[] BORDER = new int[]{3, 5};
    private static final int INPUT_SLOT = 4;

    public Dematerializer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Config config) {
        ItemStack stack = blockMenu.getItemInSlot(INPUT_SLOT);
        if (stack == null || stack.getType().isAir()) return;

        Capacitor.distributeAmong(block, ItemValues.getInstance().getValue(stack));
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        return new int[]{INPUT_SLOT};
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int slot : BORDER) {
            preset.addItem(slot, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
    }
}
