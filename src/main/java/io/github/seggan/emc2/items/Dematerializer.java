package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.TickingContainer;
import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dematerializer extends TickingContainer implements RecipeDisplayItem {

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 6, 7, 8};
    private static final int[] BORDER = new int[]{3, 5};
    private static final int INPUT_SLOT = 4;

    private static final List<ItemStack> recipeItems = new ArrayList<>();

    static {
        for (Map.Entry<Material, Long> item : ItemValues.getInstance().getValues().entrySet()) {
            ItemStack stack = new ItemStack(item.getKey());
            recipeItems.add(new CustomItem(
                stack,
                stack.getItemMeta().getDisplayName(),
                "",
                "&e" + item.getValue() + " Quark-Gluon Plasma"
            ));
        }

        for (SlimefunItem slimefunItem : SlimefunPlugin.getRegistry().getEnabledSlimefunItems()) {
            ItemStack stack = slimefunItem.getItem();

        }
    }

    public Dematerializer() {
        super(Items.CATEGORY, Items.DEMATERIALIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9]);
    }

    @Override
    protected void tick(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Config config) {
        ItemStack stack = blockMenu.getItemInSlot(INPUT_SLOT);
        if (stack == null || stack.getType().isAir()) return;

        String s = BlockStorage.getLocationInfo(block.getLocation(), "buffer");
        if (s != null) {
            long buff = Long.parseLong(s);
            if (buff > 0) {
                long overflow = QGPCapacitor.distributeAmong(block, buff);
                BlockStorage.addBlockInfo(block, "buffer", Long.toString(overflow), true);
                return;
            }
        }

        BlockStorage.addBlockInfo(block, "buffer", Long.toString(
            QGPCapacitor.distributeAmong(block, ItemValues.getInstance().getValue(stack))
        ));

        blockMenu.consumeItem(INPUT_SLOT, 64);
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

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return recipeItems;
    }
}
