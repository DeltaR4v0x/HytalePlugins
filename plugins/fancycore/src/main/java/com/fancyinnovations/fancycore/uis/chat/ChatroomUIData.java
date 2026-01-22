package com.fancyinnovations.fancycore.uis.chat;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class ChatroomUIData {

    public static final BuilderCodec<ChatroomUIData> CODEC = BuilderCodec.builder(ChatroomUIData.class, ChatroomUIData::new)
            .addField(
                    new KeyedCodec<>("ChatroomName", Codec.STRING),
                    ChatroomUIData::setChatroomName,
                    ChatroomUIData::getChatroomName
            )
            .addField(
                    new KeyedCodec<>("Action", Codec.STRING),
                    ChatroomUIData::setAction,
                    ChatroomUIData::getAction
            )
            .build();

    private String chatroomName;
    private String action;

    public ChatroomUIData() {
    }

    public String getChatroomName() {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
