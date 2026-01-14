package com.fancyinnovations.fancycore.commands.player;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.ParseResult;
import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class FancyPlayerArg {

    public static final String NAME = "player";
    public static final String DESCRIPTION = "The username or uuid of the player";

    public static final SingleArgumentType<FancyPlayer> TYPE = new SingleArgumentType<FancyPlayer>("Player", "Player username or uuid", new String[]{"OliverHD", "Simon", UUID.randomUUID().toString()}) {

        public @Nullable FancyPlayer parse(@Nonnull String input, @Nonnull ParseResult parseResult) {
            FancyPlayer fancyPlayer = FancyPlayerService.get().getByUsername(input);
            if (fancyPlayer == null) {
                try {
                    UUID uuid = UUID.fromString(input);
                    fancyPlayer = FancyPlayerService.get().getByUUID(uuid);
                } catch (IllegalArgumentException _) {
                }
            }

            if (fancyPlayer == null) {
                parseResult.fail(Message.raw("Player \"" + input + "\" not found."));
                return null;
            }

            return fancyPlayer;
        }
    };

}
