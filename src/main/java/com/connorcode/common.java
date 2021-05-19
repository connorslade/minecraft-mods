package com.connorcode;

public class common {
    public static String randomFromArray(String[] array) {
        int index = (int)(Math.random() * array.length);
        return array[index];
    }
}
