package com.pacman.router;


import com.pacman.core.Layout;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.pacman.pages.*;

public class AppRouter extends Layout {
    private GamePage gamePage;
    private CardLayout layout;
    private Container mainPanel;
    private String DEFAULT_ROUTE = "Welcome";


    // listening model for accessing state
    private static final Map<String, Object> data = new HashMap<>();
    private static Consumer<Map<String, Object>> listener;

    public AppRouter() {
        super();
        data.put("state",null);
        initializeComponents();
    }
    public AppRouter(String default_route) {
        super();
        if(!default_route.trim().isEmpty()){
            this.DEFAULT_ROUTE = default_route.trim();
        }
        data.put("state",null);
        initializeComponents();
    }

    private void initializeComponents() {
        mainPanel = getMainPanel();
        layout = getMainLayout();

        if (mainPanel == null || layout == null) {
            throw new IllegalStateException("Main panel or layout is not initialized.");
        }

        // Initialize and add pages to the main panel
        gamePage = new GamePage(this); // Store instance
        mainPanel.add(new Welcome(this), "Welcome");
        mainPanel.add(gamePage, "Game");
        mainPanel.add(new GameOverPage(this), "GameOver");
        mainPanel.add(new SettingsPage(this), "Settings");
        mainPanel.add(new ProfilePage(this), "Profile");

        showPage(DEFAULT_ROUTE); // open default page
        setVisible(true);
    }



    public void showPage(String pageName) {
        if (pageName == null || pageName.trim().isEmpty()) {
            throw new IllegalArgumentException("Page name cannot be null or empty.");
        }
        layout.show(mainPanel, pageName);
    }

    public void showPage(String pageName, Object state) {
        if (pageName == null || pageName.trim().isEmpty()) {
            throw new IllegalArgumentException("Page name cannot be null or empty.");
        }
        data.put("state",state);
        layout.show(mainPanel, pageName);
        notifyListener();
    }

    public static Object getRouterState() {
        return data.get("state");

    }

    public static void setListener(Consumer<Map<String, Object>> newListener) {
        listener = newListener;
    }
    private static void notifyListener() {
        if (listener != null) {
            listener.accept(data);
        }
    }
}