package com.digital.wallet.utls;

import io.netty.util.internal.StringUtil;

public class ComplianceHelper {
    public static String generateOTP() {
        int randomPin = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(randomPin);
    }

    public static String splitAndGet(String str, String delimiter, int indexNumberToGet) {
        String target = StringUtil.EMPTY_STRING;
        String[] split = str.split(delimiter);
        if (split.length >= indexNumberToGet) {
            target = split[indexNumberToGet];
        }
        return target;
    }
}
