package com.fancyinnovations.fancycore.placeholders.builtin.server;

import com.fancyinnovations.fancycore.api.placeholders.PlaceholderProvider;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.hypixel.hytale.server.core.HytaleServerConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerMaxPlayersPlaceholder implements PlaceholderProvider {

    public static final ServerMaxPlayersPlaceholder INSTANCE = new ServerMaxPlayersPlaceholder();

    private final int maxPlayers;

    private ServerMaxPlayersPlaceholder() {
        this.maxPlayers = HytaleServerConfig.load().getMaxPlayers();
    }

    @Override
    public String getName() {
        return "Max players";
    }

    @Override
    public String getIdentifier() {
        return "max_players";
    }

    @Override
    public String parse(@Nullable FancyPlayer player, @NotNull String input) {
        return String.valueOf(this.maxPlayers);
    }

}
