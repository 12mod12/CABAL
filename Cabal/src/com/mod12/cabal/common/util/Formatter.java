package com.mod12.cabal.common.util;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class Formatter {

    private static DecimalFormat df;

    public static String format(int decimals, double value) {
        String format = "#.";
        for (int i = 0; i < decimals; i++) {
            format += "#";
        }
        df = new DecimalFormat(format);
        return df.format(value);
    }

    public static String format(String message, Object[] args) {
		return MessageFormat.format(message, args);
	}

}
