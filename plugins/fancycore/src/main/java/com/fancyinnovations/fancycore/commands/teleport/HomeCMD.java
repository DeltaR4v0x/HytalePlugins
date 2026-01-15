package com.fancyinnovations.fancycore.commands.teleport;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.player.Home;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class HomeCMD extends AbstractPlayerCommand {

    protected final OptionalArg<String> nameArg = this.withOptionalArg("home", "specific home name", ArgTypes.STRING);

    public HomeCMD() {
        super("home", "Teleports you to your home point with the specified name or the first home if no name is provided");
        addAliases("home", "h");
        requirePermission("fancycore.commands.home");
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

        Home home;
        if (nameArg.provided(ctx)) {
            home = fp.getData().getHome(nameArg.getName());
            if (home == null) {
                fp.sendMessage("Home with the name '" + nameArg.getName() + "' does not exist.");
                return;
            }
        } else {
            if (fp.getData().getHomes().isEmpty()) {
                fp.sendMessage("You do not have any homes set.");
                return;
            }
            home = fp.getData().getHomes().getFirst();
        }

        Transform homeLoc = home.location().toTransform();
        TransformComponent transformComponent = store.getComponent(ref, TransformComponent.getComponentType());

        Vector3f previousBodyRotation = transformComponent.getRotation().clone();
        Vector3f spawnRotation = homeLoc.getRotation().clone();
        homeLoc.setRotation(new Vector3f(previousBodyRotation.getPitch(), spawnRotation.getYaw(), previousBodyRotation.getRoll()));

        Teleport teleport = new Teleport(world, homeLoc).withHeadRotation(spawnRotation);
        store.addComponent(ref, Teleport.getComponentType(), teleport);

        fp.sendMessage("Teleported to home '" + home.name() + "'.");
    }
}
