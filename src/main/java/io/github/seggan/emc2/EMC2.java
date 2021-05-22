package io.github.seggan.emc2;

import javax.annotation.Nonnull;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;

public final class EMC2 extends AbstractAddon {
    
    private static EMC2 instance;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        ItemValues.setup();
    }

    @Override
    protected Metrics setupMetrics() {
        return null;
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Seggan/AddonTemplate/master";
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static EMC2 inst() {
        return instance;
    }
}
