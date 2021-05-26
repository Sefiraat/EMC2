package io.github.seggan.emc2;

import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Tag;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

public class ItemValues {

    private static final ItemValues INSTANCE = new ItemValues();

    @Getter
    private final Map<Material, Long> values = new EnumMap<>(Material.class);

    private ItemValues() {
        // region wood/stone
        add(3, "STONE");
        add(4, "POLISHED");
        add(4, "BLACKSTONE");
        add(2, "SANDSTONE");
        add(1, "COBBLESTONE");
        add(4, "BRICKS");
        add(3, "BRICK");
        add(4, "BASALT");
        add(1, Tag.PLANKS);
        add(4, Tag.LOGS);
        add(1, Tag.BUTTONS);
        add(3, Tag.SLABS);
        add(5, Tag.STAIRS);
        add(4, Tag.SIGNS);
        add(4, Tag.FENCES);
        add(5, Tag.WALLS);
        add(3, Tag.DOORS);
        add(4, Tag.TRAPDOORS);
        add(4, Tag.FENCE_GATES);
        add(2, Tag.PRESSURE_PLATES);
        add(2, Tag.ITEMS_BOATS);
        add(1, "TORCH");
        add(2, SlimefunTag.STONE_VARIANTS);
        add(1, Material.NETHERRACK, Material.CRIMSON_NYLIUM, Material.WARPED_NYLIUM);
        add(15, "END_STONE");
        add(5, "STRIPPED");
        // endregion

        add(15, "CORAL");

        // region misc hard blocks
        add(2, SlimefunTag.ICE_VARIANTS);
        add(1, Tag.BAMBOO_PLANTABLE_ON);
        add(1, SlimefunTag.DIRT_VARIANTS);
        add(5, SlimefunTag.TERRACOTTA);
        add(8, "GLAZED");
        add(8, "CONCRETE");
        add(20, "PURPUR");
        add(8, "QUARTZ");
        add(25, "PRISMARINE");
        add(3, Material.NETHER_WART_BLOCK, Material.WARPED_WART_BLOCK);
        add(7, Material.SHROOMLIGHT, Material.GLOWSTONE);
        add(35, Material.SPONGE, Material.WET_SPONGE);
        add(15, "CORAL_BLOCK");
        add(15, "OBSIDIAN");
        // endregion

        // region ores
        add(3, Material.COAL, Material.COAL_ORE);
        add(8, Material.IRON_INGOT, Material.IRON_ORE);
        add(1, Material.IRON_NUGGET);
        add(12, Material.GOLD_INGOT, Material.GOLD_ORE);
        add(2, Material.GOLD_NUGGET);
        add(15, Material.EMERALD, Material.EMERALD_ORE);
        add(25, Material.DIAMOND, Material.DIAMOND_ORE);
        add(30, Material.NETHERITE_SCRAP, Material.ANCIENT_DEBRIS);
        add(50, Material.NETHERITE_INGOT, Material.NETHERITE_HOE, Material.NETHERITE_SHOVEL);
        // endregion

        // region food/plants
        add(1, Tag.FLOWERS);
        add(5, "VINES");
        add(5, SlimefunTag.MUSHROOMS);
        add(3, Tag.SAPLINGS);
        add(3, Tag.LEAVES);
        add(5, "POTTED");
        add(3, "COOKED");
        add(4, Tag.ITEMS_FISHES);
        add(8, "BUCKET");
        add(5, "RABBIT");
        add(3, "BEETROOT");
        add(20, "GOLDEN");
        add(30, Material.GLISTERING_MELON_SLICE);
        add(50, Material.ENCHANTED_GOLDEN_APPLE);
        // endregion

        // region colors
        add(1, Tag.WOOL);
        add(20, Tag.SHULKER_BOXES);
        add(3, Tag.BEDS);
        add(2, Tag.CARPETS);
        add(5, Tag.BANNERS);
        add(5, Tag.ITEMS_BANNERS);
        add(40, "BANNER_PATTERN");
        add(2, SlimefunTag.GLASS);
        add(1, "_DYE");
        // endregion

        // region crafting
        add(5, "TABLE");
        add(5, Material.FURNACE);
        add(13, "ANVIL");
        add(5, "CHEST");
        // endregion

        add(15, "ENDER");
        add(20, Tag.ITEMS_MUSIC_DISCS);
        add(4, Tag.ITEMS_ARROWS);
        add(6, "MINECART");
        add(6, "RAIL");
        add(15, "POTION");
        add(8, "BOOK");
        add(20, Material.ENCHANTED_BOOK);
        add(30, "HEAD");
        add(50, "SKULL");
    }

    private void add(long amount, Material... materials) {
        for (Material material : materials) {
            values.put(material, amount);
        }
    }

    private void add(long amount, Tag<Material> tag) {
        for (Material material : tag.getValues()) {
            values.put(material, amount);
        }
    }

    private void add(long amount, String s) {
        for (Material material : Material.values()) {
            String name = material.name();
            if (name.startsWith("LEGACY_")) continue;

            if (name.contains(s)) {
                values.put(material, amount);
            }
        }
    }

    @Nonnull
    public static ItemValues getInstance() {
        return INSTANCE;
    }

    // does nothing, forces init of static field
    static void setup() {
    }
}
