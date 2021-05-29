package io.github.seggan.emc2;

import io.github.seggan.emc2.items.Dematerializer;
import io.github.seggan.emc2.items.Materializer;
import io.github.seggan.emc2.items.QGPCapacitor;
import io.github.seggan.emc2.items.Router;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class Setup {

    public static void setup(EMC2 addon) {
        new QGPCapacitor(Items.SMALL_CAPACITOR, new ItemStack[9], 1_000).register(addon);
        new QGPCapacitor(Items.MEDIUM_CAPACITOR, new ItemStack[9], 50_000).register(addon);
        new QGPCapacitor(Items.LARGE_CAPACITOR, new ItemStack[9], 2_500_000).register(addon);

        new Dematerializer().register(addon);
        new Materializer().register(addon);
        new Router().register(addon);
    }
}
