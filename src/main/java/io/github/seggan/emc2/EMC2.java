package io.github.seggan.emc2;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.seggan.emc2.qgp.ItemValues;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.annotation.Nonnull;
import java.io.File;

public final class EMC2 extends AbstractAddon {
    
    private static EMC2 instance;

    public EMC2() {
    }

    public EMC2(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        ItemValues.setup();
        Setup.setup(this);
    }

    @Override
    protected Metrics setupMetrics() {
        return null;
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Seggan/EMC2/master";
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static EMC2 inst() {
        return instance;
    }
}
