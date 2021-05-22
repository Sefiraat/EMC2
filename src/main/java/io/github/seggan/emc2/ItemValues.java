package io.github.seggan.emc2;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemValues {

    private static final ItemValues INSTANCE = new ItemValues();

    private final Set<Material> baseMaterials = EnumSet.noneOf(Material.class);

    private ItemValues() {
        for (Material material : Material.values()) {
            baseMaterials.addAll(getBaseMaterials(new ItemStack(material)));
        }
    }

    @Nonnull
    private Set<Material> getBaseMaterials(@Nonnull Collection<ItemStack> ingredients) {
        Set<Material> base = new HashSet<>();
        for (ItemStack stack : ingredients) {
            base.addAll(getBaseMaterials(stack));
        }
        return base;
    }

    @Nonnull
    private Set<Material> getBaseMaterials(@Nonnull ItemStack stack) {
        Set<Material> base = new HashSet<>();

        List<Recipe> recipes = Bukkit.getRecipesFor(stack);
        if (!recipes.isEmpty()) {
            for (Recipe recipe : recipes) {
                // ik this is a mess but it'll be better when Java 17 comes out
                if (recipe instanceof ShapedRecipe) {
                    base.addAll(getBaseMaterials(((ShapedRecipe) recipe).getIngredientMap().values()));
                } else if (recipe instanceof ShapelessRecipe) {
                    base.addAll(getBaseMaterials(((ShapelessRecipe) recipe).getIngredientList()));
                } else if (recipe instanceof FurnaceRecipe) {
                    base.addAll(getBaseMaterials(((FurnaceRecipe) recipe).getInput()));
                }
            }
        } else {
            base.add(stack.getType());
        }

        return base;
    }

    @Nonnull
    public static ItemValues getInstance() {
        return INSTANCE;
    }

    // does nothing, simply forces creation of the INSTANCE field
    public static void setup() {
    }
}
