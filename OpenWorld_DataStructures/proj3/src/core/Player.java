package core;

import tileengine.TETile;
import tileengine.Tileset;

public class Player {
    private int posX;
    private int posY;
    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setPlayer(int x, int y, TETile[][] tiles) {
        if (tiles[x][y] == Tileset.FLOOR || tiles[x][y] == Tileset.GRASS) {
            tiles[x][y] = Tileset.AVATAR;
        }
        else if (tiles[x][y] == Tileset.LOWEST_LIGHT || tiles[x][y] == Tileset.LOWEST_LIGHT_GRASS) {
            tiles[x][y] = Tileset.LOWEST_LIGHT_AVATAR;
        }
        else if (tiles[x][y] == Tileset.LOW_LIGHT || tiles[x][y] == Tileset.LOW_LIGHT_GRASS) {
            tiles[x][y] = Tileset.LOW_LIGHT_AVATAR;
        }
        else if (tiles[x][y] == Tileset.MED_LIGHT || tiles[x][y] == Tileset.MED_LIGHT_GRASS) {
            tiles[x][y] = Tileset.MED_LIGHT_AVATAR;
        }
        else if (tiles[x][y] == Tileset.MED_HIGH_LIGHT || tiles[x][y] == Tileset.MED_HIGH_LIGHT_GRASS) {
            tiles[x][y] = Tileset.MED_HIGH_LIGHT_AVATAR;
        }
        else if (tiles[x][y] == Tileset.HIGH_LIGHT || tiles[x][y] == Tileset.HIGH_LIGHT_GRASS) {
            tiles[x][y] = Tileset.HIGH_LIGHT_AVATAR;
        }
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return this.posY;
    }

}
