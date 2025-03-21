package core;

import java.util.Comparator;

public class XComparator implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        if (o1.getX() <= o2.getX()) {
            return -1;
        } else {
            return 1;
        }
    }
}
