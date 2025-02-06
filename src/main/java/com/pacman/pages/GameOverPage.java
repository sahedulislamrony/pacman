package com.pacman.pages;

import com.pacman.core.ImageLoader;
import com.pacman.router.AppRouter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class GameOverPage extends JPanel {

    JLabel scoreLabel;
    public GameOverPage(AppRouter app) {


        AppRouter.setListener(this::updateState);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        // Game Over heading
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Load and resize Pac-Man image
        ImageLoader loader = new ImageLoader();
        Image originalImage = loader.loadImage("assets/pacmanRight.png");
        Image scaledImage = loader.scale(originalImage,400);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel pacmanLabel = new JLabel(resizedIcon);
        pacmanLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score label
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 1 row, 2 columns, with spacing
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(300, 50));

        // Resume Button
        JButton resumeButton = new JButton("Restart");
        styleButton(resumeButton);
        resumeButton.addActionListener(e -> app.showPage("Game"));
        resumeButton.setBackground(Color.YELLOW);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBackground(Color.RED);

        buttonPanel.add(resumeButton);
        buttonPanel.add(exitButton);

        // Add components
        add(Box.createVerticalStrut(80));
        add(gameOverLabel);
        add(Box.createVerticalStrut(30));
        add(pacmanLabel);
        add(Box.createVerticalStrut(30));
        add(scoreLabel);
        add(Box.createVerticalStrut(40));
        add(buttonPanel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void updateState(Map<String,Object> data){
        Integer score = (Integer) AppRouter.getRouterState();
        scoreLabel.setText("Your score is " + score);
    }
}
