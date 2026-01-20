package com.fancyinnovations.fancycore.placeholders.builtin;

import com.fancyinnovations.fancycore.api.placeholders.PlaceholderService;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.fancyinnovations.fancycore.placeholders.builtin.luckperms.LPPrefixPlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.luckperms.LPPrimaryGroupNamePlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.luckperms.LPSuffixPlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.player.*;
import com.fancyinnovations.fancycore.placeholders.builtin.server.ServerMaxPlayersPlaceholder;
import com.fancyinnovations.fancycore.placeholders.builtin.server.ServerOnlinePlayersPlaceholder;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;

import java.util.concurrent.TimeUnit;

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
        PlaceholderService.get().registerProvider(PlayerIsOPPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerGamemodePlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerLocationXPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerLocationYPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerLocationZPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerLocationYawPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerLocationPitchPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(PlayerLocationRollPlaceholder.INSTANCE);

        // Server placeholders
        PlaceholderService.get().registerProvider(ServerMaxPlayersPlaceholder.INSTANCE);
        PlaceholderService.get().registerProvider(ServerOnlinePlayersPlaceholder.INSTANCE);

        // LuckPerms placeholders
        FancyCorePlugin.get().getThreadPool().schedule(BuiltInPlaceholderProviders::tryToRegisterLuckPermsPlaceholders, 30L, TimeUnit.SECONDS);
    }

    private static boolean tryToRegisterLuckPermsPlaceholders() {
        PluginBase luckPermsPlugin = PluginManager.get().getPlugin(PluginIdentifier.fromString("LuckPerms:LuckPerms"));
        if (luckPermsPlugin != null && luckPermsPlugin.isEnabled()) {
            PlaceholderService.get().registerProvider(new LPPrefixPlaceholder());
            PlaceholderService.get().registerProvider(new LPSuffixPlaceholder());
            PlaceholderService.get().registerProvider(new LPPrimaryGroupNamePlaceholder());
            FancyCorePlugin.get().getFancyLogger().info("Registered LuckPerms placeholder providers");
            return true;
        }

        return false;
    }
}
