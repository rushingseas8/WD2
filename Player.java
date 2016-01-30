
/**
 * This is you. Say hi!
 * You have a level, an amount of health, possibly an amount of mana, certain slots for armor that
 * may or may not be filled, an inventory, among other things.
 * 
 * Spanning from the ideas of the original Warping Dungeons game, it appears that you'll have an
 * amount of space dedicated to your magical knowledge, your Magic Armor, 
 * 
 * @version July 7, 2013
 */
public class Player
{
    //Your current health    
    public int currentHealth;
    //Your maximum health
    public int totalHealth;
    
    //Your current mana
    public int currentMana;
    //Your maxiumum mana
    public int totalMana;
    
    //Your experience
    public int experience;
    
    //Your floor number
    public int floorNum;
    
    //Your current floor
    public Floor floor;
    
    //Your current location
    public Location loc;
    
    //Your (primary) inventory; all of your equipped weapons, armor, etc
    /*
     * Includes:
     * *Head
     * *Chest
     * *Legs
     * *Gloves
     * *Boots
     * *Ring A
     * *Ring B
     * *Necklace
     * *Weapon
     * *Offhand/Shield
     * *Quiver [allows for carrying arrows not in your backpack; saves space but limited room; you can select without penalty if you have more than one type]
     * *Bolt pouch [basically the same as the quiver]
     *      <An idea could be that you start out only able to hold low level arrows, higher tiers = higher level arrows>
     * *Rune pouch [same as quiver and bolt pouch; Starts out holding elemental runes, higher levels
     *      of pouches can hold higher level runes and increases quantities of each]
     *
     */
    public int[] equipped = new int[13];
    //Your (secondary) inventory; your backpack and its contents
    public int[] inventory = new int[20];
    
    //Creating you.
    public Player() {
        currentHealth = totalHealth = 100;
        currentMana = totalMana = 0;
        experience = 0;
        
        floorNum = 1;
    }
    
    public Player(Location spawn) {
        currentHealth = totalHealth = 100;
        currentMana = totalMana = 0;
        experience = 0;
        
        floorNum = 1;
        
        loc = spawn;
    }
    
    //Assigns you to a floor. Or, specifically, the other way around. Same concept
    public void setFloor(Floor f) {
        floor = f;
    }
    
    //Moves you one tile in a certain direction, given certain conditions.
    public void move(int dir) {
        Location temp = loc.getLocationInDir(dir);
        if (floor.isEmptyAndValid(temp)) {
            Room temp2 = floor.thisFloor[temp.getX()][temp.getY()];
            for (int i = 0; i < temp2.getConnections().size(); i++) {
                if (dir == temp2.getConnections().get(i)) { //If the direction to be moved in is a direction the room has a connection to, then continue
                    loc = temp;
                    System.out.println("INFO: Successfully moved in direction " + dir + ".");
                    break;
                } else {
                    //System.out.println("WARNING: Attempted to move in a direction in which this room is not connected to.");
                }
            }
        } else {
            System.out.println("WARNING: Attempted to move to a room that is nonexistant or invalid.");
        }
        
    }
    
    //Sets your location to a new one.
    //@precondition: loc must not be null/invalid
    public void setLocation(Location loc) {
        this.loc = loc;
    }
    
    //Default picking up; works if there is one item of one type in the room
    public void pickUp() {
        
    }
}
