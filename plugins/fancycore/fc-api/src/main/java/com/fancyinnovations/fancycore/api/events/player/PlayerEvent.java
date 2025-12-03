package com.fancyinnovations.fancycore.api.events.player;

import com.fancyinnovations.fancycore.api.events.FancyEvent;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;

/**
 * The base class for all player-related events in the FancyCore system.
 */
public abstract class PlayerEvent extends FancyEvent {

    protected final FancyPlayer player;

    public PlayerEvent(FancyPlayer player) {
        super();
        this.player = player;
    }

    /**
     * Returns the player associated with this event.
     *
     * @return the FancyPlayer involved in the event
     */
    public FancyPlayer getPlayer() {
        return player;
    }
}
