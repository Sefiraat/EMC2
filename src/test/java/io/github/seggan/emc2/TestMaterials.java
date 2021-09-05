package io.github.seggan.emc2;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;

public class TestMaterials {

    private static ServerMock server;
    private static Slimefun slimefun;
    private static EMC2 emc2;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        slimefun = MockBukkit.load(Slimefun.class);
        emc2 = MockBukkit.load(EMC2.class);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testMissingItems() {
        Set<Material> items = ItemValues.getInstance().getValues().keySet();
        Set<Material> missing = EnumSet.noneOf(Material.class);

        for (Material material : Material.values()) {
            if (!items.contains(material) && !material.name().startsWith("LEGACY_") && material.isItem()) {
                missing.add(material);
            }
        }

        missing.removeIf(SlimefunTag.UNBREAKABLE_MATERIALS::isTagged);
        missing.removeIf(SlimefunTag.SPAWN_EGGS::isTagged);
        missing.removeIf(Material::isAir);
        missing.remove(Material.DEBUG_STICK);

        if (!missing.isEmpty()) {
            missing.forEach(System.out::println);
            throw new AssertionError("Missing materials: " + missing.size());
        }
    }
}
