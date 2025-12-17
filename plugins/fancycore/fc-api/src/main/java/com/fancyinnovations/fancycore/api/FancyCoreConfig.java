package com.fancyinnovations.fancycore.api;

public interface FancyCoreConfig {

    String getLogLevel();

    String getEventDiscordWebhookUrl();

    String[] getEventDiscordNotifications();

    String primaryCurrencyName();

    void reload();

}
