//This is stuff for arrow key/instant character pressing support.
import java.awt.*;
import java.awt.event.*;

//This is for writing to, and reading from, files. For saving purposes.
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Scanner.
import java.util.Scanner;

/**
 * The class that runs the main game loop. Should have many sub-loops
 * 
 * @version: July 7, 2013
 */
public class main {
    static Player you = new Player();
    static Scanner scanner = new Scanner(System.in);

    //Runs the game. Ideally:
    /*
     * *Loads from save file all the relevant information (player info, floor number, room location, etc)
     * *Gives the title screen on start
     * *If in between floors, or starting a new game, creates a new floor
     *  *Floor fills itself with rooms based on the floor number and algorithms
     * *When a floor is finished [Perhaps it returns something back up here? Idk], delete the current floor,
     *      create a new one, and make sure the player is updated
     * *When play is finished, and the player types "Exit" and confirms,
     *  *Save player info
     *  *Save floor info
     *  *Save room info (eg, are you fighting? etc)
     *  *Write all this to the save file, and then end all the threads and exit
     */
    public static void main() {
        System.out.println("Welcome to Warping Dungeons v0.1.");

        System.out.println("This is a very, very early testing stage. It may be buggy.");

        mainLoop();
    }

    //This goes through the floors, one by one. When you finish one, it brings you lower. Adjust warped level as necessary. 
    public static void mainLoop() {
        int floorNum = 1;

        while(floorNum < 251) {
            Floor newFloor = new Floor(3, getWLFromFN(floorNum));
            newFloor.print();
            you.setFloor(newFloor);
            you.setLocation(newFloor.start.getLocation());
            
            System.out.println("Starting floor loop");
            floorLoop(newFloor);
        }
    }

    private static int getWLFromFN(int num) {
        if (num >= 1 && num < 21) {
            return 1;
        } else if (num >= 21 && num < 41) {
            return 2;
        } else {
            return 0;
        }        
    }

    //Handles the things that go on in a given floor. Takes care of player movement, options, fighting sequences, and so on. Breaks when the floor is over and the player descends.
    private static void floorLoop(Floor f) {
        while (!f.isOver()) {
            System.out.println();
            System.out.println("Enter in an option:");
            String command = scanner.nextLine();
            parse(command);
            if (you.loc.equals(f.end)) {
                f.isOver = true;
            }
        }
    }

    private static void parse(String s) {
        if (s.contains("move ")) {
            moveLoop(s);
        }        
        switch(s) {
            default: System.out.println("Invalid command.");
        }
    }

    private static void moveLoop(String s) {
        String newString = s.substring(5, 6);
        int dir = Integer.parseInt(newString);
        you.move(dir);
    }

    //Saves all the relevant data, time/date, into the save file. Then ends the program.
    public void saveAndExit() {

    }
}