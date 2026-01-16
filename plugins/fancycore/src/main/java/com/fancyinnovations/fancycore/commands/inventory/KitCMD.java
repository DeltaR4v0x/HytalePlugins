package com.fancyinnovations.fancycore.commands.inventory;

import com.fancyinnovations.fancycore.api.inventory.Kit;
import com.fancyinnovations.fancycore.api.inventory.KitsService;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KitCMD extends AbstractPlayerCommand {

    protected final RequiredArg<Kit> kitArg = this.withRequiredArg("kit", "the name of the kit", FancyCoreArgs.KIT);
    protected final OptionalArg<FancyPlayer> targetArg = this.withOptionalArg("target", "target player", FancyCoreArgs.PLAYER);

    public KitCMD() {
        super("kit", "Gives the specified kit to the targeted player");
        requirePermission("fancycore.commands.kit");
    }

    @Override
    protected void execute(@NotNull CommandContext ctx, @NotNull Store<EntityStore> store, @NotNull Ref<EntityStore> ref, @NotNull PlayerRef playerRef, @NotNull World world) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        Kit kit = kitArg.get(ctx);

        if (!PermissionsModule.get().hasPermission(fp.getData().getUUID(), "fancycore.kits." + kit.name())) {
            fp.sendMessage("You do not have permission to use this kit.");
            return;
        }

        Player player = ref.getStore().getComponent(ref, Player.getComponentType());
        if (player == null) {
            fp.sendMessage("You are not an player");
            return;
        }
        ItemContainer hotbar = player.getInventory().getHotbar();
        ItemContainer storage = player.getInventory().getStorage();


        List<ItemStack> items = KitsService.get().getKitItems(kit);
        for (ItemStack item : items) {
            if (!tryToAddItemToContainer(item, hotbar)) {
                if (!tryToAddItemToContainer(item, storage)) {
                    fp.sendMessage("Not enough space in inventory for: " + item.toString());
                }
            }
        }

        fp.sendMessage("You have received the kit: " + kit.name());
    }

    private boolean tryToAddItemToContainer(ItemStack item, ItemContainer container) {
        if (!container.canAddItemStack(item)) {
            return false;
        }

        ItemStackTransaction itemStackTransaction = container.addItemStack(item);
        if (!itemStackTransaction.succeeded()) {
            return false;
        }

        return true;
    }
}
