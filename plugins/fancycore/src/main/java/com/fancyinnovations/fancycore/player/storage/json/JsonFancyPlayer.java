package com.fancyinnovations.fancycore.player.storage.json;

import com.fancyinnovations.fancycore.player.FancyPlayerImpl;
import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.util.UUID;

public record JsonFancyPlayer(
        String uuid,
        String username,
        String nickname,
        @SerializedName("chat_color") String chatColor,
        double balance,
        @SerializedName("first_login_time") long firstLoginTime,
        @SerializedName("play_time") long playTime
) {

    /**
     * Converts a FancyPlayerImpl to a JsonFancyPlayer
     */
    public static JsonFancyPlayer from(FancyPlayerImpl player) {
        return new JsonFancyPlayer(
                player.getUUID().toString(),
                player.getUsername(),
                player.getNickname(),
                Integer.toHexString(player.getChatColor().getRGB()),
                player.getBalance(),
                player.getFirstLoginTime(),
                player.getPlayTime()
        );
    }

    /**
     * Converts this JsonFancyPlayer to a FancyPlayerImpl
     */
    public FancyPlayerImpl toFancyPlayer() {
        return new FancyPlayerImpl(
                UUID.fromString(uuid),
                username,
                nickname,
                new Color((int) Long.parseLong(chatColor, 16), true),
                balance,
                firstLoginTime,
                playTime
        );
    }

}
