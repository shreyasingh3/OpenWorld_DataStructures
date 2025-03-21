package core;

import tileengine.TETile;
import tileengine.Tileset;

public class Lights {
    private int x;
    private int y;

    public Lights(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getLightX() {
        return this.x;
    }

    public int getLightY() {
        return this.y;
    }
}
