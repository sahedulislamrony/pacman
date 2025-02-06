package com.pacman.core;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GameData {
    private static final Map<String, Object> data = new HashMap<>();
    private static Consumer<Map<String, Object>> listener;


    public static void setListener(Consumer<Map<String, Object>> newListener) {
        listener = newListener;
    }

    public static Map<String, Object> getGameData() {
        return data;
    }

    public static void updateData(String key, Object value) {
        data.put(key, value);
        notifyListener();
    }

    public static Object getValue(String key) {
        return data.get(key);
    }

    public static void resetGameData() {
        data.put("score", 0);
        data.put("lives", 3);
        notifyListener();
    }

    private static void notifyListener() {
        if (listener != null) {
            listener.accept(data);
        }
    }
}
