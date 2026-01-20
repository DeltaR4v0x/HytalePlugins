package com.fancyinnovations.fancycore.placeholders.builtin.luckperms;

import com.fancyinnovations.fancycore.api.placeholders.PlaceholderProvider;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LPSuffixPlaceholder implements PlaceholderProvider {

    private LuckPerms luckPerms = null;

    public LPSuffixPlaceholder() {
        luckPerms = LuckPermsProvider.get();
    }
    @Override
    public String getName() {
        return "LuckPerms player suffix";
    }

    @Override
    public String getIdentifier() {
        return "luckperms_suffix";
    }

    @Override
    public String parse(@Nullable FancyPlayer player, @NotNull String input) {
        if (player == null || luckPerms == null) {
            return "N/A";
        }

        User user = luckPerms.getUserManager().getUser(player.getData().getUUID());
        return user.getCachedData().getMetaData().getSuffix();
    }

}
