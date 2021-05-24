package io.github.seggan.emc2;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ItemValuesTest {

    private static ServerMock server;
    private static SlimefunPlugin slimefun;
    private static EMC2 emc2;

    @BeforeAll
    static void setUp() {
        server = MockBukkit.mock();
        slimefun = MockBukkit.load(SlimefunPlugin.class);
        emc2 = MockBukkit.load(EMC2.class);
    }

    @Test
    public void testBaseMaterials() {
        ShapelessRecipe recipe = new ShapelessRecipe(emc2.getKey("fl"), new ItemStack(Material.FLINT_AND_STEEL));
        recipe.addIngredient(Material.IRON_INGOT);
        recipe.addIngredient(Material.FLINT);
        server.addRecipe(recipe);
        server.addRecipe(new FurnaceRecipe(emc2.getKey("st"), new ItemStack(Material.STONE), Material.COBBLESTONE, 1,1 ));

        Assertions.assertFalse(ItemValues.getInstance().getBaseMaterials().contains(Material.FLINT_AND_STEEL));
        Assertions.assertFalse(ItemValues.getInstance().getBaseMaterials().contains(Material.STONE));
    }

    @AfterAll
    static void tearDown() {
        MockBukkit.unmock();
    }
}
