package com.fancyinnovations.fancycore.commands.permissions.player;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.commands.player.FancyPlayerArg;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

public class PermissionsCheckCMD extends CommandBase {

    protected final RequiredArg<String> permissionArg = this.withRequiredArg("permission", "the permission to test", ArgTypes.STRING);
    protected final OptionalArg<FancyPlayer> targetArg = this.withOptionalArg("target", "the target player", FancyPlayerArg.TYPE);

    public PermissionsCheckCMD() {
        super("check", "Checks if you/someone has a permission");
        addAliases("test");

        requirePermission("fancycore.commands.checkpermission");
    }

    @Override
    protected void executeSync(@NotNull CommandContext ctx) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        FancyPlayer target = targetArg.provided(ctx) ? targetArg.get(ctx) : fp;

        String permission = permissionArg.get(ctx);

        boolean success = target.checkPermission(permission);
        fp.sendMessage("Player " + target.getData().getUsername() + (success ? " has " : " does not have ") + "the permission " + permission + ".");
    }
}
