package com.fancyinnovations.fancycore.commands.teleport;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.teleport.Warp;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class WarpCMD extends AbstractPlayerCommand {

    protected final RequiredArg<Warp> warpArg = this.withRequiredArg("warp", "the name of the warp", FancyCoreArgs.WARP);

    public WarpCMD() {
        super("warp", "Teleports you to the warp point with the specified name");
         requirePermission("fancycore.commands.warp");
    }

    @Override
    protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        Warp warp = warpArg.get(ctx);

        if (!PermissionsModule.get().hasPermission(fp.getData().getUUID(), "fancycore.warps." + warp.name())) {
            fp.sendMessage("You do not have permission to use this warp.");
            return;
        }

        Transform warpLoc = warp.location().toTransform();
        TransformComponent transformComponent = store.getComponent(ref, TransformComponent.getComponentType());

        Vector3f previousBodyRotation = transformComponent.getRotation().clone();
        Vector3f spawnRotation = warpLoc.getRotation().clone();
        warpLoc.setRotation(new Vector3f(previousBodyRotation.getPitch(), spawnRotation.getYaw(), previousBodyRotation.getRoll()));

        Teleport teleport = new Teleport(world, warpLoc).withHeadRotation(spawnRotation);
        store.addComponent(ref, Teleport.getComponentType(), teleport);

        fp.sendMessage("Teleported to spawn point.");
    }
}
