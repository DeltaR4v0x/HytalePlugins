package com.fancyinnovations.fancycore.uis.inventory;

import com.fancyinnovations.fancycore.api.inventory.Backpack;
import com.fancyinnovations.fancycore.api.inventory.BackpacksService;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

public class BackpacksPage extends InteractiveCustomUIPage<BackpackUIData> {

    public BackpacksPage(@NotNull PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, BackpackUIData.CODEC);
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

        command.append("Pages/BackpacksPage.ui");

        command.clear("#BackpackCards");
        command.appendInline("#Main #BackpackList", "Group #BackpackCards { LayoutMode: Left; }");

        int i = 0;
        for (Backpack backpack : BackpacksService.get().getBackpacks(fp.getData().getUUID())) {
            command.append("#BackpackCards", "Pages/BackpackEntry.ui");

            command.set("#BackpackCards[" + i + "] #BackpackName.Text", backpack.name());
            command.set("#BackpackCards[" + i + "] #Size.Text", backpack.size() + " slots");

            event.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    "#BackpackCards[" + i + "] #OpenButton",
                    EventData.of("BackpackName", backpack.name()),
                    false
            );

            i++;
        }
    }

    @Override
    public void handleDataEvent(@NotNull Ref<EntityStore> ref, @NotNull Store<EntityStore> store, @NotNull BackpackUIData data) {
        super.handleDataEvent(ref, store, data);

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

        Backpack backpack = BackpacksService.get().getBackpack(fp.getData().getUUID(), data.getBackpackName());
        if (backpack == null) {
            fp.sendMessage("Backpack not found: " + data.getBackpackName());
            return;
        }

        player.getPageManager().setPage(ref, store, Page.None);

        BackpacksService.get().openBackpack(fp, fp, backpack);
    }
}
