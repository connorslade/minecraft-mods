package com.connorcode;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String[] code = sdf.format(cal.getTime()).split(":");
        int value = Integer.parseInt(code[0]) * Integer.parseInt(code[1]);
        String valueString = String.valueOf(value);

        int hash = 7;
        for (int i = 0; i < valueString.length(); i++) {
            hash = hash * 7919 + valueString.charAt(i);
        }

        System.out.println("\033[36mKey: " + "\033[32m" + hash);
    }
}
