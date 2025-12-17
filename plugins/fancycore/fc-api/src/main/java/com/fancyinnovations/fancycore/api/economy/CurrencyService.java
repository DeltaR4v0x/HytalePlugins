package com.fancyinnovations.fancycore.api.economy;

public interface CurrencyService {

    Currency getCurrency(String name);

    void registerCurrency(Currency currency);

    void unregisterCurrency(String name);

    Currency getPrimaryCurrency();

}
