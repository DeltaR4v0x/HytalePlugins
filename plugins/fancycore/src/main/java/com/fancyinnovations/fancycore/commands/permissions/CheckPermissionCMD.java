package com.fancyinnovations.fancycore.commands.permissions;

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

public class CheckPermissionCMD extends CommandBase {

    protected final RequiredArg<String> permissionArg = this.withRequiredArg("permission", "the permission to test", ArgTypes.STRING);
    protected final OptionalArg<FancyPlayer> targetArg = this.withOptionalArg("target", "the target player", FancyPlayerArg.TYPE);

    public CheckPermissionCMD() {
        super("checkpermission", "Checks if you/someone has a permission");
        requirePermission("fancycore.commands.checkpermission");
    }

    @Override
    protected void executeSync(@NotNull CommandContext ctx) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = targetArg.provided(ctx) ? targetArg.get(ctx) : FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        String permission = permissionArg.get(ctx);


        boolean success = fp.checkPermission(permission);
        fp.sendMessage("Permission check for '" + permission + "': " + success);
    }
}
