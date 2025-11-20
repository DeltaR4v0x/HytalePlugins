package com.fancyinnovations.fancycore.metrics;

import com.fancyinnovations.fancycore.main.FancyCorePlugin;

public class PluginMetrics {

    private final static FancyCorePlugin plugin = FancyCorePlugin.get();

    public PluginMetrics() {

    }

    public void register() {
        // TODO: Implement metrics registration
    }

    private double totalAmountPlayers() {
        return plugin.getPlayerStorage().countPlayers();
    }

    private String serverSizeCategory() {
        // TODO: find server size category based on online players
        // 0 = empty
        // 1-25 = small
        // 26-100 = medium
        // 101-500 = large
        // 501+ = very_large

        return "unknown";
    }
}
