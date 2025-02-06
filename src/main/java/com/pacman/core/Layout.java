package com.pacman.core;


import java.awt.*;
import javax.swing.*;

public class Layout extends JFrame {
    protected JPanel mainPanel;
    protected CardLayout cardLayout;

    @SuppressWarnings("unused")
    public Layout() {
        super("Pacman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setUndecorated(true);

        // Custom title bar
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.YELLOW);
        titleBar.setLayout(new BorderLayout());
        titleBar.setPreferredSize(new Dimension(getWidth(), 25));

        // Title label
        JLabel titleLabel = new JLabel("  Pacman Game ", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleBar.add(titleLabel, BorderLayout.WEST);

        // Close button
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.setPreferredSize(new Dimension(50, 40));
        closeButton.addActionListener(e -> System.exit(0));
        titleBar.add(closeButton, BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);
        requestFocusInWindow();
        // CardLayout for navigation
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getMainLayout(){
        return cardLayout;
    }

}
