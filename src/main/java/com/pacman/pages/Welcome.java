package com.pacman.pages;


import com.pacman.core.ImageLoader;
import com.pacman.router.AppRouter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Welcome extends JPanel {
    public Welcome(AppRouter app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        // Header label
        JLabel headerLabel = new JLabel("Welcome to Pacman Game", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(Color.YELLOW);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Load and resize Pac-Man image
        ImageLoader loader = new ImageLoader();
        Image originalImage = loader.loadImage("assets/pacmanRight.png");
        Image scaledImage = loader.scale(originalImage,400,400);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel pacmanLabel = new JLabel(resizedIcon);
        pacmanLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start button
        JButton startButton = new JButton("Start the Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(Color.YELLOW);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(e -> app.showPage("Game")); // Switch to GamePage

        // Add components
        add(Box.createVerticalStrut(100));
        add(headerLabel);
        add(Box.createVerticalStrut(50));
        add(pacmanLabel);
        add(Box.createVerticalStrut(50));
        add(startButton);
    }
}
