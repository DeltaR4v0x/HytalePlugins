package com.fancyinnovations.fancycore.commands.teleport;

import com.fancyinnovations.fancycore.api.teleport.Warp;
import com.fancyinnovations.fancycore.api.teleport.WarpService;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.ParseResult;
import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TeleportArgs {

    public static final SingleArgumentType<Warp> WARP = new SingleArgumentType<Warp>("Warp", "The name of the warp", new String[]{"spawn", "shop", "arena"}) {
        public @Nullable Warp parse(@Nonnull String input, @Nonnull ParseResult parseResult) {
            Warp warp = WarpService.get().getWarp(input);
            if (warp == null) {
                parseResult.fail(Message.raw("Warp '" + input + "' not found."));
                return null;
            }

            return warp;
        }
    };

}
