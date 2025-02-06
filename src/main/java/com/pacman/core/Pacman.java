package com.pacman.core;


import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import javax.swing.*;
import com.pacman.router.AppRouter;


public  class Pacman extends JPanel implements ActionListener, KeyListener {

    AppRouter app; // router instance
    GameMap map = new GameMap();

    // data
    HashSet<Block> walls;
    HashSet<Block> ghosts;
    HashSet<Block> foods;
    Block pacman;


    // pacman images
    Map<String, Image> images = map.getPacmanImages();
    Image pacmanUpImage = (Image) images.get("up");
    Image pacmanDownImage = (Image) images.get("down");
    Image pacmanLeftImage = (Image) images.get("left");
    Image pacmanRightImage = (Image) images.get("right");

    // Additional Map data
    Map<String, Object> mapData = map.getMap();
    Dimension dimension = (Dimension) mapData.get("dimension");
    Integer TILE_SIZE = (Integer) mapData.get("tileSize");
    Integer BOARD_WIDTH = (Integer) mapData.get("width");

    private static final char[] DIRECTIONS = {'U', 'D', 'L', 'R'}; // Up, Down, Left, Right

    private final Timer gameLoop;
    private final Random random = new Random();
    private boolean gameOver = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Pacman() {
        setPreferredSize(dimension);
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // game loop
        gameLoop = new Timer(50, this); // 20 FPS

        initializeGame();
    }

    @SuppressWarnings("unchecked")
    private void getData() {
        // get data
        Map<String, Object> entityData = map.getEntityDataSet();
        Object wallsObj = entityData.get("walls");
        Object ghostObj = entityData.get("walls");
        Object foodsObj = entityData.get("walls");
        pacman = (Block) entityData.get("pacman");
        if(wallsObj instanceof HashSet<?> && ghostObj instanceof HashSet<?> && foodsObj instanceof HashSet<?>){
            walls = (HashSet<Block>) entityData.get("walls");
            ghosts = (HashSet<Block>) entityData.get("ghosts");
            foods = (HashSet<Block>) entityData.get("foods");

        }

    }


