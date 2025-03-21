package tileengine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', new Color(3, 211, 252), Color.black, "you");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");

    public static final TETile EMPTY_SCYTHE = new TETile(' ', Color.yellow, Color.yellow, "no scythe");



    public static final TETile LOWEST_LIGHT = new TETile('·', new Color(128, 192, 128), new Color(31, 31, 31), "low light");
    public static final TETile LOW_LIGHT = new TETile('·', new Color(128, 192, 128), Color.darkGray, "low light");
    public static final TETile MED_LIGHT = new TETile('·', new Color(128, 192, 128), Color.gray, "medium light");
    public static final TETile MED_HIGH_LIGHT = new TETile('·', new Color(128, 192, 128), Color.lightGray, "medium light");
    public static final TETile HIGH_LIGHT = new TETile('·', new Color(128, 192, 128), Color.white, "high light");


    public static final TETile LOWEST_LIGHT_GRASS = new TETile('"', new Color(128, 192, 128), new Color(31, 31, 31), "low light");
    public static final TETile LOW_LIGHT_GRASS = new TETile('"', new Color(128, 192, 128), Color.darkGray, "low light");
    public static final TETile MED_LIGHT_GRASS = new TETile('"', new Color(128, 192, 128), Color.gray, "medium light");
    public static final TETile MED_HIGH_LIGHT_GRASS = new TETile('"', new Color(128, 192, 128), Color.lightGray, "medium light");
    public static final TETile HIGH_LIGHT_GRASS = new TETile('"', new Color(128, 192, 128), Color.white, "high light");

    public static final TETile LOWEST_LIGHT_AVATAR = new TETile('@', new Color(3, 211, 252), new Color(31, 31, 31), "low light");
    public static final TETile LOW_LIGHT_AVATAR = new TETile('@', new Color(3, 211, 252), Color.darkGray, "low light");
    public static final TETile MED_LIGHT_AVATAR = new TETile('@', new Color(3, 211, 252), Color.gray, "medium light");
    public static final TETile MED_HIGH_LIGHT_AVATAR = new TETile('@', new Color(3, 211, 252), Color.lightGray, "medium light");
    public static final TETile HIGH_LIGHT_AVATAR = new TETile('@', new Color(3, 211, 252), Color.white, "high light");
    public static final TETile SCYTHE_AVATAR = new TETile('@', new Color(3, 211, 252), Color.yellow, "scythe avatar");


    public static final TETile OFF_LIGHT = new TETile('o', Color.WHITE, Color.BLACK, "off light");
    public static final TETile ON_LIGHT = new TETile('o', Color.BLACK, Color.WHITE, "on light");


    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.MAGENTA, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.magenta, Color.YELLOW, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


