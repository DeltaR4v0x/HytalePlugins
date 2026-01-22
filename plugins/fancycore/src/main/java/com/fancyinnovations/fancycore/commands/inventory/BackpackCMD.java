package com.fancyinnovations.fancycore.commands.inventory;

import com.fancyinnovations.fancycore.api.inventory.Backpack;
import com.fancyinnovations.fancycore.api.inventory.BackpacksService;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

public class BackpackCMD extends AbstractPlayerCommand {

    protected final RequiredArg<String> nameArg = this.withRequiredArg("name", "backpack name", ArgTypes.STRING);
    protected final OptionalArg<FancyPlayer> targetArg = this.withOptionalArg("player", "target player", FancyCoreArgs.PLAYER);

    public BackpackCMD() {
        super("backpack", "Opens the specified backpack of the targeted player");
        addAliases("bp", "virtualbackback", "vbp");
        requirePermission("fancycore.commands.backpack");
    }

    @Override
    protected void execute(@NotNull CommandContext ctx, @NotNull Store<EntityStore> store, @NotNull Ref<EntityStore> ref, @NotNull PlayerRef playerRef, @NotNull World world) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        String backpackName = nameArg.get(ctx);
        FancyPlayer target = targetArg.provided(ctx) ? targetArg.get(ctx) : fp;

        if (target.getData().getUUID() != fp.getData().getUUID() && !PermissionsModule.get().hasPermission(fp.getData().getUUID(), "fancycore.commands.backpack.others")) {
            fp.sendMessage("You do not have permission to open other players' backpacks.");
            return;
        }

        Backpack backpack = BackpacksService.get().getBackpack(target.getData().getUUID(), backpackName);
        if (backpack == null) {
            fp.sendMessage("Backpack '" + backpackName + "' not found for " + target.getData().getUsername() + ".");
            return;
        }

        BackpacksService.get().openBackpack(fp, target, backpack);
    }
}
