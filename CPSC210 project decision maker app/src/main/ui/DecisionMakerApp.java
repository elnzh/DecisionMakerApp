package ui;

import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * DecisionMakerApp is the Console app
 */
public class DecisionMakerApp {

    static Scanner keyboard = new Scanner(System.in);
    private static final String JSON_FILE = "data/listOfChoices.json";
    private ListOfChoices lc;
    private int size;
    private RandomChoice rc;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //initialize the choice list lc and set size of the list to 0
    public DecisionMakerApp() throws IOException {
        this.lc = new ListOfChoices();
        this.size = 0;
        runApp();

    }

    public void runApp() throws IOException {
        mainMenu();
        int input = keyboard.nextInt();
        switch (input) {
            case 1:
                play();
                break;
            case 2:
                this.jsonReader = new JsonReader(JSON_FILE);
                try {
                    lc = jsonReader.readFile();
                } catch (NullChoiceException e) {
                    System.out.println("NullChoiceException is thrown");
                }
                size = lc.getSize();
                play();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input");
                runApp();
        }
    }

    //main menu display
    public void mainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> start Decision Maker");
        System.out.println("\t2 -> load Decision Maker from file ");
        System.out.println("\t3 -> quit");
    }

    //second menu display
    public void secondMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add a choices");
        System.out.println("\t2 -> remove a choice");
        System.out.println("\t3 -> change an existing choice");
        System.out.println("\t4 -> clear all the choices");
        System.out.println("\t5 -> generate a decision maker");
        System.out.println("\t-1 -> quit");
    }

    //if user doesn't choose -1, the game will keep going
    public void play() {
        int input;
        boolean stop = false;
        while (!stop) {
            secondMenu();
            input = keyboard.nextInt();
            stop = inputSwitch(input, stop);

        }
    }

    //Once user decides to quit the app, ask whether saving the current ListOfChoice
    public boolean saveChoiceOption() {
        String answer = "n";
        System.out.println("Do you want to save your choices? (y/n)");
        keyboard.nextLine();
        answer = keyboard.next();
        while (!(answer.contains("n"))) {
            if (answer.contains("y") || answer.contains("Y")) {
                try {
                    saveChoice();
                    System.out.println("Saved!");
                } catch (FileNotFoundException e) {
                    System.out.println("FileNotFoundException: Unable to save your choices.");
                }
                return true;
            } else {
                System.out.println("invalid input, please enter again:");
                answer = keyboard.nextLine();
            }
        }
        return true;
    }


    //save the current ListOfChoices object to the file using JsonWriter
    public void saveChoice() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonWriter.open();
        jsonWriter.write(lc);
        jsonWriter.close();
    }

    //get user's input and match the input with the corresponding option
    public boolean inputSwitch(int input, boolean stop) {
        switch (input) {
            case 1:
                addChoice();
                break;
            case 2:
                removeChoice();
                break;
            case 3:
                changeChoice();
                break;
            case 4:
                clear();
                break;
            case 5:
                decisionMaker();
                break;
            case -1:
                stop = saveChoiceOption();
                break;
            default:
                defaultSwitch();
        }
        return stop;
    }

    //when user input is not belong to any of the given choices, inputSwitch method will call
    //this method to handle
    public void defaultSwitch() {
        System.out.println("Invalid input");
        play();
    }

    //when user choose 1, add an input
    public void addChoice() {
        String input;
        int n = 0;
        boolean stop = false;
        System.out.println("\tYou can start adding choices\n\tinput -1 to go back to previous menu.");
        System.out.println();
        keyboard.nextLine();
        if (lc.getSize() != 0) {
            for (int i = 0; i < lc.getSize(); i++) {
                n++;
                printEachChoice(n, i, ":", "Caught InvalidIndexException");
            }
        }
        while (!stop) {
            n++;
            System.out.print(n + ": ");
            input = keyboard.nextLine();
            if (input.equals("-1")) {
                stop = true;
            } else {
                size++;
                catchNullException(input);
            }
        }
    }

    private void printEachChoice(int n, int i, String s, String s2) {
        try {
            System.out.println(n + s + lc.getString(i));
        } catch (InvalidIndexException e) {
            System.out.println(s2);
        }
    }

    //try and catch NullChoiceException everytime user add a choice
    private void catchNullException(String input) {
        try {
            lc.addChoice(input);
        } catch (NullChoiceException e) {
            System.out.println("NullChoiceException is thrown");
        }
    }

    //when user choose 2, remove an input
    public void removeChoice() {
        boolean stop = false;
        int input;
        keyboard.nextLine();
        while (!stop) {
            printListOfChoice("remove");
            input = keyboard.nextInt();
            if (input == -1) {
                stop = true;
            } else {
                try {
                    lc.removeChoice(input - 1);
                    size--;
                    System.out.println("\tDone!");
                } catch (InvalidIndexException e) {
                    System.out.println("\tInvalid index, please try again:");
                    keyboard.nextLine();
                }
            }
        }

    }

    //when user choose 3, change an existing choice
    public void changeChoice() {
        boolean stop = false;
        int input;
        String str;
        keyboard.nextLine();
        while (!stop) {
            printListOfChoice("modify");

            input = keyboard.nextInt();
            if (input == -1) {
                stop = true;
            } else {
                keyboard.nextLine();
                System.out.println("\t the new choice:");
                str = keyboard.nextLine();
                try {
                    lc.setChoice(input - 1,  str);
                } catch (NullChoiceException e) {
                    System.out.println("NullChoiceException is caught. Can not set a choice null");
                } catch (InvalidIndexException e) {
                    System.out.println("\tInvalid index, please try again");
                }
            }
        }
    }


    public void printListOfChoice(String action) {
        for (int i = 0; i < size; i++) {
            printEachChoice(i + 1, i, ".", "caught invalid index");
        }
        System.out.println("\tselect which index to " + action);
        System.out.println("\tinput -1 to go back to previous menu.");
    }


    //when user choose 4, clear everything
    public void clear() {
        size = 0;
        lc.clear();

    }

    //when user choose 5, generate a decision maker app. Note if lc is empty, this method will not work.
    public void decisionMaker() {
        boolean stop = false;
        String str;
        rc = new RandomChoice(lc);
        keyboard.nextLine();
        if (size == 0) {
            System.out.println("\tEmpty list, please add choices");
        }
        while (!stop && size != 0) {
            System.out.println("\tDecision Maker App has generated a random choice: \n");
            try {
                System.out.println("\t" + rc.randChoice());
            } catch (InvalidIndexException e) {
                e.printStackTrace();
            }
            System.out.println("\n\tinput anything to try again ");
            System.out.println("\tinput -1 to go back to previous menu. ");
            str = keyboard.nextLine();
            if (str.equals("-1")) {
                stop = true;
            }
        }


    }
}