package com.fancyinnovations.fancycore.economy.service;

import com.fancyinnovations.fancycore.api.FancyCoreConfig;
import com.fancyinnovations.fancycore.api.economy.Currency;
import com.fancyinnovations.fancycore.api.economy.CurrencyService;
import com.fancyinnovations.fancycore.api.economy.CurrencyStorage;
import com.fancyinnovations.fancycore.api.economy.LeaderboardEntry;
import com.fancyinnovations.fancycore.api.player.FancyPlayerData;
import com.fancyinnovations.fancycore.commands.economy.currencytemplate.CurrencyTemplateCMD;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.hypixel.hytale.server.core.command.system.CommandManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CurrencyServiceImpl implements CurrencyService {

    private static final CurrencyStorage STORAGE = FancyCorePlugin.get().getCurrencyStorage();
    private static final FancyCoreConfig CONFIG = FancyCorePlugin.get().getConfig();

    private final Map<String, Currency> currencies;
    private Map<Currency, List<LeaderboardEntry>> topBalances;

    public CurrencyServiceImpl() {
        this.currencies = new ConcurrentHashMap<>();
        this.topBalances = new ConcurrentHashMap<>();
        load();

        FancyCorePlugin.get().getThreadPool().scheduleWithFixedDelay(this::calculateTopBalances, 30, 60*10, TimeUnit.SECONDS);
    }

    private void load() {
        String serverName = FancyCorePlugin.get().getConfig().getServerName();

        for (Currency currency : STORAGE.getAllCurrencies()) {
            // Only load currencies for this server
            if (!currency.server().equalsIgnoreCase("global") && !currency.server().equalsIgnoreCase(serverName)) {
                continue;
            }

            this.currencies.put(currency.name(), currency);
            CommandManager.get().register(new CurrencyTemplateCMD(currency));
        }
    }

    @Override
    public Currency getCurrency(String name) {
        return this.currencies.get(name);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return List.copyOf(this.currencies.values());
    }

    @Override
    public void registerCurrency(Currency currency) {
        this.currencies.put(currency.name(), currency);
        STORAGE.setCurrency(currency);

        CommandManager.get().register(new CurrencyTemplateCMD(currency));
    }

    @Override
    public void unregisterCurrency(String name) {
        this.currencies.remove(name);
        STORAGE.removeCurrency(name);

        CommandManager.get().getCommandRegistration().remove(name.toLowerCase());
    }

    @Override
    public Currency getPrimaryCurrency() {
        return getCurrency(CONFIG.primaryCurrencyName());
    }

    @Override
    public List<LeaderboardEntry> getTopBalances(Currency currency, int limit) {
        List<LeaderboardEntry> entries = this.topBalances.getOrDefault(currency, new ArrayList<>());
        if (entries.size() <= limit) {
            return entries;
        }
        return entries.subList(0, limit);
    }

    private void calculateTopBalances() {
        FancyCorePlugin.get().getFancyLogger().info("Calculating top balances for all currencies...");

        Map<Currency, List<LeaderboardEntry>> newTopBalances = new ConcurrentHashMap<>();

        for (Currency currency : getAllCurrencies()) {
            List<FancyPlayerData> allPlayers = FancyCorePlugin.get().getPlayerStorage().loadAllPlayers();

            List<FancyPlayerData> sorted = allPlayers.stream()
                    .filter(playerData -> playerData.getBalance(currency) > 0)
                    .sorted((p1, p2) -> Double.compare(p2.getBalance(currency), p1.getBalance(currency)))
                    .limit(10)
                    .toList();

            List<LeaderboardEntry> topEntries = new ArrayList<>();
            for (int i = 0; i < sorted.size(); i++) {
                FancyPlayerData playerData = sorted.get(i);
                LeaderboardEntry entry = new LeaderboardEntry(
                        i + 1,
                        playerData.getUUID(),
                        playerData.getUsername(),
                        currency,
                        playerData.getBalance(currency)
                );
                topEntries.add(entry);
            }

            newTopBalances.put(currency, topEntries);
        }

        this.topBalances = newTopBalances;
        FancyCorePlugin.get().getFancyLogger().info("Top balances calculation completed.");
    }

}
