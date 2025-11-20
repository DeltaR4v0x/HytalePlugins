package com.fancyinnovations.fancycore.api.player;

import java.util.UUID;

public interface FancyPlayerStorage {

    void savePlayer(FancyPlayer player);

    FancyPlayer loadPlayer(UUID uuid);

    void deletePlayer(UUID uuid);

    int countPlayers();
}
