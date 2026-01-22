package com.fancyinnovations.fancycore.inventory.service;

import com.fancyinnovations.fancycore.api.inventory.Backpack;
import com.fancyinnovations.fancycore.api.inventory.BackpacksService;
import com.fancyinnovations.fancycore.api.inventory.BackpacksStorage;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.windows.ContainerWindow;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.List;
import java.util.UUID;

public class BackpacksServiceImpl implements BackpacksService {

    private final BackpacksStorage storage;

    public BackpacksServiceImpl(BackpacksStorage storage) {
        this.storage = storage;
    }

    @Override
    public Backpack getBackpack(UUID ownerUUID, String name) {
        return storage.getBackpack(ownerUUID, name);
    }

    @Override
    public List<ItemStack> getBackpackItems(UUID ownerUUID, String name) {
        return storage.getBackpackItems(ownerUUID, name);
    }

    @Override
    public List<Backpack> getBackpacks(UUID ownerUUID) {
        return storage.getBackpacks(ownerUUID);
    }

    @Override
    public void createBackpack(UUID ownerUUID, String name, int size) {
        Backpack backpack = new Backpack(ownerUUID, name, size);
        storage.storeBackpack(backpack);
        // Initialize with empty items
        storage.storeBackpackItems(ownerUUID, name, List.of());
    }

    @Override
    public void deleteBackpack(UUID ownerUUID, String name) {
        storage.deleteBackpack(ownerUUID, name);
    }

    @Override
    public void setBackpackItems(UUID ownerUUID, String name, List<ItemStack> items) {
        storage.storeBackpackItems(ownerUUID, name, items);
    }

    @Override
    public void openBackpack(FancyPlayer fp, FancyPlayer target, Backpack backpack) {
        if (!fp.isOnline()) {
            return;
        }

        Ref<EntityStore> ref = fp.getPlayer().getReference();
        Store<EntityStore> store = ref.getStore();

        List<ItemStack> items = BackpacksService.get().getBackpackItems(fp.getData().getUUID(), backpack.name());

        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            fp.sendMessage("You are not a player.");
            return;
        }

        // Create a container with the backpack's capacity
        SimpleItemContainer backpackContainer = new SimpleItemContainer((short) backpack.size());

        // Load items into the container
        for (int i = 0; i < items.size() && i < backpack.size(); i++) {
            ItemStack item = items.get(i);
            if (item != null) {
                backpackContainer.setItemStackForSlot((short) i, item);
            }
        }

        // Create and open the container window
        ContainerWindow window = new ContainerWindow(backpackContainer);

        // Register close event to save items back to storage
        window.registerCloseEvent(event -> {
            // Save all items from the container back to storage
            List<ItemStack> savedItems = new java.util.ArrayList<>();
            for (short i = 0; i < backpackContainer.getCapacity(); i++) {
                ItemStack item = backpackContainer.getItemStack(i);
                if (item != null) {
                    savedItems.add(item);
                }
            }
            BackpacksService.get().setBackpackItems(target.getData().getUUID(), backpack.name(), savedItems);
        });

        // Open the window using PageManager (this actually sends the packet)
        if (player.getPageManager().setPageWithWindows(ref, store, Page.Bench, true, window)) {
            fp.sendMessage("Opened backpack '" + backpack.name() + "' (" + backpack.size() + " slots) of " + target.getData().getUsername() + ".");
        } else {
            fp.sendMessage("Failed to open backpack '" + backpack.name() + "'.");
        }
    }

}
