package com.fancyinnovations.fancycore.metrics;

public class PluginMetrics {

    public PluginMetrics() {

    }

    public void register() {
        // TODO: Implement metrics registration
    }

    private double totalAmountPlayers() {
        // TODO: count documents in player storage
        return 0.0;
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
