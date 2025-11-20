package com.fancyinnovations.fancycore.player;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import org.jetbrains.annotations.ApiStatus;

import java.awt.*;
import java.util.UUID;

public class FancyPlayerImpl implements FancyPlayer {

    private final UUID uuid;
    private final String username;
    private String nickname;
    private Color chatColor;
    private double balance;
    private long firstLoginTime; // timestamp
    private long playTime; // in milliseconds

    private boolean isDirty;

    /**
     * Constructor for a new FancyPlayer
     */
    public FancyPlayerImpl(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.nickname = username; // default nickname is the username
        this.chatColor = Color.WHITE;
        this.balance = 0.0;
        this.firstLoginTime = System.currentTimeMillis();
        this.playTime = 0L;
        this.isDirty = true;
    }

    public FancyPlayerImpl(UUID uuid, String username, String nickname, Color chatColor, double balance, long firstLoginTime, long playTime) {
        this.chatColor = chatColor;
        this.uuid = uuid;
        this.username = username;
        this.nickname = nickname;
        this.balance = balance;
        this.firstLoginTime = firstLoginTime;
        this.playTime = playTime;
        this.isDirty = false;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        this.isDirty = true;
    }

    @Override
    public Color getChatColor() {
        return chatColor;
    }

    @Override
    public void setChatColor(Color chatColor) {
        this.chatColor = chatColor;
        this.isDirty = true;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
        this.isDirty = true;
    }

    @Override
    public void addBalance(double amount) {
        this.balance += amount;
        this.isDirty = true;
    }

    @Override
    public void removeBalance(double amount) {
        this.balance -= amount;
        this.isDirty = true;
    }

    @Override
    public long getFirstLoginTime() {
        return firstLoginTime;
    }

    @ApiStatus.Internal
    public void setFirstLoginTime(long firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    @Override
    public long getPlayTime() {
        return playTime;
    }

    @ApiStatus.Internal
    public void setPlayTime(long playTime) {
        this.playTime = playTime;
        this.isDirty = true;
    }

    @Override
    public void addPlayTime(long additionalTime) {
        this.playTime += additionalTime;
        this.isDirty = true;
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }
}
