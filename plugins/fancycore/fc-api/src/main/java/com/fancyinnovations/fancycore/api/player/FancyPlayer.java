package com.fancyinnovations.fancycore.api.player;

import java.awt.*;
import java.util.UUID;

public interface FancyPlayer {

    UUID getUUID();

    String getUsername();

    String getNickname();
    void setNickname(String nickname);

    Color getChatColor();
    void setChatColor(Color chatColor);

    double getBalance();
    void setBalance(double balance);
    void addBalance(double balance);
    void removeBalance(double balance);

    long getFirstLoginTime();

    long getPlayTime();
    void addPlayTime(long playTime);

    boolean isDirty();
    void setDirty(boolean dirty);

}
