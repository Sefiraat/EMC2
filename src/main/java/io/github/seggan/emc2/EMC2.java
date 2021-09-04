package io.github.seggan.emc2;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.metrics.bukkit.Metrics;
import io.github.mooy1.infinitylib.metrics.charts.SingleLineChart;
import io.github.seggan.emc2.items.QGPCapacitor;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import lombok.Getter;

import java.io.File;
import java.util.Map;

public final class EMC2 extends AbstractAddon {
    
    private static EMC2 instance;

    @Getter
    private boolean slimeTinkerInstalled;

    public EMC2() {
        super("Seggan", "EMC2", "master", "auto-update");
    }

    public EMC2(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file, "Seggan", "EMC2", "master", "auto-update");
    }

    @Override
    public void enable() {
        instance = this;

        Metrics metrics = new Metrics(this, 11550);
        metrics.addCustomChart(new SingleLineChart("qgp_stored", () -> {
            int amount = 0;
            for (BlockStorage bs : Slimefun.getRegistry().getWorlds().values()) {
                for (Map.Entry<Location, Config> entry : bs.getRawStorage().entrySet()) {
                    if (SlimefunItem.getById(entry.getValue().getString("id")) instanceof QGPCapacitor) {
                        amount += QGPCapacitor.get(entry.getKey().getBlock());
                    }
                }
            }
            return amount;
        }));

        ItemValues.setup();
        Setup.setupRecipes(this);
        Setup.setupResearches();

        slimeTinkerInstalled = getServer().getPluginManager().isPluginEnabled("SlimeTinker");
    }

    @Override
    public void disable() {
        instance = null;
    }

    public static EMC2 inst() {
        return instance;
    }
}
