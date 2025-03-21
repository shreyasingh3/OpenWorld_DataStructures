package core;

import tileengine.TETile;
import tileengine.Tileset;

public class PlayerMovement {
    private Player player;
    private Boolean hasScythe;
    private Boolean hasTreasure;
    private Boolean treasureCheck;
    private Boolean fromOff;
    private Boolean fromOn;

    public PlayerMovement(Player player) {
        this.player = player;
        hasScythe = false;
        hasTreasure = false;
        treasureCheck = false;
        fromOff = false;
        fromOn = false;
    }

    public TETile playerTile(int x, int y, TETile[][] tiles) {
        if (fromOn) {
            fromOn = false;
            return Tileset.ON_LIGHT;
        }
        else if (fromOff) {
            fromOff = false;
            return Tileset.OFF_LIGHT;
        }

        else if (tiles[x][y] == Tileset.SCYTHE_AVATAR) {
            return Tileset.EMPTY_SCYTHE;
        }
        else if (tiles[x][y] == Tileset.LOWEST_LIGHT_AVATAR) {
            return Tileset.LOWEST_LIGHT;
        }
        else if (tiles[x][y] == Tileset.LOW_LIGHT_AVATAR) {
            return Tileset.LOW_LIGHT;
        }
        else if (tiles[x][y] == Tileset.MED_LIGHT_AVATAR) {
            return Tileset.MED_LIGHT;
        }
        else if (tiles[x][y] == Tileset.MED_HIGH_LIGHT_AVATAR) {
            return Tileset.MED_HIGH_LIGHT;
        }
        else if (tiles[x][y] == Tileset.HIGH_LIGHT_AVATAR) {
            return Tileset.HIGH_LIGHT;
        }
        return Tileset.FLOOR;
    }


    public void tileCheck(int oldX, int oldY, int newX, int newY, TETile[][] tiles) {
        if (tiles[newX][newY] == Tileset.FLOOR || hasScythe && tiles[newX][newY] == Tileset.GRASS) {
            tiles[newX][newY] = Tileset.AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.LOWEST_LIGHT || hasScythe && tiles[newX][newY] == Tileset.LOWEST_LIGHT_GRASS) {
            tiles[newX][newY] = Tileset.LOWEST_LIGHT_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.LOW_LIGHT || hasScythe && tiles[newX][newY] == Tileset.LOW_LIGHT_GRASS) {
            tiles[newX][newY] = Tileset.LOW_LIGHT_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.MED_LIGHT || hasScythe && tiles[newX][newY] == Tileset.MED_LIGHT_GRASS) {
            tiles[newX][newY] = Tileset.MED_LIGHT_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.MED_HIGH_LIGHT || hasScythe && tiles[newX][newY] == Tileset.MED_HIGH_LIGHT_GRASS) {
            tiles[newX][newY] = Tileset.MED_HIGH_LIGHT_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.HIGH_LIGHT || hasScythe && tiles[newX][newY] == Tileset.HIGH_LIGHT_GRASS) {
            tiles[newX][newY] = Tileset.HIGH_LIGHT_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.MOUNTAIN) {
            tiles[newX][newY] = Tileset.SCYTHE_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
            hasScythe = true;
        }
        else if (tiles[newX][newY] == Tileset.SAND) {
            tiles[newX][newY] = Tileset.SCYTHE_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
            hasTreasure = true;
        }
        else if (tiles[newX][newY] == Tileset.EMPTY_SCYTHE) {
            tiles[newX][newY] = Tileset.SCYTHE_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
        }
        else if (tiles[newX][newY] == Tileset.ON_LIGHT) {
            tiles[newX][newY] = Tileset.HIGH_LIGHT_AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
            fromOn = true;
        }
        else if (tiles[newX][newY] == Tileset.OFF_LIGHT) {
            tiles[newX][newY] = Tileset.AVATAR;
            player.setPosX(newX);
            player.setPosY(newY);
            tiles[oldX][oldY] = playerTile(oldX, oldY, tiles);
            fromOff = true;
        }
    }

    public void moveLeft(int posX, int posY, TETile[][] tiles) {
        if (hasTreasure) {
            treasureCheck = true;
        }

        tileCheck(posX, posY, posX - 1, posY, tiles);
    }

    public void moveRight(int posX, int posY, TETile[][] tiles) {
        if (hasTreasure) {
            treasureCheck = true;
        }

        tileCheck(posX, posY,posX + 1, posY, tiles);

    }


    public void moveUp(int posX, int posY, TETile[][] tiles) {
        if (hasTreasure) {
            treasureCheck = true;
        }

        tileCheck(posX, posY, posX, posY + 1, tiles);
    }


    public void moveDown(int posX, int posY, TETile[][] tiles) {
        if (hasTreasure) {
            treasureCheck = true;
        }

        tileCheck(posX, posY, posX, posY - 1, tiles);
    }





    public void setHasScythe(boolean scythe) {
        this.hasScythe = scythe;
    }

    public boolean getHasScythe() {
        return hasScythe;
    }

    public void setHasTreasure(boolean treasure) {
        this.hasTreasure = treasure;
    }

    public boolean getHasTreasure() {
        return hasTreasure;
    }

    public boolean getTreasureCheck() {
        return treasureCheck;
    }

}
