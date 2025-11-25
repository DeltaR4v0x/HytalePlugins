package com.fancyinnovations.fancycore.utils;

import java.awt.*;

public class ColorUtils {

    public static String formatColorInHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

}
