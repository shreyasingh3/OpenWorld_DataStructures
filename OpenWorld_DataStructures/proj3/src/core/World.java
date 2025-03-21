package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static edu.princeton.cs.algs4.StdDraw.*;
import static utils.FileUtils.*;

public class World {
    private int WIDTH = 100;
    private int HEIGHT = 40;
    private TETile[][] tiles;
    private Long seed;
    private Random randomizer;
    private ArrayList<Room> rooms;
    private ArrayList<Lights> lights;
    private ArrayList<Integer> scythe;
    private ArrayList<Integer> treasure;
    private ArrayList<String> readableRooms;
    private final int MAX_ROOM_HEIGHT = 20;
    private final int MAX_ROOM_WIDTH = 20;
    private final int BIG_NUMBER = 5000;
    Player player;
    PlayerMovement movement;
    private String position;
    private Boolean colonCheck;
    private Boolean lightsOff;
    private Boolean gameOff;
    MainMenu menu;
    private String menuName;


    // Creates a new 100x40 world filled with NOTHING tiles.
    public World(Long s) {
        gameOff = false;
        lights = new ArrayList<>();
        scythe = new ArrayList<>();
        treasure = new ArrayList<>();
        readableRooms = new ArrayList<>();
        lightsOff = true;
        seed = s;
        rooms = new ArrayList<>();
        tiles = new TETile[WIDTH][HEIGHT];
        colonCheck = false;
        randomizer = new Random(seed); //outputs number based off of seed given

        background();

        manyRooms(randomizer);
        rooms.sort(new XComparator());
        Room firstRoom = rooms.get(0);
        int positionX = firstRoom.getX() + 2;
        int positionY = firstRoom.getY() + 1;

        connectAll();
        obstacles();
        makeLights();
        tool();

        player = new Player(positionX, positionY);
        player.setPlayer(positionX, positionY, tiles);

        movement = new PlayerMovement(player);

        updateReadableRooms();
    }

    public void setMenuName(String name) {
        this.menuName = name;
    }

    public void updateReadableRooms() {
        for (int i = 0; i < rooms.size(); i++) {
            Room readRoom = rooms.get(i);
            readableRooms.add(readRoom.getX() + "," + readRoom.getY() + "," + readRoom.getWidth() + "," + readRoom.getHeight());
        }
    }

    public void updateWorld() {
        if (hasNextKeyTyped()) {
            char input = nextKeyTyped();
            if (input == 'a' || input == 'A') {
                movement.moveLeft(player.getPosX(), player.getPosY(), tiles);
                colonCheck = false;
            }
            if (input == 'w' || input == 'W') {
                movement.moveUp(player.getPosX(), player.getPosY(), tiles);
                colonCheck = false;
            }
            if (input == 's' || input == 'S') {
                movement.moveDown(player.getPosX(), player.getPosY(), tiles);
                colonCheck = false;
            }
            if (input == 'd' || input == 'D') {
                movement.moveRight(player.getPosX(), player.getPosY(), tiles);
                colonCheck = false;
            }
            if (input == ':') {
                colonCheck = true; //checks if colon was last thing typed. Used for ":Q" case
            }
            if (input == 'q' || input == 'Q') {
                if (colonCheck) { //checks to see if colon was last thing typed.
                    writeFile("Save.txt", TETile.toString(tiles)); //saves world
                    writeFile("Scythe.txt", String.valueOf(movement.getHasScythe())); //s hasScythe
                    writeFile("Seed.txt", seed.toString());
                    writeFile("Treasure.txt", String.valueOf(movement.getHasTreasure()));
                    writeFile("ScytheCoords.txt", scythe.toString());
                    writeFile("TreasureCoords.txt", treasure.toString());
                    writeFile("ReadableRoom.txt", readableRooms.toString());
                    writeFile("NameSave.txt", menuName);

                    System.exit(0); //exits game
                    colonCheck = false;
                }
            }
            if (input == 'e' || input == 'E') {
                if (lightsOff) {
                    turnLightsOn();
                    lightsOff = false;
                } else if (!lightsOff) {
                    turnLightsOff();
                    lightsOff = true;
                }
            }
        }
        makeHUD();
    }


