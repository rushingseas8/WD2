import java.util.ArrayList;

/**
 * The broad category of anything that can be interacted with in a room. 
 * Each object must have an examine text, so that when the player does "examine" as a command, every
 * single object will list its small description, or pick an appropriate one in more than one case.
 * 
 * @version 0.1
 * 
 */
public class Interactable extends IDable
{
    //The name of the item
    protected String name;

    //The examine text of the item
    protected String examineText;

    //Returns the name of the item
    public String getName() {
        return name;
    }

    //This should be modified if there is more than one examine text based on the situation; 
    //Modify it to select the text based on the situation.
    public String examine() {
        return examineText;
    }

    //the action that picks up the item/interacts with it/fights it
    //Intended to be a helper method that makes starting a fight, picking up items, and so on easier.
    public void interact() {
        //Default action, should most likely be changed by subclasses.
        System.out.println(examine());
    }

    /**
     * @return 0 for singular, "a"; 1 for singular, "an"; 2 for plural, "some"
     */
    protected int nameHelper(String name, int number) {
        char[] anList = new char[4];
        anList[0] = 'a'; anList[0] = 'e'; anList[0] = 'i'; anList[0] = 'o';

        char toCompare = name.charAt(0);

        if (number > 1) {
            return 2;
        }

        for (char a: anList) {
            if (toCompare == a) {
                return 1;
            }
        }
        
        return 0;
    }
}
