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
    public void onAddonEnable() {
        instance = this;

        ItemValues.setup();
        Setup.setupRecipes(this);
        Setup.setupResearches();
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
        return new Metrics(this, 11550);
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
