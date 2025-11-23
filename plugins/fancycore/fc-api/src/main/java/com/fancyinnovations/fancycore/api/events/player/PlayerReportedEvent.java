package com.fancyinnovations.fancycore.api.events.player;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.punishments.PlayerReport;

/**
 * Event fired when a player is reported by another player.
 */
public class PlayerReportedEvent extends PlayerEvent {

    private final PlayerReport report;

    public PlayerReportedEvent(FancyPlayer player, PlayerReport report) {
        super(player);
        this.report = report;
    }

    /**
     * Returns the report that was filed against the player.
     *
     * @return the PlayerReport object
     */
    public PlayerReport getReport() {
        return report;
    }
}
