package com.pacman.pages;


import com.pacman.router.AppRouter;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JPanel {
    public ProfilePage(AppRouter app) {
        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        JLabel profileLabel = new JLabel("Profile", SwingConstants.CENTER);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 36));
        profileLabel.setForeground(Color.WHITE);
        add(profileLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> app.showPage("Welcome"));

        add(backButton, BorderLayout.SOUTH);
    }
}
