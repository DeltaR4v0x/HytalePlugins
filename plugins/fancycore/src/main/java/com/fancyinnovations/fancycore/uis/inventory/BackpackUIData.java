package com.fancyinnovations.fancycore.uis.inventory;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class BackpackUIData {

    public static final BuilderCodec<BackpackUIData> CODEC = BuilderCodec.builder(BackpackUIData.class, BackpackUIData::new)
            .addField(
                    new KeyedCodec<>("BackpackName", Codec.STRING),
                    BackpackUIData::setBackpackName,
                    BackpackUIData::getBackpackName
            )
            .build();

    private String backpackName;

    public BackpackUIData() {
    }

    public String getBackpackName() {
        return backpackName;
    }

    public void setBackpackName(String backpackName) {
        this.backpackName = backpackName;
    }
}
