package com.pacman.pages;


import com.pacman.router.AppRouter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;
import com.pacman.core.Pacman;
import com.pacman.core.GameData;

public class GamePage extends JPanel {
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    private final Pacman pacmanGame;

    public GamePage(AppRouter app) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 0, 10));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        livesLabel.setHorizontalAlignment(SwingConstants.LEFT);

        topPanel.add(scoreLabel);
        topPanel.add(livesLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel gameWrapper = new JPanel(new GridBagLayout());
        gameWrapper.setOpaque(false);

        pacmanGame = new Pacman();
        pacmanGame.setBackground(Color.BLACK);
        pacmanGame.setRouter(app);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                pacmanGame.requestFocusInWindow();
                pacmanGame.initializeGame();
            }
        });

        gameWrapper.add(pacmanGame, new GridBagConstraints());
        add(gameWrapper, BorderLayout.CENTER);


        // Subscribe to GameData updates
        GameData.setListener(this::updateUI);
    }

    private void updateUI(Map<String, Object> data) {
        scoreLabel.setText("Score: " + data.get("score"));
        livesLabel.setText("Lives: " + data.get("lives"));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        pacmanGame.requestFocusInWindow();
    }
}
