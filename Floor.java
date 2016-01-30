import java.util.ArrayList;
import java.util.Random;

/**
 * The class that handles all the stuff that happens on a given floor; 
 * 
 * In addition, this class has code that generates the floor and every room in it, the way
 * they all connect, where the boss room is, where the locks and keys are, and so on.
 * 
 * A floor consists of rooms based on the size of the dungeon, and is basically a randomly
 * made maze of rooms. You can go left, right, forward, and back in these rooms to travel
 * between them, but only after you've cleared the room/ begun to dodge (where you ignore the
 * enemies, and can always start combat, but take normal damage at a 75% rate).
 * 
 * @author (your name) 
 * @version 0.1
 * 
 * @revision 0.1:
 *  *Basic skeleton; creates a new floor of the given size with all rooms filled in.
 */
public class Floor
{   
    /*
     * A quick summary on floors:
     * <Regular floors>
     * :1-20 are type I (Basic, regular and uncorrupted monsters, simple)
     * :21-40 are type II (More complex, like a medium level dungeon but similar to above. More lore)
     * :41-60 are type III (Slightly warped, things start to lash out at you like the weapons and plants)
     * :61-80 are type IV (Bulky and warlike; Wicked. As if everything here wants to kill you. Brutish.)
     * :81-100 are type V (Primal. Everything is streamlined to kill you. Warped by evil magicks. Contains the final boss.)
     * 
     * <Subprimal floors>
     * :100-120 are type VI (Sub-primal. Curved, elegant, and very, very powerful. As if defeated the above evil.)
     * :120-150 are type VII (A corrupted version of VI, as the people who thought they defeated the corruption were taken over themselves. Even more powerful.)
     * :150-200 are type VIII (Pure, utter corruption. Nobody has been here before; Simple and deadly. Like an elegant version of V. Simply fancy.)
     * 
     * <Insanity floors>
     * :200-249 are type IX (Same art design as in VIII, but things get much more extreme. This is where the coders should NOT hold back, making it nigh impossible to win.)
     * Finally. Floor 250 is type X. Most, if not all, the bosses are on this floor instead of monsters. True final boss and the source of the corruption.
     */

    //The level of warpedness. Can be calculated from the floor number, but this makes things easier and also helps to keep track when you're creating rooms and such.
    public int warpedLevel;

    Random random = new Random();

    /** 
     * The size of the dungeon; Larger dungeons = bigger bonuses.
     * 0 = small (3x3)
     * 1 = medium (4x4)
     * 2 = large (5x5)
     * 3 = extreme (7x7)
     */
    private int size;

    //Determines if this floor is over or not.
    public boolean isOver = false;

    //The array that contains the rooms in the floor
    public Room[][] thisFloor;

    //The X coordinate of the current room in the floor
    public int roomX;

    //The Y coordinate of the current room in the floor
    public int roomY;

    //Important info for a floor loop.. See main method.
    public Room start;
    public Room end;

    //Used in a generateIII helper method to try to fill up some extra spaces.
    private double roomSaturation;

    public Floor(int sz, int wl)
    {
        warpedLevel = wl;
        size = sz;

        //Creates the rooms [Very basic as of version July 7, 2013. Will add a system wherein each room has four "connecters" and each will be turned on based on what has a door to it.
        //This will be done via a snaking system: Start by aiming north; Roll the dice, if pass, then search for an available path (north of start); Roll the dice and pick one of those paths;
        //Create a room there; The direction moved will be open to start, the opposite of the direction moved will be open to the new room. Repeat until you fail the random chance of searching
        //for a new path. This will ideally fill in as many random areas as possible, BUT does not allow for forked paths. This will be added, again, later.]

        System.out.println("**Generation log**");
        thisFloor = generateI();
        thisFloor = generateII(thisFloor);
        thisFloor = generateIII(thisFloor);
        generateEnd();
        System.out.println("**Floor generation complete!**");
    }

    public int getSize() {
        return size;
    }

    public int getWL() {
        return warpedLevel;
    }

    //The initial method that generates a skeleton of a floor; makes the 2D array of rooms the proper size and fills in all the spaces with Rooms. Will just make the empty array when built upon.
    private Room[][] generateI() {
        System.out.println("INFO: generateI has started");

        //Assigns a size based on size parameter
        switch(size) {
            //3x3
            case 0:
            thisFloor = new Room[3][3]; break;
            //4x4
            case 1:
            thisFloor = new Room[4][4]; break;
            //5x5
            case 2:
            thisFloor = new Room[5][5]; break;
            //7x7
            case 3:
            thisFloor = new Room[7][7]; break;
            //Just in case; this is to prevent the errors from being TOO bad
            default: thisFloor = new Room[1][1]; System.out.println("WARNING: Error in parameters in method 'generateBasic' in class 'Floor'");
        }

        System.out.println("INFO: instantiated floor");

        return thisFloor;
    }

