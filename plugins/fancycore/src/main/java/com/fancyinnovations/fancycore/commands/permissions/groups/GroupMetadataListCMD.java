package com.fancyinnovations.fancycore.commands.permissions.groups;

import com.fancyinnovations.fancycore.api.permissions.Group;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GroupMetadataListCMD extends CommandBase {

    protected final RequiredArg<Group> groupArg = this.withRequiredArg("group", "name of the group", FancyCoreArgs.GROUP);

    protected GroupMetadataListCMD() {
        super("list", "Lists all metadata for a specified group");
        requirePermission("fancycore.commands.groups.metadata.list");
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

        fp.sendMessage("Metadata for group " + group.getName() + ":");
        if (group.getMetadata().isEmpty()) {
            fp.sendMessage("  No metadata attached.");
            return;
        }

        for (Map.Entry<String, Object> entry : group.getMetadata().entrySet()) {
            fp.sendMessage("  - " + entry.getKey() + " = " + entry.getValue().toString());
        }
    }
}
