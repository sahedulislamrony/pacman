package com.pacman.pages;


import com.pacman.router.AppRouter;

import javax.swing.*;
import java.awt.*;

public class SettingsPage extends JPanel {
    public SettingsPage(AppRouter app) {
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);

        JLabel settingsLabel = new JLabel("Settings", SwingConstants.CENTER);
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 36));
        settingsLabel.setForeground(Color.WHITE);
        add(settingsLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> app.showPage("Welcome"));

        add(backButton, BorderLayout.SOUTH);
    }
}
