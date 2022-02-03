package model;

import exceptions.InvalidIndexException;

import java.util.Random;

/**
 * RandomChoice is a class to generate random result from existing List of Choices.
 * And it has functionality such as getting the random choice it generated.
 */
public class RandomChoice {

    private ListOfChoices lc;
    private int randInt;

    //EFFECTS: assign the given list to lc, initialize randInt to -1 (an impossible index)
    public RandomChoice(ListOfChoices lc) {
        this.lc = lc;
        this.randInt = -1;
    }

    //MODIFIES:this
    //EFFECTS:assign a rand integer between 0 and lc's last index to randInt.
    private void generateRand() {
        Random rand = new Random();
        //a random int between 0--(size-1)
        randInt = rand.nextInt(lc.getSize());
    }

    //EFFECTS:return the String that it's position is at the random index
    public String randChoice() throws InvalidIndexException {
        generateRand();
        String str = null;
        str = lc.getString(randInt);

        return str;
    }

    //EFFECTS: return the current rand integer
    public int getRandInt() {
        return randInt;
    }




}

