package com.fancyinnovations.fancycore.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    public static String formatNumber(double value) {
        // TODO: Add support large numbers (k, m, b, etc.)
        return DECIMAL_FORMAT.format(value);
    }

}