    //Takes an empty floor and fills it up with a simple algorithm. Essentially uses decay to randomly fill up spaces in simple paths. Only does a single path.
    private Room[][] generateII(Room[][] emptyFloor) {
        System.out.println("INFO: generateII has started");

        int seedCoords = emptyFloor.length/2;
        Room seed = new Room(warpedLevel, new Location(seedCoords, seedCoords));

        //Adds a new Room at the middle of the floor; a seed.
        emptyFloor[seedCoords][seedCoords] = seed;

        System.out.println("INFO: added new room at location " + seedCoords + ", " + seedCoords);

        recursiveGenerate(seed);
        return emptyFloor;
    }

    //Creates at least 2 but no more than 4 unique paths in this floor.
    private Room[][] generateIII(Room[][] genIIFloor) {
        System.out.println("INFO: generateIII has started");

        Room temp = genIIFloor[genIIFloor.length/2][genIIFloor.length/2];

        //This creates paths at random, for a total of 4 at the most.
        recursiveGenerate(temp);
        recursiveGenerate(temp);
        recursiveGenerate(temp);

        System.out.println("INFO: There are " + temp.getConnections().size() + " paths.");

        genIIFloor = generation3Fixer1(genIIFloor);

        System.out.println("INFO: There are " + temp.getConnections().size() + " paths.");        

        return genIIFloor;
    }

    //
    //private Room[][] generateIV(Room[][] genIIIFloor) {

    //}

    //Looks around a given room; gets a list of unconnected areas; randomizes the list; 7/8th chance of continuing; if the selected area is empty and acceptable..
    //Place a new room there and use the new room as a seed. Repeat until there are no possible connections left, or we try to run into ourselves.
    private void recursiveGenerate(Room r) {
        for (int a: generation2Fixer1(r.getOpenConnections())) { //Scans for any open possible-connections, goes through them clockwise starting @north
            if (random.nextInt(8) != 0) { //7/8 chance of making a new path here, if this fails, we move on; if it succeeds, continue
                //int a = random.nextInt(r.getOpenConnections().size()); //Randomly picks an open possible-connection
                Location temp = r.getLocation().getLocationInDir(a); //The location that is one tile in the proper direction of the starting point
                if (isValidLocation(temp)) { //and if this location is in the valid floor parameters..
                    if (thisFloor[temp.getX()][temp.getY()] == null) { //If this location is currently devoid of a Room..

                        Room temp2 = new Room(warpedLevel, temp); //Create a new Room.

                        r.connect(a); //Connect the seed room to the new Room in the proper direction.
                        temp2.connect((a + 2) % 4); //Connect the new Room to the seed room in the opposite direction. 

                        thisFloor[temp.getX()][temp.getY()] = temp2; //Goes to the new location in the floor, and assigns to it the newly generated Room.
                        System.out.println("INFO: added new room at location " + temp2.getLocation().getX() + ", " + temp2.getLocation().getY()); 

                        String dir = "";

                        switch(a) {
                            case 0: dir = "NORTH"; break;
                            case 1: dir = "EAST"; break;
                            case 2: dir = "SOUTH"; break;
                            case 3: dir = "WEST"; break;
                            default: dir = "ERROR: DIRECTION";
                        }

                        System.out.println("INFO: added connection to room at " + r.getLocation().getX() + ", " + r.getLocation().getY() + " towards direction " + dir);
                        //System.out.println("INFO: connected room at " + temp2.getLocation().getX() + ", " + temp2.getLocation().getY() + " at direction " + ((a+2)%4));

                        recursiveGenerate(temp2); //Continues to search, using the freshly made room as a seed.

                        break; //Stops looking for more paths. Prevents splits. Will be changed in version III.

                    } else {
                        //**Added with genIII
                        System.out.println("WARNING: recursiveGenerate tried to run into itself. Ending.");
                        break;
                    }                        
                } else {
                    //Ends the loop if it tries to generate in an impossible location.
                    System.out.println("WARNING: recursiveGenerate tried to make a room in an impossible location. Rerouting.");
                    a++;
                    //break;
                }
            }
        }
    }

