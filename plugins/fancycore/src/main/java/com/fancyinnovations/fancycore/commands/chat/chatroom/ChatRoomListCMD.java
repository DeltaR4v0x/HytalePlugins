package com.fancyinnovations.fancycore.commands.chat.chatroom;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.uis.chat.ChatroomsPage;
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

public class ChatRoomListCMD extends AbstractPlayerCommand {

    protected ChatRoomListCMD() {
        super("list", "List all chat rooms you have permission to access");
        requirePermission("fancycore.commands.chatrooms.list");
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

        player.getPageManager().openCustomPage(ref, store, new ChatroomsPage(playerRef));

//        fp.sendMessage("Chat Rooms: ");
//        for (ChatRoom cr : ChatService.get().getAllChatRooms()) {
//            if (!PermissionsModule.get().hasPermission(fp.getData().getUUID(), "fancycore.chatrooms." + cr.getName())) {
//                continue;
//            }
//
//            fp.sendMessage("- " + cr.getName() + " (" + cr.getWatchers().size() + " watchers)");
//        }
    }
}
