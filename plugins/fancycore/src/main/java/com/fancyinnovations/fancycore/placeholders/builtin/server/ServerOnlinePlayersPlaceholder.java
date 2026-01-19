package com.fancyinnovations.fancycore.placeholders.builtin.server;

import com.fancyinnovations.fancycore.api.placeholders.PlaceholderProvider;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.hypixel.hytale.server.core.universe.Universe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerOnlinePlayersPlaceholder implements PlaceholderProvider {

    public static final ServerOnlinePlayersPlaceholder INSTANCE = new ServerOnlinePlayersPlaceholder();

    private ServerOnlinePlayersPlaceholder() {
    }

    @Override
    public String getName() {
        return "Online players";
    }

    @Override
    public String getIdentifier() {
        return "online_players";
    }

    @Override
    public String parse(@Nullable FancyPlayer player, @NotNull String input) {
        return String.valueOf(Universe.get().getPlayerCount());
    }

}