    public void makeHUD() {

        if (Objects.equals(this.menuName, "")) {
            String tile = "Tile: " + mousePosition();
            setPenColor(255, 255, 255);
            text(4, HEIGHT - 1, tile);

            show();
        } else {
            String tile = "Tile: " + mousePosition();
            setPenColor(255, 255, 255);
            text(4, HEIGHT - 1, tile);

            String boing = "Name: " + this.menuName;
            setPenColor(255, 255, 255);
            text((double) WIDTH / 2, this.HEIGHT - 1, boing);

            show();
        }

    }

    private String mousePosition() {
        int mousePosX = (int) StdDraw.mouseX();
        int mousePosY = (int) StdDraw.mouseY();
        if (mousePosX >= WIDTH || mousePosY >= HEIGHT) {
            return this.position;
        }

        if (tiles[mousePosX][mousePosY] == Tileset.AVATAR || tiles[mousePosX][mousePosY] == Tileset.HIGH_LIGHT_AVATAR) {
            String string = "you";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.LOWEST_LIGHT_AVATAR || tiles[mousePosX][mousePosY] == Tileset.LOW_LIGHT_AVATAR) {
            String string = "you";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.MED_LIGHT_AVATAR || tiles[mousePosX][mousePosY] == Tileset.MED_HIGH_LIGHT_AVATAR) {
            String string = "you";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.FLOOR || tiles[mousePosX][mousePosY] == Tileset.HIGH_LIGHT) {
            String string = "floor";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.SCYTHE_AVATAR) {
            String string = "you";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.LOWEST_LIGHT || tiles[mousePosX][mousePosY] == Tileset.LOW_LIGHT) {
            String string = "scythe";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.MED_LIGHT || tiles[mousePosX][mousePosY] == Tileset.MED_HIGH_LIGHT) {
            String string = "scythe";
            this.position = string;
            return string;
        }

        if (tiles[mousePosX][mousePosY] == Tileset.WALL) {
            String string = "wall";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.NOTHING) {
            String string = "nothing";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.GRASS || tiles[mousePosX][mousePosY] == Tileset.HIGH_LIGHT_GRASS) {
            String string = "grass";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.LOWEST_LIGHT_GRASS || tiles[mousePosX][mousePosY] == Tileset.LOW_LIGHT_GRASS) {
            String string = "grass";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.MED_LIGHT_GRASS || tiles[mousePosX][mousePosY] == Tileset.MED_HIGH_LIGHT_GRASS) {
            String string = "grass";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.MOUNTAIN) {
            String string = "scythe";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.EMPTY_SCYTHE) {
            String string = "empty";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.SAND) {
            String string = "treasure";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.OFF_LIGHT) {
            String string = "off light";
            this.position = string;
            return string;
        }
        if (tiles[mousePosX][mousePosY] == Tileset.ON_LIGHT) {
            String string = "on light";
            this.position = string;
            return string;
        }
        return null;
    }

    //puts rooms in the world at random coordinates.
    // SS - creates random number of rooms until weight is less than or equal to MAGIC NUMBER 0.5 at random coordinates
    public void manyRooms(Random r) {
        while (roomWeight() < 0.5) {
            int x = r.nextInt(WIDTH);
            int y = r.nextInt(HEIGHT);
            int width = randomizer.nextInt(MAX_ROOM_WIDTH);           // SS - for makeOneRoom and helper
            int height = randomizer.nextInt(MAX_ROOM_HEIGHT);         // SS - for makeOneRoom and helper
            Room newInstance = makeOneRoom(x, y, width, height);
            if (newInstance != null) {
                rooms.add(newInstance);
            }
        }
    }