    private void generateEnd() {
        start = thisFloor[thisFloor.length/2][thisFloor.length/2];

        System.out.println("INFO: the start room is at " + start.getLocation().getX() + ", " + start.getLocation().getY());        

        ArrayList<Room> deadends = new ArrayList<Room>();
        for (int a = 0; a < thisFloor.length/2; a++) {
            for (int b = 0; b < thisFloor.length/2; b++) {
                if (thisFloor[a][b] != null) {
                    if (thisFloor[a][b].getConnections().size() == 1) {
                        deadends.add(thisFloor[a][b]);
                    }
                }
            }
        }

        if (deadends.size() > 0) {
            end = deadends.get(random.nextInt(deadends.size()));      
            System.out.println("INFO: the boss room is at " + end.getLocation().getX() + ", " + end.getLocation().getY());            
        } else {
            System.out.println("ERROR: no end room!");
        }

    }

    //This makes the room generation unbiased; eliminates north->east->south->west bias. 
    private ArrayList<Integer> generation2Fixer1(ArrayList<Integer> unscrambled) {
        //System.out.println("BUGFIXER: preventing NESW bias..");
        java.util.Collections.shuffle(unscrambled, new Random(System.nanoTime()));
        System.out.println("BUGFIXER: randomised direction of generation");
        return unscrambled;
    }

    //This makes sure that there are at least 2 paths, but no more than 4.
    private Room[][] generation3Fixer1(Room[][] floorToFix) {
        System.out.println("BUGFIXER: making sure there are 2-4 paths..");
        ArrayList<Integer> connections = floorToFix[floorToFix.length/2][floorToFix.length/2].getConnections();

        if(connections.size() < 2) {
            recursiveGenerate(floorToFix[floorToFix.length/2][floorToFix.length/2]);
            System.out.println("BUGFIXER: added a new path!");
        }

        System.out.println("BUGFIXER: done!");
        return floorToFix;
    }

    //A public method that determines first if the location is in the floor, and if it contains a room.
    public boolean isEmptyAndValid(Location location) {
        return isValidLocation(location) && !isEmptyLocation(location);
    }

    //Tests if this Location is within the bounds of this floor
    private boolean isValidLocation(Location location) {
        Location loc = location;
        if (loc.getX() >= 0 && loc.getX() < thisFloor.length) {
            if (loc.getY() >= 0 && loc.getY() < thisFloor[0].length) {
                return true;
            }
        }
        return false;
    }

    //Tests if there is something in here
    private boolean isEmptyLocation(Location location) {
        return thisFloor[location.getX()][location.getY()] == null;
    }

    public boolean isOver() {
        return isOver;
    }

    //Prints out a visual representation [map] of this floor. For debugging.
    public void print() {
        for (int a = 0; a < thisFloor.length; a++) {
            for (int b = 0; b < thisFloor[0].length; b++) {
                if (thisFloor[a][b] == null) {
                    System.out.print("_ "); //Literally nothing here.
                }
                else if (thisFloor[a][b] == null) {
                    System.out.print("X "); //This is an empty space.
                }
                else if (thisFloor[a][b] != null) {
                    Room temp = thisFloor[a][b];

                    if (temp.getConnections().size() == 0) {
                        System.out.print("R "); //Unconnected Room? What?
                    }

                    else if (temp.getConnections().size() == 1) {
                        System.out.print("D "); //Dead end!
                    }

                    else if (temp.getConnections().size() == 2) {
                        switch(temp.getConnections().get(0)) {
                            case 0:
                            switch(temp.getConnections().get(1)) {
                                case 1: System.out.print("\\ "); break;
                                case 2: System.out.print("| "); break;
                                case 3: System.out.print("/ "); break;
                                default: System.out.print("? "); 
                            }
                            break;
                            case 1: 
                            switch(temp.getConnections().get(1)) {
                                case 0: System.out.print("\\ "); break;
                                case 2: System.out.print("/ "); break;
                                case 3: System.out.print("- "); break;
                            }
                            break;
                            case 2: 
                            switch(temp.getConnections().get(1)) {
                                case 0: System.out.print("| "); break;
                                case 1: System.out.print("/ "); break;
                                case 3: System.out.print("\\ "); break;
                            }
                            break;
                            case 3: 
                            switch(temp.getConnections().get(1)) {
                                case 0: System.out.print("/ "); break;
                                case 1: System.out.print("- "); break;
                                case 2: System.out.print("\\ "); break;
                            }
                            break;
                            default: System.out.print("0 "); //Error of some sort. How!?
                        }
                    }

                    else if (temp.getConnections().size() > 2) {
                        System.out.print("+ "); //Will be fixed later.
                    }

                    else {
                        System.out.print("? "); //Some kind of connection slip-up
                    }
                }
                else {
                    System.out.print("? "); //What happened?
                }
            }
            System.out.println();
        }
    }
}
