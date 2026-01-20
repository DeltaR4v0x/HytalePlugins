package com.fancyinnovations.fancycore.placeholders.builtin;

import com.fancyinnovations.fancycore.api.placeholders.PlaceholderService;
import com.fancyinnovations.fancycore.placeholders.builtin.luckperms.LPPrefixPlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.luckperms.LPPrimaryGroupNamePlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.luckperms.LPSuffixPlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.player.*;
import com.fancyinnovations.fancycore.placeholders.builtin.server.ServerMaxPlayersPlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.server.ServerOnlinePlayersPlaceholder;

public class BuiltInPlaceholderProviders {

    public static void registerAll() {
        // Player placeholders
        PlaceholderService.get().registerProvider(PlayerBalanceFormattedPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerBalanceRawPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerChatColorPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerFirstTimeJoinedPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerFirstTimeJoinedRawPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerGroupPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerGroupPrefixPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerGroupSuffixPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerNamePlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerNickNamePlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerPlayTimeFormattedPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerPlayTimeMsPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerUuidPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerWorldPlaceholder.INSTANCE);

        // Server placeholders
        PlaceholderService.get().registerProvider(ServerMaxPlayersPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(ServerOnlinePlayersPlaceholder.INSTANCE);

        // LuckPerms placeholders
         PlaceholderService.get().registerProvider(LPPrefixPlaceholder.INSTANCE);
         PlaceholderService.get().registerProvider(LPSuffixPlaceholder.INSTANCE);
         PlaceholderService.get().registerProvider(LPPrimaryGroupNamePlaceholder.INSTANCE);
    }

}
