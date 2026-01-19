package com.fancyinnovations.fancycore.commands.scoreboard;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.scoreboard.ScoreboardLine;
import com.fancyinnovations.fancycore.api.scoreboard.ScoreboardPage;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.fancyinnovations.fancycore.scoreboard.ScoreboardLineImpl;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

public class ScoreboardPageLinesAddCMD extends AbstractPlayerCommand {

    private final RequiredArg<ScoreboardPage> pageArg = withRequiredArg("page", "the scoreboard page", FancyCoreArgs.SCOREBOARD_PAGE);
    private final RequiredArg<String> textArg = withRequiredArg("text", "the text", ArgTypes.STRING);

    public ScoreboardPageLinesAddCMD() {
        super("add", "Add a new line to a scoreboard page");
        requirePermission("fancycore.commands.scoreboard.page.lines.add");
        setAllowsExtraArguments(true);
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

        String[] parts = ctx.getInputString().split(" ");
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 5; i < parts.length; i++) {
            messageBuilder.append(parts[i]);
            if (i < parts.length - 1) {
                messageBuilder.append(" ");
            }
        }
        String text = messageBuilder.toString();

        ScoreboardPage page = pageArg.get(ctx);

        ScoreboardLine line = new ScoreboardLineImpl(
                text,
                "Left",
                22,
                0,
                0,
                10,
                0
        );

        page.addLine(line);
        FancyCorePlugin.get().getScoreboardStorage().storePage(page);

        fp.sendMessage("Added new line to scoreboard page '" + page.getName() + "': " + line.getContent());
    }
}
