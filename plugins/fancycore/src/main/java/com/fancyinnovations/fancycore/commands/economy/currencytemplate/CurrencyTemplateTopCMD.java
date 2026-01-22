package com.fancyinnovations.fancycore.commands.economy.currencytemplate;

import com.fancyinnovations.fancycore.api.economy.Currency;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.uis.economy.BalancetopPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class CurrencyTemplateTopCMD extends AbstractPlayerCommand {

    protected final Currency currency;

    public CurrencyTemplateTopCMD(Currency currency) {
        super("top", "Shows the balance of a player in " + currency.name() + ".");
        this.currency = currency;
        requirePermission("fancycore.commands."+currency.name()+".top");
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

        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            ctx.sendMessage(Message.raw("Player component not found."));
            return;
        }

        player.getPageManager().openCustomPage(ref, store, new BalancetopPage(playerRef, currency));

//        List<LeaderboardEntry> topBalances = CurrencyService.get().getTopBalances(currency, 10);
//
//        fp.sendMessage("Top 10 Balances in " + currency.name() + ":");
//        if (topBalances.isEmpty()) {
//            fp.sendMessage("No balances found.");
//            return;
//        }
//
//        for (int i = 0; i < topBalances.size(); i++) {
//            LeaderboardEntry entry = topBalances.get(i);
//            fp.sendMessage(entry.rank() + ". " + entry.username() + " (" + currency.symbol() + NumberUtils.formatNumber(entry.balance()) + ") ");
//        }
    }
}
