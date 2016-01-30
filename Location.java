
/**
 * A simple Location class. This will help in arranging Floors, as each Room will be assigned a 
 * Location instead of an x and a y, and each floor will be an array of Rooms, from which a location
 * is easily accessible.
 *
 * @version July 9, 2013
 */
public class Location
{
    //X variable of this location
    private int x;

    //Y variable of this location
    private int y;
    
    //The contents of this location, empty or contains a room (or a special room)
    private Room contained = null;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location getLocationInDir(int dir) {
        switch (dir) {
            case 0: return new Location(x-1, y); //North
            case 1: return new Location(x, y+1); //East
            case 2: return new Location(x+1, y); //South
            case 3: return new Location(x, y-1); //West
            default: return null; 
        }
    }
    
    public Room get() {
        return contained;
    }
    
    public void assign(Room thisRoom) {
        contained = thisRoom;
    }
}
