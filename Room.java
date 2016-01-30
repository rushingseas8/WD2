import java.util.Random;
import java.util.ArrayList;

/**
 * A room object. Contains:
 * -monsters (up to 4)
 * -resources (up to 2/3 unique ones)
 * -utilities (up to 1)
 * -treasure (up to 1)
 * -other
 * 
 * @version 0.1
 */
public class Room
{
    //The array (arraylist) of items in this room that you can manipulate.
    protected ArrayList<Object> interactable = new ArrayList<Object>();

    //Random number generator
    Random random = new Random();

    //The level of warped-ness. Can be determined from the floor, but eh. Laziness. Might fix later
    protected int warpedLevel = -1;

    //These are used to determine where this room is connected to others, and thus where doors are.
    boolean isNorthConnected, isEastConnected, isSouthConnected, isWestConnected;
    
    //X and Y coordinates, these are solely for the sake of making it easier to generate floors.
    //Could be eliminated later on.
    Location loc;

    //Add a random number generator for all of the containable stuff
    //Create new onjects for all of these interactables
    //Add an "examine" command that gives a small list of everything in the room
    
    //Default constructor 
    public Room(int wl, Location location) {
        warpedLevel = wl;
        loc = location;
    }    
    
    //A unified method that adds a connection given a direction to add it in.
    public void connect(int dir) {
        switch(dir) {
            case 0: connectNorth(); break;
            case 1: connectEast(); break;
            case 2: connectSouth(); break;
            case 3: connectWest(); break;
            default: System.out.println("WARNING: error in parameter in method 'connect(int dir)' in class Room");
        }
    }
    
    //These following are methods used in the Floor class for generating the floors; 
    //**Changed to helper methods; now use the connect(int dir) method!    
    private void connectNorth() {
        isNorthConnected = true;
    }
    
    private void connectEast() {
        isEastConnected = true;
    }
    
    private void connectSouth() {
        isSouthConnected = true;
    }
    
    private void connectWest() {
        isWestConnected = true;
    }    
    
    //Gives a list of all the OPEN connections this room has; used in Floor to help generate rooms
    public ArrayList<Integer> getOpenConnections() {
        ArrayList<Integer> toReturn = new ArrayList<Integer>();
        if (!isNorthConnected) {
            toReturn.add(0);
        }
        if (!isEastConnected) {
            toReturn.add(1);
        }
        if (!isSouthConnected) {
            toReturn.add(2);
        }
        if (!isWestConnected) {
            toReturn.add(3);
        }
        return toReturn;
    }
    
    //Gives a list of all the connections this room has; used in Floor to help generate rooms [Might be used in more advanced codes for pathfinding or similar]
    public ArrayList<Integer> getConnections() {
        ArrayList<Integer> toReturn = new ArrayList<Integer>();
        if (isNorthConnected) {
            toReturn.add(0);
        }
        if (isEastConnected) {
            toReturn.add(1);
        }
        if (isSouthConnected) {
            toReturn.add(2);
        }
        if (isWestConnected) {
            toReturn.add(3);
        }
        return toReturn;
    }    
    
    public Location getLocation() {
        return loc;
    }
}
