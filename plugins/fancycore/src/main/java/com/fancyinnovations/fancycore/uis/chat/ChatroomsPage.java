package com.fancyinnovations.fancycore.uis.chat;

import com.fancyinnovations.fancycore.api.chat.ChatRoom;
import com.fancyinnovations.fancycore.api.chat.ChatService;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

public class ChatroomsPage extends InteractiveCustomUIPage<ChatroomUIData> {

    public ChatroomsPage(@NotNull PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, ChatroomUIData.CODEC);
    }

    @Override
    public void build(@NotNull Ref<EntityStore> ref, @NotNull UICommandBuilder command, @NotNull UIEventBuilder event, @NotNull Store<EntityStore> store) {
        UUIDComponent uuidComponent = store.getComponent(ref, UUIDComponent.getComponentType());
        if (uuidComponent == null) {
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(uuidComponent.getUuid());
        if (fp == null) {
            return;
        }

        command.append("Pages/ChatroomsPage.ui");

        command.clear("#ChatroomsCards");
        command.appendInline("#Main #ChatroomsList", "Group #ChatroomsCards { LayoutMode: Left; }");

        int i = 0;
        for (ChatRoom chatroom : ChatService.get().getAllChatRooms()) {
            if (!PermissionsModule.get().hasPermission(fp.getData().getUUID(), "fancycore.chatrooms." + chatroom.getName())) {
                continue;
            }

            command.append("#ChatroomsCards", "Pages/ChatroomEntry.ui");

            command.set("#ChatroomsCards[" + i + "] #ChatroomName.Text", chatroom.getName());
            command.set("#ChatroomsCards[" + i + "] #WatchingCount.Text", chatroom.getWatchers().size() + "");

            if (chatroom.getWatchers().contains(fp)) {
                command.set("#ChatroomsCards[" + i + "] #WatchButton.Text", "Unwatch");
            }

            if (fp.getCurrentChatRoom().getName().equals(chatroom.getName())) {
                command.remove("#ChatroomsCards[" + i + "] #SwitchButton");
            }

            event.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    "#ChatroomsCards[" + i + "] #WatchButton",
                    EventData.of("ChatroomName", chatroom.getName()).append("Action", "Watch"),
                    false
            );

            if (!fp.getCurrentChatRoom().getName().equals(chatroom.getName())) {
                event.addEventBinding(
                        CustomUIEventBindingType.Activating,
                        "#ChatroomsCards[" + i + "] #SwitchButton",
                        EventData.of("ChatroomName", chatroom.getName()).append("Action", "Switch"),
                        false
                );
            }

            i++;
        }
    }

    @Override
    public void handleDataEvent(@NotNull Ref<EntityStore> ref, @NotNull Store<EntityStore> store, @NotNull ChatroomUIData data) {
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            return;
        }

        UUIDComponent uuidComponent = store.getComponent(ref, UUIDComponent.getComponentType());
        if (uuidComponent == null) {
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(uuidComponent.getUuid());
        if (fp == null) {
            return;
        }

        ChatRoom chatRoom = ChatService.get().getChatRoom(data.getChatroomName());
        if (chatRoom == null) {
            fp.sendMessage("Chatroom not found: " + data.getChatroomName());
            return;
        }

        if (!PermissionsModule.get().hasPermission(fp.getData().getUUID(), "fancycore.chatrooms." + chatRoom.getName())) {
            fp.sendMessage("You do not have permission to access this chatroom: " + data.getChatroomName());
            return;
        }

        if (data.getAction().equals("Watch")) {
            if (chatRoom.getWatchers().contains(fp)) {
                String defaultChatroom = FancyCorePlugin.get().getConfig().getDefaultChatroom();
                if (chatRoom.getName().equalsIgnoreCase(defaultChatroom)) {
                    fp.sendMessage("You cannot unwatch the default chat room.");
                    return;
                }

                chatRoom.stopWatching(fp);
                fp.sendMessage("You have stopped watching chat room " + chatRoom.getName() + ".");
            } else {
                chatRoom.startWatching(fp);
                fp.sendMessage("You are now watching chat room " + chatRoom.getName() + ".");
            }
            player.getPageManager().setPage(ref, store, Page.None);
            return;
        } else if (data.getAction().equals("Switch")) {
            if (!chatRoom.getWatchers().contains(fp)) {
                chatRoom.startWatching(fp);
            }
            fp.switchChatRoom(chatRoom);
            fp.sendMessage("You have switched to chat room " + chatRoom.getName() + ".");
        } else {
            fp.sendMessage("Unknown action: " + data.getAction());
            return;
        }

        player.getPageManager().setPage(ref, store, Page.None);
    }
}
