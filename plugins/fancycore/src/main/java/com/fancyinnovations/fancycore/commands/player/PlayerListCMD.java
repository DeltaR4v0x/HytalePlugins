package com.fancyinnovations.fancycore.commands.player;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.uis.player.PlayerListPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class PlayerListCMD extends AbstractPlayerCommand {


    public PlayerListCMD() {
        super("playerlist", "Displays a list of all online players");
        addAliases("online", "list");
        requirePermission("fancycore.commands.playerlist");
    }

    @Override
    protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            ctx.sendMessage(Message.raw("Player component not found."));
            return;
        }

        player.getPageManager().openCustomPage(ref, store, new PlayerListPage(playerRef));

//        ctx.sendMessage(Message.raw("Online Players:"));
//
//        for (FancyPlayer onlinePlayer : FancyPlayerService.get().getOnlinePlayers()) {
//            ctx.sendMessage(Message.raw("- " + onlinePlayer.getData().getUsername() + " (UUID: " + onlinePlayer.getData().getUUID().toString() + ")"));
//        }
    }
}
