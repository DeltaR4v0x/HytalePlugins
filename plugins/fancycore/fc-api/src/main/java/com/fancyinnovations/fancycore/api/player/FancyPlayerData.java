package com.fancyinnovations.fancycore.api.player;

import com.fancyinnovations.fancycore.api.permissions.Permission;
import org.jetbrains.annotations.ApiStatus;

import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * Represents the data associated with a Fancy Player.
 */
public interface FancyPlayerData {

    UUID getUUID();
    @ApiStatus.Internal void setUUID(UUID uuid);

    String getUsername();
    @ApiStatus.Internal void setUsername(String username);

    List<Permission> getPermissions();
    void setPermissions(List<Permission> permissions);
    void setPermission(String permission, boolean enabled);
    void removePermission(String permission);

    List<UUID> getGroups();
    void setGroups(List<UUID> groups);
    void addGroup(UUID group);
    void removeGroup(UUID group);

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
