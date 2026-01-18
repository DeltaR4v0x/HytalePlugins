package com.fancyinnovations.fancycore.commands.economy;

import com.fancyinnovations.fancycore.api.economy.Currency;
import com.fancyinnovations.fancycore.api.economy.CurrencyService;
import com.fancyinnovations.fancycore.api.economy.LeaderboardEntry;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.utils.NumberUtils;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BalanceTopCMD extends CommandBase {

    public BalanceTopCMD() {
        super("balancetop", "View the top balances for the primary currency.");
        addAliases("baltop");
        requirePermission("fancycore.commands.balancetop");
    }

    @Override
    protected void executeSync(@NotNull CommandContext ctx) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        Currency currency = CurrencyService.get().getPrimaryCurrency();
        if (currency == null) {
            fp.sendMessage("No primary currency is set.");
            return;
        }

        List<LeaderboardEntry> topBalances = CurrencyService.get().getTopBalances(currency, 10);

        fp.sendMessage("Top 10 Balances in " + currency.name() + ":");
        if (topBalances.isEmpty()) {
            fp.sendMessage("No balances found.");
            return;
        }

        for (int i = 0; i < topBalances.size(); i++) {
            LeaderboardEntry entry = topBalances.get(i);
            fp.sendMessage(entry.rank() + ". " + entry.username() + " (" + currency.symbol() + NumberUtils.formatNumber(entry.balance()) + ") ");
        }
    }
}
