package com.fancyinnovations.fancycore.chat;

import com.fancyinnovations.fancycore.api.FancyCore;
import com.fancyinnovations.fancycore.api.chat.ChatRoom;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomImpl implements ChatRoom {

    private final String name;
    private final Set<FancyPlayer> watchers;
    private final Map<UUID, Long> lastMessageTimestamps;
    private boolean muted;
    private long cooldown;

    public ChatRoomImpl(String name) {
        this.name = name;
        this.watchers = new HashSet<>();
        this.muted = false;
        this.cooldown = 0;
        this.lastMessageTimestamps = new ConcurrentHashMap<>();
    }

    public ChatRoomImpl(String name, boolean muted, long cooldown) {
        this.name = name;
        this.watchers = new HashSet<>();
        this.muted = muted;
        this.cooldown = cooldown;
        this.lastMessageTimestamps = new ConcurrentHashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<FancyPlayer> getWatchers() {
        return new ArrayList<>(watchers);
    }

    @Override
    public void startWatching(FancyPlayer player) {
        watchers.add(player);
    }

    @Override
    public void stopWatching(FancyPlayer player) {
        watchers.remove(player);
    }

    @Override
    public void broadcastMessage(String message) {
        for (FancyPlayer participant : watchers) {
            participant.sendMessage(message);
        }
    }

    @Override
    public void sendMessage(FancyPlayer sender, String message) {
        if (muted && !sender.checkPermission("fancycore.chat.bypassmute")) {
            sender.sendMessage("Chat is currently muted."); // TODO make message translatable
            return;
        }

        long lastMessageTime = lastMessageTimestamps.getOrDefault(sender.getData().getUUID(), 0L);
        long currentTime = System.currentTimeMillis();
        long remainingCooldown = cooldown - (currentTime - lastMessageTime);
        if (remainingCooldown > 0 && !sender.checkPermission("fancycore.chat.bypasscooldown")) {
            sender.sendMessage("You must wait " + (remainingCooldown / 1000) + " seconds before sending another message."); // TODO make message translatable
            return;
        }

        String parsedMessage = FancyCore.get().getPlaceholderService().parse(sender, message);
        parsedMessage = parsedMessage.replace("%message%", message);

        broadcastMessage(parsedMessage);

        lastMessageTimestamps.put(sender.getData().getUUID(), currentTime);
    }

    @Override
    public boolean isMuted() {
        return muted;
    }

    @Override
    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    @Override
    public void clearChat() {
        for (int i = 0; i < 100; i++) {
            broadcastMessage(""); // Sending empty messages to simulate clearing chat
        }

        broadcastMessage("Chat has been cleared."); // TODO make message translatable
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
