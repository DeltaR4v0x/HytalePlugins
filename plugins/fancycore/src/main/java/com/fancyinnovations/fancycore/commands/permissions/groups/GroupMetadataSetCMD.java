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

public class GroupMetadataSetCMD extends CommandBase {

    protected final RequiredArg<Group> groupArg = this.withRequiredArg("group", "name of the group", FancyCoreArgs.GROUP);
    protected final RequiredArg<String> keyArg = this.withRequiredArg("key", "the key of the metadata", ArgTypes.STRING);
    protected final RequiredArg<String> valueArg = this.withRequiredArg("value", "the value of the metadata", ArgTypes.STRING);

    protected GroupMetadataSetCMD() {
        super("set", "Sets a metadata key-value pair for a specified group");
        requirePermission("fancycore.commands.groups.metadata.set");
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
        String value = valueArg.get(ctx);

        Double numValue = null;
        try {
            numValue = Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }

        group.getMetadata().put(key, numValue != null ? numValue : value);

        FancyCorePlugin.get().getPermissionStorage().storeGroup(group);

        fp.sendMessage("Set metadata for group " + group.getName() + ": " + key + " = " + value);
    }
}
