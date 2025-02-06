package com.pacman.core;


import java.awt.*;
import java.util.*;

public class GameMap {
    private static final int TILE_SIZE = 32;
    private static final int ROW_COUNT = 21;
    private static final int COLUMN_COUNT = 19;
    private static final int BOARD_WIDTH = COLUMN_COUNT * TILE_SIZE;
    private static final int BOARD_HEIGHT = ROW_COUNT * TILE_SIZE;


    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    private HashSet<Block> walls;
    private HashSet<Block> foods;
    private HashSet<Block> ghosts;
    private Block pacman;

    private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    public  GameMap(){
        // initial load
        load();
    }
    public Map<String, Object> getMap() {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("tileMap", tileMap);
        mapData.put("dimension", new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        mapData.put("width",BOARD_WIDTH);
        mapData.put("height",BOARD_HEIGHT);
        mapData.put("tileSize",TILE_SIZE);
        mapData.put("rowCount",ROW_COUNT);
        mapData.put("columnCount",COLUMN_COUNT);
        return mapData;
    }

    public int getMapWidth() {
        return BOARD_WIDTH;
    }

    public int getMapHeight() {
        return BOARD_HEIGHT;
    }

    private void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        for (int r = 0; r < ROW_COUNT; r++) {
            String row = tileMap[r];
            for (int c = 0; c < COLUMN_COUNT; c++) {
                char tileChar = row.charAt(c);
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                switch (tileChar) {
                    case 'X' -> walls.add(new Block(wallImage, x, y, TILE_SIZE, TILE_SIZE));
                    case 'b' -> ghosts.add(new Block(blueGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                    case 'o' -> ghosts.add(new Block(orangeGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                    case 'p' -> ghosts.add(new Block(pinkGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                    case 'r' -> ghosts.add(new Block(redGhostImage, x, y, TILE_SIZE, TILE_SIZE));
                    case 'P' -> pacman = new Block(pacmanRightImage, x, y, TILE_SIZE, TILE_SIZE);
                    case ' ' -> foods.add(new Block(null, x + 14, y + 14, 4, 4));
                }
            }
        }
    }

    private void loadImages() {
        ImageLoader imageLoader = new ImageLoader();
        wallImage = imageLoader.loadImage("assets/wall.png");
        blueGhostImage = imageLoader.loadImage("assets/blueGhost.png");
        orangeGhostImage = imageLoader.loadImage("assets/orangeGhost.png");
        pinkGhostImage = imageLoader.loadImage("assets/pinkGhost.png");
        redGhostImage = imageLoader.loadImage("assets/redGhost.png");
        pacmanUpImage = imageLoader.loadImage("assets/pacmanUp.png");
        pacmanDownImage = imageLoader.loadImage("assets/pacmanDown.png");
        pacmanLeftImage = imageLoader.loadImage("assets/pacmanLeft.png");
        pacmanRightImage = imageLoader.loadImage("assets/pacmanRight.png");
    }

    public void load() {
        loadImages();
        loadMap();
    }

    public Map<String, Object> getEntityDataSet() {
        Map<String, Object> dataSet = new HashMap<>();
        dataSet.put("walls", walls);
        dataSet.put("ghosts", ghosts);
        dataSet.put("foods", foods);
        dataSet.put("pacman", pacman); // Pacman is a single object, not a HashSet
        return dataSet;
    }

    public Map<String, Image> getPacmanImages(){
        Map<String,Image> imageMap = new HashMap<>();
        imageMap.put("up",pacmanUpImage);
        imageMap.put("down",pacmanDownImage);
        imageMap.put("left",pacmanLeftImage);
        imageMap.put("right",pacmanRightImage);

        return imageMap;
    }
}
