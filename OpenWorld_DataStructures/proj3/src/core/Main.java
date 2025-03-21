package core;

import tileengine.TERenderer;


import static edu.princeton.cs.algs4.StdDraw.*;
import static utils.FileUtils.readFile;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.renderMenu();
        while (mainMenu.whileMenu()) {
            mainMenu.checkMenuInput();
        }

        while (mainMenu.whileNamePrompt()) {
            mainMenu.nameValue();
        }

        while (mainMenu.whileUpdatedMenu()) {
            mainMenu.checkMenuInput();
            while (mainMenu.whileNamePrompt()) {
                mainMenu.nameValue();
            }
        }

        while (mainMenu.whileSeedPrompt()) {
            mainMenu.seedValue();
        }

        World world = new World(1L);


        if (mainMenu.reload) {
            String seed = readFile("Seed.txt");
            world.setMenuName(mainMenu.name);
            world = world.loadIn();
        } else if (!mainMenu.reload) {
            Long seed = Long.valueOf(mainMenu.input);
            world = new World(seed);
            world.setMenuName(mainMenu.name);
        }


        TERenderer ter = new TERenderer();
        ter.initialize(world.getGameWidth(), world.getGameHeight());
        ter.renderFrame(world.getTiles());

        while (!world.isGameOver()) {
            ter.drawTiles(world.getTiles());
            world.updateWorld();
        }
        setCanvasSize();
        while (world.gameOver()) {
            mainMenu.gameOverScreen();
        }
    }


}
