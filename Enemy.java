
/**
 * The (not as) broad category of things that you can fight, examine, and that's it.
 * Will drop loot and experience.
 * 
 * @version 0.1
 */
public class Enemy extends Interactable
{
    //The level of the enemy. Starts at 1. Goes up to.. Infinity for now. *edit: modified to 1000
    protected int level;
    
    //The amount of health of this monster. Should ideally follow a pattern based on level.
    protected int health;
    
    //The amount of mana this monster has. Should be used by mages, rarely otherwise.
    protected int mana;

    public Enemy(int ID) {
        super.ID = ID;
        name = getNameFromID(ID);
        level = -1;
    }
    
    public Enemy(String nm, int lvl) {
        super.ID = getIDFromName(nm);
        name = nm;
        level = lvl;
    }
    
    public void setLevel(int lvl) {
        if (lvl > 0) {
            level = lvl;
        }
    }
    
    public void interact() {
        fight();
    }
    
    public void fight() {
        
    }
}