    public  void initializeGame() {
        map.load();
        getData();
        gameOver = false;
        for (Block ghost : ghosts) {
            ghost.setDirection(DIRECTIONS[random.nextInt(DIRECTIONS.length)]);
            updateVelocity(ghost);
        }

        gameLoop.start();

        // reset game data
        GameData.resetGameData();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), pacman.getWidth(), pacman.getHeight(), null);

        ghosts.forEach(ghost -> g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight(), null));
        walls.forEach(wall -> g.drawImage(wall.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null));

        g.setColor(Color.YELLOW);
        foods.forEach(food -> g.fillRect(food.getX(), food.getY(), food.getWidth(), food.getHeight()));

    }

    private void move() {
        updatePacmanPosition();
        checkCollisions();
        updateGhosts();
        checkFoodCollision();
        checkGameOver();
    }

    private void updatePacmanPosition() {
        pacman.setX(pacman.getX() + pacman.getVelocityX());
        pacman.setY(pacman.getY() + pacman.getVelocityY());

        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                pacman.setX(pacman.getX() - pacman.getVelocityX());
                pacman.setY(pacman.getY() - pacman.getVelocityY());
                break;
            }
        }
    }

    private void checkCollisions() {
        for (Block ghost : ghosts) {
            if (collision(ghost, pacman)) {
                int lives = (int) GameData.getValue("lives") - 1;
                GameData.updateData("lives", lives);
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }
        }
    }


    private void updateGhosts() {
        int path = 9;
        for (Block ghost : ghosts) {
            int ghostRow = ghost.getY() / TILE_SIZE;
            if (ghostRow == path && ghost.getDirection() != 'U' && ghost.getDirection() != 'D') {
                char newDirection = random.nextBoolean() ? 'U' : 'D';

                int prevX = ghost.getX();
                int prevY = ghost.getY();
                char prevDirection = ghost.getDirection();

                ghost.setDirection(newDirection);
                updateVelocity(ghost);
                ghost.setX(ghost.getX() + ghost.getVelocityX());
                ghost.setY(ghost.getY() + ghost.getVelocityY());

                for(Block wall : walls){
                    if(collision(wall,ghost)) {
                        ghost.setX(prevX);
                        ghost.setY(prevY);
                        ghost.setDirection(prevDirection);
                        updateVelocity(ghost);

                    }
                }

            }

            // Update ghost's position using its current velocity.
            ghost.setX(ghost.getX() + ghost.getVelocityX());
            ghost.setY(ghost.getY() + ghost.getVelocityY());

            // Check for collisions with walls or horizontal board boundaries.
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.getX() <= 0 || ghost.getX() + ghost.getWidth() >= BOARD_WIDTH) {
                    // Revert the movement.
                    ghost.setX(ghost.getX() - ghost.getVelocityX());
                    ghost.setY(ghost.getY() - ghost.getVelocityY());

                    ghost.setDirection(DIRECTIONS[random.nextInt(DIRECTIONS.length)]);
                    updateVelocity(ghost);
                }
            }
        }
    }



    private void checkFoodCollision() {
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(pacman, food)) {
                foodEaten = food;
                int score = (int) GameData.getValue("score") + 10;
                GameData.updateData("score", score);
            }
        }
        foods.remove(foodEaten);

        if (foods.isEmpty()) {
            map.load();
            resetPositions();
        }
    }


    private void checkGameOver() {
        if (gameOver) {
            GameOver();
        }
    }

    private void GameOver() {
        gameLoop.stop();
        Integer currentScore = (Integer) GameData.getValue("score");
        app.showPage("GameOver", currentScore);
    }

    public void setRouter(AppRouter app) {
        this.app = app;
    }

    private boolean collision(Block a, Block b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }

    private void resetPositions() {
        pacman.reset();
        pacman.setVelocityX(0);
        pacman.setVelocityY(0);
        for (Block ghost : ghosts) {
            ghost.reset();
            ghost.setDirection(DIRECTIONS[random.nextInt(4)]);
            updateVelocity(ghost);
        }
    }

    private void updateVelocity(Block entity) {
        switch (entity.getDirection()) {
            case 'U' -> {
                entity.setVelocityX(0);
                entity.setVelocityY(-TILE_SIZE / 4);
            }
            case 'D' -> {
                entity.setVelocityX(0);
                entity.setVelocityY(TILE_SIZE / 4);
            }
            case 'L' -> {
                entity.setVelocityX(-TILE_SIZE / 4);
                entity.setVelocityY(0);
            }
            case 'R' -> {
                entity.setVelocityX(TILE_SIZE / 4);
                entity.setVelocityY(0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            GameOver();
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> updatePacmanDirection('U', pacmanUpImage);
            case KeyEvent.VK_DOWN -> updatePacmanDirection('D', pacmanDownImage);
            case KeyEvent.VK_LEFT -> updatePacmanDirection('L', pacmanLeftImage);
            case KeyEvent.VK_RIGHT -> updatePacmanDirection('R', pacmanRightImage);
        }
    }

    private void updatePacmanDirection(char direction, Image image) {
        // Store the current position and state
        int prevX = pacman.getX();
        int prevY = pacman.getY();
        char prevDirection = pacman.getDirection();
        Image prevImage = pacman.getImage();

        // Temporarily update the direction and image
        pacman.setDirection(direction);
        pacman.setImage(image);
        updateVelocity(pacman);

        // Move Pacman slightly in the new direction to check for a collision
        pacman.setX(prevX + pacman.getVelocityX());
        pacman.setY(prevY + pacman.getVelocityY());

        boolean isColliding = false;
        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                isColliding = true;
                break;
            }
        }

        // If collision is detected, revert the changes
        if (isColliding) {
            pacman.setX(prevX);
            pacman.setY(prevY);
            pacman.setDirection(prevDirection);
            pacman.setImage(prevImage);
            updateVelocity(pacman);
        }
    }

}
