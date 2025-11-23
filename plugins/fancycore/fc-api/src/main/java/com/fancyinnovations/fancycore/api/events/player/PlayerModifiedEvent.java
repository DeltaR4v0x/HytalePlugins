package com.fancyinnovations.fancycore.api.events.player;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;

/**
 * Event fired when a player's data is modified (via commands).
 */
public class PlayerModifiedEvent extends PlayerEvent {

    private final FancyPlayer newPlayerData;
    private final ModifiedField modifiedField;

    public PlayerModifiedEvent(FancyPlayer oldPlayerData, FancyPlayer newPlayerData, ModifiedField modifiedField) {
        super(oldPlayerData);
        this.newPlayerData = newPlayerData;
        this.modifiedField = modifiedField;
    }

    /**
     * Returns the player's data before the modification.
     *
     * @return the old FancyPlayer data
     */
    public FancyPlayer getOldPlayerData() {
        return getPlayer();
    }

    /**
     * Returns the player's data after the modification.
     *
     * @return the new FancyPlayer data
     */
    public FancyPlayer getNewPlayerData() {
        return newPlayerData;
    }

    /**
     * Returns the field that was modified.
     *
     * @return the modified field
     */
    public ModifiedField getModifiedField() {
        return modifiedField;
    }

    public enum ModifiedField {
        NICKNAME,
        CHAT_COLOR,
        BALANCE,
        FIRST_LOGIN_TIME,
        PLAY_TIME
    }
}
