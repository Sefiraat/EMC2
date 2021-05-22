package io.github.seggan.addontemplate;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

public final class AddonTemplate extends AbstractAddon {
    
    private static AddonTemplate instance;
    
    public static AddonTemplate inst() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
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
    
}
