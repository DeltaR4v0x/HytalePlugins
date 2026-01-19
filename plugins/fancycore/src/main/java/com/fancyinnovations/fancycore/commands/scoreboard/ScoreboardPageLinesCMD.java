package com.fancyinnovations.fancycore.commands.scoreboard;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

public class ScoreboardPageLinesCMD extends AbstractCommandCollection {

    public ScoreboardPageLinesCMD() {
        super("lines", "Manage scoreboard page lines");
        requirePermission("fancycore.commands.scoreboard.page.lines");

        addSubCommand(new ScoreboardPageLinesInfoCMD());
        addSubCommand(new ScoreboardPageLinesAddCMD());
        addSubCommand(new ScoreboardPageLinesRemoveCMD());
        addSubCommand(new ScoreboardPageLinesSetTextCMD());
        addSubCommand(new ScoreboardPageLinesSetAlignmentCMD());
        addSubCommand(new ScoreboardPageLinesSetFontsizeCMD());
        addSubCommand(new ScoreboardPageLinesSetPaddingTopCMD());
        addSubCommand(new ScoreboardPageLinesSetPaddingBottomCMD());
        addSubCommand(new ScoreboardPageLinesSetPaddingLeftCMD());
        addSubCommand(new ScoreboardPageLinesSetPaddingRightCMD());
    }
}
