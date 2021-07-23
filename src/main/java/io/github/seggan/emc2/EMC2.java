package io.github.seggan.emc2;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.bstats.charts.SingleLineChart;
import io.github.seggan.emc2.items.QGPCapacitor;
import io.github.seggan.emc2.qgp.ItemValues;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import lombok.Getter;

import java.io.File;
import java.util.Map;
import javax.annotation.Nonnull;

public final class EMC2 extends AbstractAddon {
    
    private static EMC2 instance;

    @Getter
    private boolean slimeTinkerInstalled;

    public EMC2() {
    }

    public EMC2(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onAddonEnable() {
        instance = this;

        ItemValues.setup();
        Setup.setupRecipes(this);
        Setup.setupResearches();

        slimeTinkerInstalled = getServer().getPluginManager().isPluginEnabled("SlimeTinker");
    }

    @Override
    public void onAddonDisable() {
        instance = null;
    }

    @Override
    protected void onTestEnable() {
        instance = this;

        ItemValues.setup();
        Setup.setupRecipes(this);
        Setup.setupResearches();
    }

    @Override
    public void onTestDisable() {
        instance = null;
    }

    @Override
    protected Metrics setupMetrics() {
        Metrics metrics = new Metrics(this, 11550);
        metrics.addCustomChart(new SingleLineChart("qgp_stored", () -> {
            int amount = 0;
            for (BlockStorage bs : SlimefunPlugin.getRegistry().getWorlds().values()) {
                for (Map.Entry<Location, Config> entry : bs.getRawStorage().entrySet()) {
                    if (SlimefunItem.getByID(entry.getValue().getString("id")) instanceof QGPCapacitor) {
                        amount += QGPCapacitor.get(entry.getKey().getBlock());
                    }
                }
            }
            return amount;
        }));
        return metrics;
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Seggan/EMC2/master";
    }

    public static EMC2 inst() {
        return instance;
    }
}
