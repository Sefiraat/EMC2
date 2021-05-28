package io.github.seggan.emc2.items;

import io.github.seggan.emc2.Items;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QGPCapacitor extends SlimefunItem {

    private static final String BLOCK_STORAGE_KEY = "qgp";

    public static final BlockFace[] ADJACENT_BLOCK_FACES = new BlockFace[]{
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.EAST,
        BlockFace.WEST
    };

    @Getter
    private final long capacity;

    public QGPCapacitor(SlimefunItemStack item, ItemStack[] recipe, long capacity) {
        super(Items.CATEGORY, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.capacity = capacity;
    }

    public static long get(@Nonnull Block b) {
        String s = BlockStorage.getLocationInfo(b.getLocation(), BLOCK_STORAGE_KEY);
        if (s == null) {
            BlockStorage.addBlockInfo(b, BLOCK_STORAGE_KEY, Long.toString(0));
            return 0;
        }

        return Long.parseLong(s);
    }

    public static void set(@Nonnull Block b, long amount) {
        BlockStorage.addBlockInfo(b, BLOCK_STORAGE_KEY, Long.toString(amount));
    }

    public void add(@Nonnull Block b, long amount) {
        long initialAmount = QGPCapacitor.get(b);
        long newAmount = amount + initialAmount;
        newAmount = Math.min(0, Math.max(newAmount, capacity));
        QGPCapacitor.set(b, newAmount);
    }

    /**
     * Distributes Quark-Gluon Plasma evenly among capacitors adjacent to the source block
     *
     * @param sourceBlock the central block that distributes
     * @param amount the amount to distribute
     *
     * @return the Quark-Gluon Plasma that couldn't fit, if any
     */
    public static long distributeAmong(@Nonnull Block sourceBlock, long amount) {
        List<Block> caps = new ArrayList<>();
        for (BlockFace face : ADJACENT_BLOCK_FACES) {
            Block b = sourceBlock.getRelative(face);
            if (BlockStorage.check(b) instanceof QGPCapacitor) {
                caps.add(b);
            }
        }

        return distributeAmong(caps, amount);
    }

    private static long distributeAmong(@Nonnull List<Block> blocks, long amount) {
        if (blocks.isEmpty()) return amount;

        long splitAmount = amount / blocks.size();
        long extra = amount % blocks.size();
        long overflow = 0;

        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block b = iterator.next();
            QGPCapacitor capacitor = (QGPCapacitor) BlockStorage.check(b); // guaranteed cast
            assert capacitor != null; // will not fail
            long capacity = capacitor.getCapacity();

            long storedAmount = QGPCapacitor.get(b);
            long newAmount = storedAmount + splitAmount;
            if (newAmount > capacity) {
                iterator.remove();
                QGPCapacitor.set(b, capacity);
                overflow += newAmount - capacity;
            } else {
                long adjustedAmount = newAmount;
                if (extra > 0) {
                    adjustedAmount += extra;
                }
                if (adjustedAmount > capacity) {
                    QGPCapacitor.set(b, capacity);
                } else {
                    extra = 0;
                    QGPCapacitor.set(b, adjustedAmount);
                }
            }
        }

        overflow += extra;

        if (overflow == 0) return 0;

        return distributeAmong(blocks, overflow);
    }

    /**
     * Removes {@code amount} Quark-Gluon Plasma equally from capacitors adjacent to the source block
     *
     * @param sourceBlock the block to remove around
     * @param amount the amount ot remove
     *
     * @return the amount removed, always less than or equal to {@code amount}
     */
    public static long removeAmong(Block sourceBlock, long amount) {
        List<Block> caps = new ArrayList<>();
        for (BlockFace face : ADJACENT_BLOCK_FACES) {
            Block b = sourceBlock.getRelative(face);
            if (BlockStorage.check(b) instanceof QGPCapacitor) {
                caps.add(b);
            }
        }

        return amount - removeAmong(caps, amount);
    }

    private static long removeAmong(List<Block> blocks, long amount) {
        if (blocks.isEmpty()) return amount;

        long splitAmount = amount / blocks.size();
        long extra = amount % blocks.size();
        long underflow = 0;

        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block b = iterator.next();

            long newAmount = QGPCapacitor.get(b) - splitAmount;

            if (newAmount < 0) {
                iterator.remove();
                QGPCapacitor.set(b, 0);
                underflow += -newAmount;
            } else {
                long adjustedAmount = newAmount;
                if (extra > 0) {
                    adjustedAmount -= extra;
                }
                if (adjustedAmount < 0) {
                    QGPCapacitor.set(b, newAmount);
                } else {
                    extra = 0;
                    QGPCapacitor.set(b, adjustedAmount);
                }
            }
        }

        underflow += extra;

        if (underflow == 0) return 0;

        return removeAmong(blocks, underflow);
    }
}
