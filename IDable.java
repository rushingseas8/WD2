
/**
 * The broadest class of things. Everything here has to have an ID, and this class has a few methods
 * to help deal with confusion.
 * 
 * @version 0.1
 */
public class IDable
{
    /*
     * A quick summary on ID's:
     * :1-1000 are enemies (This doesn't include levels; those are unique to each eney object)
     *  *1-100 are type I (basic) 
     *  *101-200 are type II (intermediate)
     *  *etc..
     *  *901-1000 are type X (warped bosses)
     * :1001-2000 are resources
     *  *Every tier of 100 is a different floor
     * :2001-3000 are utilities
     *  *See above
     * :3001-4000 are unused.
     * :4001-9999 are unused.
     * :10000+ are item IDs.
     */


    //The ID of this object
    protected int ID;

    //IDs for enemies from floors 1-20
    protected String[] enemiesI = {
            "Mouse",
            "Rat",
            "Small slime",
            "Goblin", 
        };

    //IDs for enemies from floors 21-40    
    protected String[] enemiesII = {};
    //IDs for enemies from floors 41-60    
    protected String[] enemiesIII = {};
    //IDs for enemies from floors 61-80   
    protected String[] enemiesIV = {};
    //IDs for enemies from floors 81-100   
    protected String[] enemiesV = {};
    //IDs for enemies from floors 101-120  
    protected String[] enemiesVI = {};
    //IDs for enemies from floors 121-150
    protected String[] enemiesVII = {};
    //IDs for enemies from floors 151-200 
    protected String[] enemiesVIII = {};
    //IDs for enemies from floors 201-249    
    protected String[] enemiesIX = {};
    //IDs for enemies on floor 250 
    protected String[] enemiesX = {};    

    //A helper method that gives the name of the object given the ID, or null if not found.
    protected String getNameFromID(int ID) {
        
        if (ID > 0 && ID < 101) {
            if (ID <= enemiesI.length) {
                return enemiesI[ID];
            } 
            return null;
        }
        
        if (ID > 100 && ID < 201) {
            if (ID - 100 <= enemiesII.length) {
                return enemiesII[ID-100];
            }
            return null;
        }
        return null;
    }
    
    //A helper method that gives the ID of the object given the name, or -1 if not found.
    protected int getIDFromName(String name) {
        for (int a = 0; a < enemiesI.length; a++) {
            if (enemiesI[a].equalsIgnoreCase(name)) {
                return a;
            }
        }
        
        for (int a = 0; a < enemiesII.length; a++) {
            if (enemiesII[a].equalsIgnoreCase(name)) {
                return a + 100;
            }
        }               
        
        return -1;
    }
}