    // SS - helper method that returns the amount of tiles filled compared to the board size
    private double roomWeight() {
        int numTile = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (tiles[i][j] != Tileset.NOTHING) {
                    numTile++;
                }
            }
        }
        return (double) numTile / (WIDTH * HEIGHT);
    }

    //makes a room with random size between height h and length l.
    //places it at the specified coordinate, with the bottom left corner of the room at x,y
    //size of room includes walls!
    // SS - width and height parameters are random size of room being made
    public Room makeOneRoom(int x, int y, int width, int height) {
        int newHeight = 0;
        int newWidth = 0;

        if (!isOverlapping(x, y, width, height)) {
            if (height > 3 && width > 3) { //makes sure that the rooms aren't just walls
                // SS - changed this to 3 bc it was giving me a bunch of size 2 rooms LOL (we can change it back ofc)

                //fills in the tiles.
                for (int i = x; i <= width + x; i++) {
                    for (int k = y; k <= height + y; k++) {
                        if (!outOfBounds(i, k)) { //skips these coordinates if they're out of bounds
                            if (i == x || k == y || i == width + x || k == height + y) {
                                tiles[i][k] = Tileset.WALL;
                                newWidth = i;
                                newHeight = k;
                            } else if (i == WIDTH - 1 || k == HEIGHT - 1) { //adds wall if tile is on edge of world
                                tiles[i][k] = Tileset.WALL;
                                newWidth = i;
                                newHeight = k;
                            } else {
                                tiles[i][k] = Tileset.FLOOR;
                                newWidth = i;
                                newHeight = k;
                            }
                        }
                    }
                }
                return new Room(x, y, newWidth, newHeight);
            }
        }
        return null;
    }

    //creates horizontal hallway between two points.
    //must specify x and y of starting point, but only x of end point
    public void makeHorizontalHallway(int xStart, int yStart, int end) {
        if (xStart <= end) {
            for (int i = xStart; i <= end + 1; i++) {
                tiles[i][yStart] = Tileset.FLOOR;

                //only place walls on nothing tiles (prevents overlap)
                if (!outOfBounds(i, yStart + 1) && tiles[i][yStart + 1] == Tileset.NOTHING) {
                    tiles[i][yStart + 1] = Tileset.WALL;
                }
                if (!outOfBounds(i, yStart - 1) && tiles[i][yStart - 1] == Tileset.NOTHING) {
                    tiles[i][yStart - 1] = Tileset.WALL;
                }
            }
        } else if (xStart > end) {
            for (int i = xStart; i > end; i--) {
                tiles[i][yStart] = Tileset.FLOOR;

                //only place walls on nothing tiles (prevents overlap)
                if (!outOfBounds(i, yStart + 1) && tiles[i][yStart + 1] == Tileset.NOTHING) {
                    tiles[i][yStart + 1] = Tileset.WALL;
                }
                if (!outOfBounds(i, yStart - 1) && tiles[i][yStart - 1] == Tileset.NOTHING) {
                    tiles[i][yStart - 1] = Tileset.WALL;
                }
            }
        }
    }

    //creates vertical hallway between two points.
    //must specify x and y of starting point, but only y of end point
    public void makeVerticalHallway(int yStart, int xStart, int end) {
        if (yStart <= end) {
            for (int i = yStart; i <= end; i++) {
                tiles[xStart][i] = Tileset.FLOOR;

                //only place walls on nothing tiles (prevents overlap)
                if (!outOfBounds(xStart + 1, i) && tiles[xStart + 1][i] == Tileset.NOTHING) {
                    tiles[xStart + 1][i] = Tileset.WALL;
                }
                if (!outOfBounds(xStart - 1, i) && tiles[xStart - 1][i] == Tileset.NOTHING) {
                    tiles[xStart - 1][i] = Tileset.WALL;
                }
            }
        } else if (yStart > end) {
            for (int i = yStart; i > end; i--) {
                tiles[xStart][i] = Tileset.FLOOR;

                //only place walls on nothing tiles (prevents overlap)
                if (!outOfBounds(xStart + 1, i) && tiles[xStart + 1][i] == Tileset.NOTHING) {
                    tiles[xStart + 1][i] = Tileset.WALL;
                }
                if (!outOfBounds(xStart - 1, i) && tiles[xStart - 1][i] == Tileset.NOTHING) {
                    tiles[xStart - 1][i] = Tileset.WALL;
                }
            }
        }
    }

    //gets a random Y coordinate between the two rooms
    public int selectY(Room one, Room two) {
        //find which has bigger y coordinate and make that the minimum bound "bottom"
        int bottom = -1;
        if (one.getY() <= two.getY()) {
            bottom = one.getY();
        } else if (two.getY() < one.getY()) {
            bottom = two.getY();
        }

        //find which has smaller height coordinate and make that the maximum bound "top"
        int top = -1;
        if (one.getHeight() <= two.getHeight()) {
            top = one.getHeight();
        } else if (two.getHeight() < one.getHeight()) {
            top = two.getHeight();
        }
        //continue until you find a y coordinate between top and bottom
        int newY = BIG_NUMBER; //arbitrarily large number
        while (newY >= top || newY <= bottom) {
            newY = randomizer.nextInt(top);
        }
        return newY;
    }

    //gets a random x coordinate between the two rooms
    public int selectX(Room one, Room two) {
        //find which has bigger x coordinate and make that the minimum bound "bottom"
        int bottom = -1;
        if (one.getX() <= two.getX()) {
            bottom = one.getX();
        } else if (two.getX() < one.getX()) {
            bottom = two.getX();
        }

        //find which has smaller width coordinate and make that the maximum bound "top"
        int top = -1;
        if (one.getWidth() <= two.getWidth()) {
            top = one.getWidth();
        } else if (two.getWidth() < one.getWidth()) {
            top = two.getWidth();
        }
        //continue until you get an x coordinate between top and bottom
        int newX = BIG_NUMBER; //arbitrarily large number
        while (newX >= top || newX <= bottom) {
            newX = randomizer.nextInt(top);
        }
        return newX;
    }


    public void connectOne(Room room1, Room room2) {
        //if rooms have same x coordinate, just make a vertical hallway between them (with a random x)
        if (room1.getX() == room2.getX()) {
            int newX = selectX(room1, room2);
            makeVerticalHallway(room1.getY() + 1, newX, room2.getY() + 1);

            //if rooms have same y coordinate, just make a horizontal hallway between them (with a random y)
        } else if (room1.getY() == room2.getY()) {
            int newY = selectY(room1, room2);
            makeHorizontalHallway(room1.getX() + 1, newY, room2.getX() + 1);

            //make an L shaped hallway between boxes with different (x,y)
        } else {
            int startX = -1;
            while (startX <= room1.getX()) {
                startX = randomizer.nextInt(room1.getWidth() - 1);
            }
            int startY = -1;
            while (startY <= room1.getY()) {
                startY = randomizer.nextInt(room1.getHeight() - 1);
            }
            int endX = -1;
            while (endX <= room2.getX()) {
                endX = randomizer.nextInt(room2.getWidth() - 1);
            }
            int endY = -1;
            while (endY <= room2.getY()) {
                endY = randomizer.nextInt(room2.getHeight() - 1);
            }
            connectHallways(startX, startY, endX, endY);
        }
    }

    public void connectAll() {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room room1 = rooms.get(i);
            Room room2 = rooms.get(i + 1);

            connectOne(room1, room2);
        }
    }

    public void connectHallways(int xStart, int yStart, int xEnd, int yEnd) {
        makeHorizontalHallway(xStart, yStart, xEnd - 1);
        makeVerticalHallway(yStart, xEnd, yEnd);
    }


    public boolean isGameOver() {
        if (movement.getTreasureCheck()) {
            gameOff = true;
            return true;
        }
        return false;
    }

    public boolean gameOver() {
        return gameOff;
    }

    //returns true if coordinate x,y is out of bounds of world
    public boolean outOfBounds(int x, int y) {
        return x >= WIDTH || y >= HEIGHT || x < 0 || y < 0;
    }

    public boolean outOfRoomBounds(int x, int y, Room room) {
        return x >= room.getWidth() || y >= room.getHeight() || x <= room.getX() || y <= room.getY();
    }


    // SS - returns if the room being made is overlapping a previous one
    private boolean isOverlapping(int x, int y, int width, int height) {
        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <= y + height; j++) {
                if (isAlreadySet(i, j)) {
                    return true;
                }
                if (outOfBounds(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }


    // SS - returns true if a tile at a coordinate is able to be assigned to something (is not NOTHING)
    private boolean isAlreadySet(int x, int y) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            return tiles[x][y] != Tileset.NOTHING;
        }
        return false;
    }


    // Iterates through every tile, making them all Nothing tiles
    public void background() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }


    public ArrayList<Integer> newXY(Room room) {
        int newX = BIG_NUMBER;
        while (newX >= room.getWidth() || newX <= room.getX()) {
            newX = randomizer.nextInt(room.getWidth());
        }
        int newY = BIG_NUMBER;
        while (newY >= room.getHeight() || newY <= room.getY()) {
            newY = randomizer.nextInt(room.getHeight());
        }
        ArrayList<Integer> product = new ArrayList<>();
        product.add(newX);
        product.add(newY);
        return product;
    }

    public void tool() {
        Room itemRoom = rooms.get(3);

        ArrayList<Integer> product = newXY(itemRoom);
        int newX = product.get(0);
        int newY = product.get(1);
        scythe.add(newX);
        scythe.add(newY);


        Room finalRoom = rooms.get(rooms.size() - 1);
        ArrayList<Integer> coords = newXY(finalRoom);
        int newXX = coords.get(0);
        int newYY = coords.get(1);
        treasure.add(newXX);
        treasure.add(newYY);
    }

    public void obstacles() {
        for (int i = 5; i < rooms.size(); i += 2) {
            Room room = rooms.get(i);
            for (int x = room.getX() + 1; x < room.getWidth(); x++) {
                for (int y = room.getY() + 1; y < room.getHeight(); y++) {
                    tiles[x][y] = Tileset.GRASS;
                }
            }
        }
        Room lastRoom = rooms.get(rooms.size() - 1);
        for (int l = lastRoom.getX() + 1; l < lastRoom.getWidth(); l++) {
            for (int m = lastRoom.getY() + 1; m < lastRoom.getHeight(); m++) {
                tiles[l][m] = Tileset.GRASS;
            }
        }
    }

    public World loadIn() {
        //Reads in "Save.txt" which is a string representation of the map.
        //Iterates through the whole thing, reconstructing the strings as tiles.
        if (fileExists("Save.txt")) {
            String number = readFile("Seed.txt");
            seed = Long.valueOf(number);
            randomizer = new Random(seed);

            String name = readFile("NameSave.txt");
            menuName = name;

            rooms.clear();
            readableRooms.clear();

            String readRooms = readFile("ReadableRoom.txt");
            readRooms = readRooms.replace("[", "");
            readRooms = readRooms.replace("]", "");
            String[] roomSplit = readRooms.split(", ");
            for (int k = 0; k < roomSplit.length; k++) {
                String[] smallSplit = roomSplit[k].split(",");
                rooms.add(new Room(Integer.valueOf(smallSplit[0]), Integer.valueOf(smallSplit[1]), Integer.valueOf(smallSplit[2]), Integer.valueOf(smallSplit[3])));
                readableRooms.add(Integer.valueOf(smallSplit[0]) + "," + Integer.valueOf(smallSplit[1]) + "," + Integer.valueOf(smallSplit[2]) + "," + Integer.valueOf(smallSplit[3]));
            }

            String scytheNums = readFile("ScytheCoords.txt");
            scytheNums = scytheNums.replace("[", "");
            scytheNums = scytheNums.replace("]", "");
            scytheNums = scytheNums.replace(", ", " ");
            String[] split = scytheNums.split(" ");
            scythe.clear();
            scythe.add(Integer.valueOf(split[0]));
            scythe.add(Integer.valueOf(split[1]));

            String treasureNums = readFile("TreasureCoords.txt");
            treasureNums = treasureNums.replace("[", "");
            treasureNums = treasureNums.replace("]", "");
            treasureNums = treasureNums.replace(", ", " ");
            String[] splits = treasureNums.split(" ");
            treasure.clear();
            treasure.add(Integer.valueOf(splits[0]));
            treasure.add(Integer.valueOf(splits[1]));

            lights.clear();

            lightsOff = true;

            In reader = new In("Save.txt");
            int y = HEIGHT;
            while (!reader.isEmpty()) {
                y -= 1;
                String nextLine = reader.readLine();
                String[] splitLine = nextLine.split("");
                for (int i = 0; i < splitLine.length; i++) {
                    if (Objects.equals(splitLine[i], " ")) {
                        tiles[i][y] = Tileset.NOTHING;
                    } else if (Objects.equals(splitLine[i], "o")) {
                        tiles[i][y] = Tileset.OFF_LIGHT;
                        lights.add(new Lights(i, y));
                    } else if (Objects.equals(splitLine[i], "#")) {
                        tiles[i][y] = Tileset.WALL;
                    } else if (Objects.equals(splitLine[i], "·")) {
                        tiles[i][y] = Tileset.FLOOR;
                    } else if (Objects.equals(splitLine[i], "▲")) {
                        tiles[i][y] = Tileset.MOUNTAIN;
                    } else if (Objects.equals(splitLine[i], "\"")) {
                        tiles[i][y] = Tileset.GRASS;
                    } else if (Objects.equals(splitLine[i], "▒")) {
                        tiles[i][y] = Tileset.SAND;
                    } else if (Objects.equals(splitLine[i], "@")) {

                        tiles[i][y] = Tileset.AVATAR;
                        player = new Player(i, y);
                        movement = new PlayerMovement(player);

                        String condition = readFile("Scythe.txt");
                        if (Objects.equals(condition, "false")) {
                            movement.setHasScythe(false);
                        } else {
                            movement.setHasScythe(true);
                        }

                        String treasures = readFile("Treasure.txt");
                        if (Objects.equals(treasures, "false")) {
                            movement.setHasTreasure(false);
                        } else {
                            movement.setHasTreasure(true);
                        }
                    }
                }
            }
            lightsInOrder();
            hideScytheTreasure();
        }
        return this;
    }

    public void lightsInOrder() {
        lights.clear();
        for (int i = 0; i < rooms.size(); i++) {
            Room lightRoom = rooms.get(i);
            for (int x = lightRoom.getX(); x < lightRoom.getWidth(); x++) {
                for (int y = lightRoom.getY(); y < lightRoom.getHeight(); y++) {
                    if (!outOfRoomBounds(x, y, lightRoom) && tiles[x][y] == Tileset.OFF_LIGHT) {
                        lights.add(new Lights(x, y));
                    }
                }
            }
        }
    }

    public void hideScytheTreasure() {
        tiles[scythe.get(0)][scythe.get(1)] = Tileset.FLOOR;
        tiles[treasure.get(0)][treasure.get(1)] = Tileset.GRASS;
    }

    public void save() {
        writeFile("Save.txt", TETile.toString(tiles)); //saves world
        writeFile("Scythe.txt", String.valueOf(movement.getHasScythe())); //saves hasScythe
        writeFile("Seed.txt", seed.toString()); //saves seed
        writeFile("Treasure.txt", String.valueOf(movement.getHasTreasure())); //saves hasTreasure
        writeFile("ScytheCoords.txt", scythe.toString());
        writeFile("TreasureCoords.txt", treasure.toString());
        writeFile("ReadableRoom.txt", readableRooms.toString());
    }

    public void makeLights() {
        for (int i = 0; i < rooms.size(); i++) {
            ArrayList<Integer> product = newXY(rooms.get(i));
            int newX = product.get(0);
            int newY = product.get(1);
            Lights addition = new Lights(newX, newY);
            lights.add(addition);
            tiles[newX][newY] = Tileset.OFF_LIGHT;
        }
    }

    //adds lights
    public void turnLightsOn() {
        for (int i = 0; i < lights.size(); i++) {
            Lights light = lights.get(i);
            int newX = light.getLightX();
            int newY = light.getLightY();
            if (tiles[newX + 1][newY] == Tileset.FLOOR || tiles[newX][newY + 1] == Tileset.FLOOR || tiles[newX - 1][newY] == Tileset.FLOOR || tiles[newX][newY - 1] == Tileset.FLOOR) {
                tiles[newX][newY] = Tileset.ON_LIGHT;
                addLowestLight(newX, newY, Tileset.FLOOR, Tileset.LOWEST_LIGHT, rooms.get(i));
                addLowestLight(newX, newY, Tileset.GRASS, Tileset.LOWEST_LIGHT_GRASS, rooms.get(i));
                addLowLight(newX, newY, Tileset.LOWEST_LIGHT, Tileset.LOW_LIGHT, rooms.get(i));
                addLowLight(newX, newY, Tileset.LOWEST_LIGHT_GRASS, Tileset.LOW_LIGHT_GRASS, rooms.get(i));
                addMedLight(newX, newY, Tileset.LOW_LIGHT, Tileset.MED_LIGHT, rooms.get(i));
                addMedLight(newX, newY, Tileset.LOW_LIGHT_GRASS, Tileset.MED_LIGHT_GRASS, rooms.get(i));
                addMedHighLight(newX, newY, Tileset.MED_LIGHT, Tileset.MED_HIGH_LIGHT, rooms.get(i));
                addMedHighLight(newX, newY, Tileset.MED_LIGHT_GRASS, Tileset.MED_HIGH_LIGHT_GRASS, rooms.get(i));
                addHighLight(newX, newY, Tileset.MED_HIGH_LIGHT, Tileset.HIGH_LIGHT, rooms.get(i));
                addHighLight(newX, newY, Tileset.MED_HIGH_LIGHT_GRASS, Tileset.HIGH_LIGHT_GRASS, rooms.get(i));
                scytheLight();
                treasureLight();
                player.setPlayer(player.getPosX(), player.getPosY(), tiles);
            }
            if (tiles[newX + 1][newY] == Tileset.GRASS || tiles[newX][newY + 1] == Tileset.GRASS || tiles[newX - 1][newY] == Tileset.GRASS || tiles[newX][newY - 1] == Tileset.GRASS) {
                tiles[newX][newY] = Tileset.ON_LIGHT;
                addLowestLight(newX, newY, Tileset.GRASS, Tileset.LOWEST_LIGHT_GRASS, rooms.get(i));
                addLowestLight(newX, newY, Tileset.FLOOR, Tileset.LOWEST_LIGHT, rooms.get(i));
                addLowLight(newX, newY, Tileset.LOWEST_LIGHT_GRASS, Tileset.LOW_LIGHT_GRASS, rooms.get(i));
                addLowLight(newX, newY, Tileset.LOWEST_LIGHT, Tileset.LOW_LIGHT, rooms.get(i));
                addMedLight(newX, newY, Tileset.LOW_LIGHT_GRASS, Tileset.MED_LIGHT_GRASS, rooms.get(i));
                addMedLight(newX, newY, Tileset.LOW_LIGHT, Tileset.MED_LIGHT, rooms.get(i));
                addMedHighLight(newX, newY, Tileset.MED_LIGHT_GRASS, Tileset.MED_HIGH_LIGHT_GRASS, rooms.get(i));
                addMedHighLight(newX, newY, Tileset.MED_LIGHT, Tileset.MED_HIGH_LIGHT, rooms.get(i));
                addHighLight(newX, newY, Tileset.MED_HIGH_LIGHT_GRASS, Tileset.HIGH_LIGHT_GRASS, rooms.get(i));
                addHighLight(newX, newY, Tileset.MED_HIGH_LIGHT, Tileset.HIGH_LIGHT, rooms.get(i));
                scytheLight();
                treasureLight();
                player.setPlayer(player.getPosX(), player.getPosY(), tiles);
            }
        }
    }

    public void scytheLight() {
        int scytheX = scythe.get(0);
        int scytheY = scythe.get(1);

        if (scytheX == player.getPosX() && scytheY == player.getPosY()) {
            tiles[scytheX][scytheY] = Tileset.SCYTHE_AVATAR;
        }
        else if (movement.getHasScythe() == false) {
            tiles[scytheX][scytheY] = Tileset.MOUNTAIN;
        }
        else if (movement.getHasScythe() == true) {
            tiles[scytheX][scytheY] = Tileset.EMPTY_SCYTHE;
        }
    }
    public void treasureLight() {
        int treasureX = treasure.get(0);
        int treasureY = treasure.get(1);

        if (movement.getHasTreasure() == false) {
            tiles[treasureX][treasureY] = Tileset.SAND;
        }
        else if (movement.getHasTreasure() == true) {
            tiles[treasureX][treasureY] = Tileset.EMPTY_SCYTHE;
        }
    }


    public void addHighLight(int x, int y, TETile onTop, TETile replacement, Room room) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int k = y - 1; k <= y + 1; k++) {
                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == Tileset.AVATAR) {
                    tiles[i][k] = replacement;
                }

                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == onTop) {
                    tiles[i][k] = replacement;
                }
            }
        }
    }

    public void addMedHighLight(int x, int y, TETile onTop, TETile replacement, Room room) {
        for (int i = x - 2; i <= x + 2; i++) {
            for (int k = y - 2; k <= y + 2; k++) {
                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == Tileset.AVATAR) {
                    tiles[i][k] = replacement;
                }

                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == onTop) {
                    tiles[i][k] = replacement;
                }
            }
        }
    }

    public void addMedLight(int x, int y, TETile onTop, TETile replacement, Room room) {
        for (int i = x - 3; i <= x + 3; i++) {
            for (int k = y - 3; k <= y + 3; k++) {
                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == Tileset.AVATAR) {
                    tiles[i][k] = replacement;
                }

                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == onTop) {
                    tiles[i][k] = replacement;
                }
            }
        }
    }

    public void addLowLight(int x, int y, TETile onTop, TETile replacement, Room room) {
        for (int i = x - 4; i <= x + 4; i++) {
            for (int k = y - 4; k <= y + 4; k++) {
                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == Tileset.AVATAR) {
                    tiles[i][k] = replacement;
                }

                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == onTop) {
                    tiles[i][k] = replacement;
                }
            }
        }
    }

    public void addLowestLight(int x, int y, TETile onTop, TETile replacement, Room room) {
        for (int i = x - 5; i <= x + 5; i++) {
            for (int k = y - 5; k <= y + 5; k++) {
                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == Tileset.AVATAR) {
                    tiles[i][k] = replacement;
                }

                if (!outOfRoomBounds(i, k, room) && tiles[i][k] == onTop) {
                    tiles[i][k] = replacement;
                }
            }
        }
    }

    public void turnLightsOff() {
        for (int i = 0; i < lights.size(); i++) {
            Lights light = lights.get(i);
            removeLight(light.getLightX(), light.getLightY());
        }
    }

    public void removeLight(int x, int y) {
        if (scythe.get(0) == player.getPosX() && scythe.get(1) == player.getPosY()) {
            tiles[scythe.get(0)][scythe.get(1)] = Tileset.AVATAR;
        }
        else {
            tiles[scythe.get(0)][scythe.get(1)] = Tileset.FLOOR;
        }
        tiles[treasure.get(0)][treasure.get(1)] = Tileset.GRASS;

        for (int i = x - 5; i <= x + 5; i++) {
            for (int k = y - 5; k <= y + 5; k++) {
                if (!outOfBounds(i, k)) {
                    if (tiles[i][k] == Tileset.ON_LIGHT) {
                        tiles[i][k] = Tileset.OFF_LIGHT;
                    }

                    else if (tiles[i][k] == Tileset.LOWEST_LIGHT_AVATAR || tiles[i][k] == Tileset.SCYTHE_AVATAR) {
                        tiles[i][k] = Tileset.AVATAR;
                    }
                    else if (tiles[i][k] == Tileset.LOW_LIGHT_AVATAR || tiles[i][k] == Tileset.MED_LIGHT_AVATAR) {
                        tiles[i][k] = Tileset.AVATAR;
                    }
                    else if (tiles[i][k] == Tileset.MED_HIGH_LIGHT_AVATAR || tiles[i][k] == Tileset.HIGH_LIGHT_AVATAR) {
                        tiles[i][k] = Tileset.AVATAR;
                    }

                    else if (tiles[i][k] == Tileset.MOUNTAIN || tiles[i][k] == Tileset.EMPTY_SCYTHE) {
                        tiles[i][k] = Tileset.FLOOR;
                    }
                    else if (tiles[i][k] == Tileset.SAND) {
                        tiles[i][k] = Tileset.GRASS;
                    }

                    else if (tiles[i][k] == Tileset.LOWEST_LIGHT) {
                        tiles[i][k] = Tileset.FLOOR;
                    }
                    else if (tiles[i][k] == Tileset.LOW_LIGHT || tiles[i][k] == Tileset.MED_LIGHT) {
                        tiles[i][k] = Tileset.FLOOR;
                    }
                    else if (tiles[i][k] == Tileset.MED_HIGH_LIGHT || tiles[i][k] == Tileset.HIGH_LIGHT) {
                        tiles[i][k] = Tileset.FLOOR;
                    }

                    else if (tiles[i][k] == Tileset.LOWEST_LIGHT_GRASS) {
                        tiles[i][k] = Tileset.GRASS;
                    }
                    else if (tiles[i][k] == Tileset.LOW_LIGHT_GRASS || tiles[i][k] == Tileset.MED_LIGHT_GRASS) {
                        tiles[i][k] = Tileset.GRASS;
                    }
                    else if (tiles[i][k] == Tileset.MED_HIGH_LIGHT_GRASS || tiles[i][k] == Tileset.HIGH_LIGHT_GRASS) {
                        tiles[i][k] = Tileset.GRASS;
                    }
                }
            }
        }
    }


    // Returns all the tiles inside the world;
    public TETile[][] getTiles() {
        return tiles;
    }

    public TETile getOneTile(int x, int y) {
        return tiles[x][y];
    }

    public int getGameWidth() {
        return WIDTH;
    }

    public int getGameHeight() {
        return HEIGHT;
    }

    public boolean getLightsOff() {
        return lightsOff;
    }

    public void setLightsOff(Boolean value) {
        lightsOff = value;
    }


}
