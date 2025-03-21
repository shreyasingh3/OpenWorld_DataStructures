package core;

import tileengine.TETile;
import tileengine.Tileset;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {
        input = input.toLowerCase();
        if (input.contains("n")) {
            String numbers = onlyNumbers(input); //returns string of only numbers
            Long seed = Long.valueOf(numbers); //convert to Long
            World world = new World(seed); //create new world with that seed

            //gets rid of everything but the player movements and quit/save
            input = input.replace("n", "");
            input = input.replaceFirst("s", "");
            input = input.replace(numbers, "");

            Boolean colonCheck = false;

            char[] letters = input.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                char character = letters[i];
                if (character == 'a') {
                    world.movement.moveLeft(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == 'w') {
                    world.movement.moveUp(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == 's') {
                    world.movement.moveDown(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == 'd') {
                    world.movement.moveRight(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == ':') {
                    colonCheck = true;
                } else if (character == 'q') {
                    if (colonCheck) {
                        world.save();
                        return world.getTiles();
                    }
                } else if (character == 'e') {
                    colonCheck = false;
                    if (world.getLightsOff()) {
                        world.turnLightsOn();
                        world.setLightsOff(false);
                    }
                    else {
                        world.turnLightsOff();
                        world.setLightsOff(true);
                    }
                }
            }
            return world.getTiles();

        } else { //if it starts with L instead of N
            World world = new World(1L);
            world = world.loadIn();

            input = input.replace("l", "");

            Boolean colonCheck = false;
            char[] letters = input.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                char character = letters[i];
                if (character == 'a') {
                    world.movement.moveLeft(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == 'w') {
                    world.movement.moveUp(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == 's') {
                    world.movement.moveDown(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == 'd') {
                    world.movement.moveRight(world.player.getPosX(), world.player.getPosY(), world.getTiles());
                    colonCheck = false;
                } else if (character == ':') {
                    colonCheck = true;
                } else if (character == 'q') {
                    if (colonCheck) {
                        world.save();
                        return world.getTiles();
                    }
                }
            }
            return world.getTiles();
        }
    }

    public static String onlyNumbers(String input) {
        input = input.replaceAll("n", "");
        input = input.replaceAll("s", "");
        input = input.replaceAll(":", "");
        input = input.replaceAll("q", "");
        input = input.replaceAll("a", "");
        input = input.replaceAll("w", "");
        input = input.replaceAll("d", "");
        input = input.replaceAll("l", "");
        input = input.replaceAll("e","");
        return input;
    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
