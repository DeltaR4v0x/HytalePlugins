package com.fancyinnovations.fancycore.commands.inventory;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.uis.inventory.BackpacksPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

public class ListBackpacksCMD extends AbstractPlayerCommand {

    public ListBackpacksCMD() {
        super("listbackpacks", "Lists all available backpacks");
        addAliases("backpacks", "listvirtualbackpacks", "listbp");
        requirePermission("fancycore.commands.listbackpacks");
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

        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            ctx.sendMessage(Message.raw("Player component not found."));
            return;
        }

        player.getPageManager().openCustomPage(ref, store, new BackpacksPage(playerRef));

//        List<Backpack> backpacks = BackpacksService.get().getBackpacks(fp.getData().getUUID());
//        if (backpacks.isEmpty()) {
//            ctx.sendMessage(Message.raw("You have no backpacks."));
//            return;
//        }
//
//        ctx.sendMessage(Message.raw("Your Backpacks:"));
//        for (Backpack backpack : backpacks) {
//            ctx.sendMessage(Message.raw("- " + backpack.name() + " (" + backpack.size() + " slots)"));
//        }
    }
}
