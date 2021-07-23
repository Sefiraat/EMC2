package io.github.seggan.emc2.qgp;

import io.github.sefiraat.slimetinker.items.templates.ToolTemplate;
import io.github.seggan.emc2.EMC2;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class ItemValues {

    private static final ItemValues INSTANCE = new ItemValues();

    @Getter
    private final Map<Material, Long> values = new EnumMap<>(Material.class);

    private final Map<String, Long> overrides = new HashMap<>();

    private final Map<Enchantment, Long> enchants = new HashMap<>();

    private ItemValues() {
        Arrays.stream(Material.values()).filter(Material::isItem)
            .forEach(m -> values.put(m, 8L)); // implicitly sets values for non-specified items

        // region wood/stone
        add(3, "STONE");
        add(2, "SANDSTONE");
        add(1, "COBBLESTONE");
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
        add(4, Material.LADDER);
        add(4, "WOODEN");
        add(8, Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_SWORD);
        add(1, "TORCH");
        add(2, Material.STICK);
        add(2, SlimefunTag.STONE_VARIANTS);
        add(1, Material.NETHERRACK, Material.CRIMSON_NYLIUM, Material.WARPED_NYLIUM);
        add(5, "STRIPPED");
        add(4, "POLISHED");
        add(8, "BLACKSTONE");
        add(8, "BRICKS");
        add(5, "BRICK");
        add(10, "BASALT");
        // endregion

        add(15, "CORAL");

        // region misc hard blocks
        add(2, SlimefunTag.ICE_VARIANTS);
        add(1, Tag.BAMBOO_PLANTABLE_ON);
        add(1, SlimefunTag.DIRT_VARIANTS);
        add(5, SlimefunTag.TERRACOTTA);
        add(8, "GLAZED");
        add(8, "CONCRETE");
        add(25, "PRISMARINE");
        add(3, Material.NETHER_WART_BLOCK, Material.WARPED_WART_BLOCK);
        add(7, Material.SHROOMLIGHT, Material.GLOWSTONE);
        add(35, Material.SPONGE, Material.WET_SPONGE);
        add(15, "CORAL_BLOCK");
        add(15, "OBSIDIAN");
        // endregion

        // region ores/tools
        add(3, Material.COAL, Material.CHARCOAL, Material.COAL_ORE);
        add(27, Material.COAL_BLOCK);

        add(5, "LEATHER");

        add(8, Material.IRON_INGOT, Material.IRON_ORE, Material.IRON_HOE, Material.IRON_SHOVEL,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS,
            Material.CHAINMAIL_BOOTS, Material.CHAIN);
        add(1, Material.IRON_NUGGET);
        add(72, Material.IRON_BLOCK);
        add(40, Material.IRON_AXE, Material.IRON_PICKAXE, Material.IRON_HELMET, Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.IRON_SWORD);

        add(12, Material.GOLD_INGOT, Material.GOLD_ORE, Material.NETHER_GOLD_ORE, Material.GOLDEN_HOE, Material.GOLDEN_SHOVEL);
        add(1, Material.GOLD_NUGGET);
        add(108, Material.GOLD_BLOCK);
        add(30, Material.GOLDEN_AXE, Material.GOLDEN_PICKAXE, Material.GOLDEN_HELMET,
            Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS, Material.GOLDEN_SWORD);

        add(13, Material.LAPIS_ORE, Material.LAPIS_LAZULI);
        add(117, Material.LAPIS_BLOCK);

        add(15, Material.EMERALD, Material.EMERALD_ORE);
        add(135, Material.EMERALD_BLOCK);

        add(25, Material.DIAMOND, Material.DIAMOND_ORE, Material.DIAMOND_HOE, Material.DIAMOND_SHOVEL);
        add(225, Material.DIAMOND_BLOCK);
        add(125, Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
            Material.DIAMOND_SWORD);

        add(30, Material.NETHERITE_SCRAP, Material.ANCIENT_DEBRIS);
        add(50, Material.NETHERITE_INGOT, Material.NETHERITE_HOE, Material.NETHERITE_SHOVEL);
        add(450, Material.NETHERITE_BLOCK);
        add(175, Material.NETHERITE_AXE, Material.NETHERITE_PICKAXE, Material.NETHERITE_HELMET,
            Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS,
            Material.NETHERITE_SWORD);

        add(15, Material.CROSSBOW, Material.BOW, Material.SHIELD, Material.FISHING_ROD, Material.SHEARS);
        add(17, Material.CARROT_ON_A_STICK, Material.WARPED_FUNGUS_ON_A_STICK);
        add(4, Tag.ITEMS_ARROWS);
        add(25, Material.TRIDENT);
        add(100, Material.ELYTRA);
        add(200, Material.TOTEM_OF_UNDYING);

        add(30, Material.TURTLE_HELMET, Material.TURTLE_EGG, Material.SCUTE);

        add(40, Material.IRON_HORSE_ARMOR, Material.DIAMOND_HORSE_ARMOR);
        add(30, Material.LEATHER_HORSE_ARMOR, Material.LEAD, Material.SADDLE, Material.NAME_TAG);

        add(15, "POTION");
        add(8, "BOOK");
        add(20, Material.ENCHANTED_BOOK);
        // endregion

        // region food/plants
        Arrays.stream(Material.values()).filter(Material::isEdible).forEach(m -> values.put(m, 2L));
        add(1, Tag.FLOWERS);
        add(5, "VINE");
        add(8, "MUSHROOM");
        add(5, SlimefunTag.MUSHROOMS);
        add(3, Tag.SAPLINGS);
        add(3, Tag.LEAVES);
        add(5, "POTTED");
        add(4, Material.FLOWER_POT);
        add(5, Material.KELP, Material.FERN, Material.CACTUS, Material.SUGAR_CANE, Material.GRASS,
            Material.TALL_GRASS, Material.LARGE_FERN, Material.WHEAT, Material.SEAGRASS, Material.TALL_SEAGRASS,
            Material.SEA_PICKLE, Material.NETHER_SPROUTS, Material.DEAD_BUSH, Material.LILY_PAD);
        add(3, "COOKED");
        add(4, Tag.ITEMS_FISHES);
        add(8, "BUCKET");
        add(1, "SEEDS");
        add(5, "RABBIT");
        add(3, "BEETROOT");
        add(20, "GOLDEN");
        add(10, "PUMPKIN");
        add(10, Material.MELON);
        add(30, Material.GLISTERING_MELON_SLICE);
        add(75, Material.ENCHANTED_GOLDEN_APPLE);
        // endregion

        // region colors
        add(1, Tag.WOOL);
        add(35, Tag.SHULKER_BOXES);
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
        add(10, Material.SMOKER, Material.BLAST_FURNACE);
        add(10, "CAMPFIRE");
        add(20, "ANVIL");
        add(5, "CHEST");
        add(5, Material.BARREL);
        add(20, Material.CAULDRON);
        add(15, Material.BREWING_STAND);
        add(7, Material.LOOM, Material.LECTERN, Material.COMPOSTER);
        // endregion

        // region redstone
        add(3, Material.REDSTONE, Material.REPEATER, Material.REDSTONE_TORCH, Material.COMPARATOR,
            Material.NOTE_BLOCK, Material.REDSTONE_ORE, Material.LEVER);
        add(5, Material.DROPPER, Material.DISPENSER, Material.TARGET, Material.DAYLIGHT_DETECTOR,
            Material.OBSERVER, Material.TRIPWIRE_HOOK);
        add(8, "PISTON");
        add(10, Material.BELL, Material.HOPPER, Material.TNT);
        add(27, Material.REDSTONE_BLOCK, Material.JUKEBOX);
        add(20, Tag.ITEMS_MUSIC_DISCS);

        add(8, "MINECART");
        add(6, "RAIL");
        // endregion

        // region nether
        add(8, "QUARTZ");
        add(10, "SOUL");
        add(10, "ROOTS");
        add(20, Material.RESPAWN_ANCHOR);
        add(10, Material.MAGMA_CREAM, Material.BLAZE_ROD, Material.FIRE_CHARGE, Material.BLAZE_POWDER);
        add(25, Material.GHAST_TEAR);
        add(100, Material.NETHER_STAR);
        add(500, Material.BEACON);
        add(15, Material.MAGMA_BLOCK, Material.NETHER_WART);
        // endregion

        // region end
        add(15, "ENDER");
        add(30, Material.END_ROD);
        add(25, Material.SHULKER_SHELL);
        add(25, "CHORUS");
        add(35, Material.END_CRYSTAL);
        add(100_000, Material.DRAGON_EGG);
        add(100, Material.DRAGON_BREATH);
        add(15, "END_STONE");
        add(20, "PURPUR");
        // endregion

        // region loot
        add(5, Material.ROTTEN_FLESH, Material.GUNPOWDER, Material.FEATHER, Material.INK_SAC,
            Material.BONE, Material.EGG, Material.STRING);
        add(2, Material.BONE_MEAL);
        add(15, Material.SLIME_BALL, Material.COBWEB, Material.PHANTOM_MEMBRANE, Material.SPIDER_EYE);
        add(25, Material.EXPERIENCE_BOTTLE, Material.FERMENTED_SPIDER_EYE);
        add(30, "HEAD");
        add(30, "SKULL");
        add(135, Material.SLIME_BLOCK);
        add(15, Material.BONE_BLOCK);
        add(40, Material.HEART_OF_THE_SEA, Material.NAUTILUS_SHELL);
        add(360, Material.CONDUIT);
        add(100, Material.SPAWNER);
        // endregion

        add(15, Material.IRON_BARS);
        add(15, "LANTERN");
        add(4, "SNOW");
        add(20, "BEE");
        add(20, "HONEY");
        add(10, "MAP");
        add(45, Material.DRIED_KELP_BLOCK);
        add(5, Material.DRIED_KELP);

        Arrays.stream(Material.values()).filter(SlimefunTag.UNBREAKABLE_MATERIALS::isTagged)
            .forEach(m -> values.put(m, 500_000L));

        overrides.put(SlimefunItems.ALUMINUM_DUST.getItemId(), 2L);
        overrides.put("VOID_BIT", 32L);
        overrides.put("SEGGANESSON", 50L);
        overrides.put("OSMIUM", 45L);

        enchants.put(Enchantment.DAMAGE_ALL, 40L);
        enchants.put(Enchantment.CHANNELING, 40L);
        enchants.put(Enchantment.DURABILITY, 32L);
        enchants.put(Enchantment.LOYALTY, 40L);
        enchants.put(Enchantment.MENDING, 40L);
        enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 32L);
        enchants.put(Enchantment.SILK_TOUCH, 40L);
    }

    @Nonnull
    public static ItemValues getInstance() {
        return INSTANCE;
    }

    // does nothing, forces init of static field
    public static void setup() {
    }

    public long getValue(ItemStack stack, boolean rematerializing) {
        if (SlimefunGuide.isGuideItem(stack)) return 0;

        long value = 0;

        if (stack.getType() == Material.SPAWNER) return 150;

        SlimefunItem slimefunItem = SlimefunItem.getByItem(stack);
        if (slimefunItem != null && !slimefunItem.isUseableInWorkbench()) {
            if (slimefunItem.getAddon().equals(EMC2.inst())) return 0;
            if (EMC2.inst().isSlimeTinkerInstalled() && ToolTemplate.isTool(stack)) return 0;

            Map<ItemStack, Long> calc = Calculator.calculate(slimefunItem, stack.getAmount());
            for (ItemStack i : calc.keySet()) {
                SlimefunItem item = SlimefunItem.getByItem(i);
                if (item == null) {
                    calc.put(i, values.getOrDefault(i.getType(), 0L) * calc.get(i));
                } else {
                    if (item.getRecipeType().equals(RecipeType.GEO_MINER)) {
                        calc.put(i, 30L);
                    }
                    calc.put(i, overrides.getOrDefault(item.getId(), calc.get(i)));
                }
            }

            value = calc.values().stream().mapToLong(Long::longValue).sum();
        } else {
            value = values.getOrDefault(stack.getType(), 0L) * stack.getAmount();
        }

        if (rematerializing) {
            double percent = EMC2.inst().getConfig().getDouble("qgp.extra-burn", 20) / 100D;
            value += value * percent;
        }

        if (stack.hasItemMeta()) {
            for (Map.Entry<Enchantment, Integer> entry : stack.getItemMeta().getEnchants().entrySet()) {
                long enchVal = enchants.getOrDefault(entry.getKey(), 30L);
                if (rematerializing) {
                    long exponent = 1;
                    for (int i = 0; i < entry.getValue(); i++) {
                        exponent *= 2;
                    }
                    enchVal *= exponent;
                } else {
                    enchVal *= entry.getValue();
                }

                value += enchVal;
            }
        }

        return value;
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
}
