package com.fancyinnovations.fancycore.api.player;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Wrapper around the actual Player object from the Hytale API.
 * This interface provides methods to interact with the player.
 */
public interface FancyPlayer {

    FancyPlayerData getData();

    @Nullable FakeHytalePlayer getPlayer();
    @ApiStatus.Internal void setPlayer(@NotNull FakeHytalePlayer player);

    boolean checkPermission(String permission);
    boolean isInGroup(UUID group);

    boolean isOnline();

    void sendMessage(String message);

}
