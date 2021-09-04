package io.github.seggan.emc2.items;

import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.mooy1.infinitylib.machines.TickingMenuBlock;
import io.github.seggan.emc2.Items;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Dematerializer extends TickingMenuBlock {

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 6, 7, 8};
    private static final int[] BORDER = new int[]{3, 5};
    private static final int INPUT_SLOT = 4;

    public Dematerializer() {
        super(Items.CATEGORY, Items.DEMATERIALIZER, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
            SlimefunItems.FIRE_RUNE, Items.QGP_CONTAINMENT_CELL, SlimefunItems.FIRE_RUNE,
            Items.ROUTER, Items.ATOMIZER, Items.ROUTER,
            SlimefunItems.FIRE_RUNE, Items.QGP_CONTAINMENT_CELL, SlimefunItems.FIRE_RUNE,
        });
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull BlockMenu blockMenu) {
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
            QGPCapacitor.distributeAmong(block, ItemValues.getInstance().getValue(stack, false))
        ));

        blockMenu.consumeItem(INPUT_SLOT, 64);
    }

    @Override
    protected void setup(BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int slot : BORDER) {
            preset.addItem(slot, MenuBlock.INPUT_BORDER, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    protected int[] getInputSlots() {
        return new int[]{INPUT_SLOT};
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }
}
