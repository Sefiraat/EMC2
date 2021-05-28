package io.github.seggan.emc2;

import io.github.seggan.emc2.items.Capacitor;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class Setup {

    public static void setup(EMC2 addon) {
        new Capacitor(Items.SMALL_CAPACITOR, new ItemStack[9], 1000)
            .register(addon);
    }
}
