package com.fancyinnovations.fancycore.commands.permissions.groups;

import com.fancyinnovations.fancycore.api.permissions.Group;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

public class GroupMetadataRemoveCMD extends CommandBase {

    protected final RequiredArg<Group> groupArg = this.withRequiredArg("group", "name of the group tp remove permissions in", FancyCoreArgs.GROUP);
    protected final RequiredArg<String> keyArg = this.withRequiredArg("key", "the key to remove", ArgTypes.STRING);

    protected GroupMetadataRemoveCMD() {
        super("remove", "Removes a metadata entry from a specified group");
        requirePermission("fancycore.commands.groups.metadata.remove");
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

        Group group = groupArg.get(ctx);
        String key = keyArg.get(ctx);

        group.removeMetadataValue(key);

        FancyCorePlugin.get().getPermissionStorage().storeGroup(group);

        fp.sendMessage("Removed metadata entry '" + key + "' from group " + group.getName() + ".");
    }
}
