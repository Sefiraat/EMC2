package io.github.seggan.emc2.qgp;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Calculator {

    private static final Set<RecipeType> blacklistedRecipes = new HashSet<>();
    private static final Set<String> blacklistedIds = new HashSet<>();

    static {
        blacklistedRecipes.add(RecipeType.ORE_WASHER);
        blacklistedRecipes.add(RecipeType.GEO_MINER);
        blacklistedRecipes.add(RecipeType.GOLD_PAN);
        blacklistedRecipes.add(RecipeType.MOB_DROP);
        blacklistedRecipes.add(RecipeType.BARTER_DROP);
        blacklistedRecipes.add(RecipeType.ORE_CRUSHER);
        blacklistedRecipes.add(RecipeType.NULL);

        blacklistedIds.add("UU_MATTER");
        blacklistedIds.add("SILICON");
        blacklistedIds.add("FALLEN_METEOR");
    }

    @Nonnull
    public static Map<ItemStack, Long> calculate(@Nonnull SlimefunItem parent, long amount) {

        Map<ItemStack, Long> result = new HashMap<>();
        add(result, parent.getItem(), amount);

        // uncraft the material
        add(result, parent.getItem(), -parent.getRecipeOutput().getAmount());
        for (ItemStack item : parent.getRecipe()) {
            if (item == null) continue;
            add(result, item, item.getAmount());
        }

        // uncraft submaterials
        SlimefunItemStack next = getNextItem(result);
        while (next != null) {
            int multiplier = next.getItem().getRecipeOutput().getAmount();
            long operations = (result.get(next) + multiplier - 1) / multiplier; // ceiling(needed/multiplier) but abusing fast ints
            add(result, next, -(multiplier * operations));
            for (ItemStack item : next.getItem().getRecipe()) {
                if (item == null) continue;
                add(result, item, item.getAmount() * operations);
            }
            next = getNextItem(result);
        }

        return result;
    }

    /**
     * Gets the next item of a map that needs to be uncrafted. Returns null if no items are found.
     * An item needs to be uncrafted if
     * - it is a slimefun item
     * - there is a positive amount in the map(still requires crafting), and
     * - it is not blacklisted.
     */
    @Nullable
    private static SlimefunItemStack getNextItem(Map<ItemStack, Long> map) {
        for (Map.Entry<ItemStack, Long> entry : map.entrySet()) {
            if (entry.getKey() instanceof SlimefunItemStack) {
                SlimefunItemStack ingredient = (SlimefunItemStack) entry.getKey();
                if (!blacklistedIds.contains(ingredient.getItemId()) &&
                    !blacklistedRecipes.contains(ingredient.getItem().getRecipeType())) {
                    if (entry.getValue() > 0) {
                        return ingredient;
                    }
                }
            }
        }
        return null;
    }

    private static void add(@Nonnull Map<ItemStack, Long> map, @Nonnull ItemStack key, long amount) {
        ItemStack clone = key.clone();
        clone.setAmount(1);
        map.merge(clone, amount, Long::sum);
    }
}
