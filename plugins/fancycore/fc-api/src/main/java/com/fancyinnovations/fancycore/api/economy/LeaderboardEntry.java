package com.fancyinnovations.fancycore.api.economy;

import java.util.UUID;

public record LeaderboardEntry(
        int rank,
        UUID playerUUID,
        String username,
        Currency currency,
        double balance
) {
}
