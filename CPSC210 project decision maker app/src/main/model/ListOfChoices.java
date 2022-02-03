package model;

import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * ListOfChoices is a List of Choice objects which each represent a user input choice(String object)
 * List can add new choice, delete existing choice, remove all, or modify existing choice
 */
public class ListOfChoices implements Writable {

    private List<Choice> choiceList;
    private Choice choice;


    // a list is created
    //EFFECTS: Create a empty list to store choices.
    public ListOfChoices() {
        this.choiceList = new ArrayList<Choice>();

    }


    // user input a string and convert it to Choice object
    //MODIFIES: this
    //EFFECTS: create a Choice object with the given String add the choice to the choiceList
    public void addChoice(String s) throws NullChoiceException {
        choice = new Choice(s);
        choiceList.add(choice);

    }




    //MODIFIES: this
    //EFFECTS: if index out of the bounds, throw InvalidIndexException. Else remove the choice from the choiceList
    public void removeChoice(int index) throws InvalidIndexException {
        if (index < 0 || index >= choiceList.size()) {
            throw new InvalidIndexException();
        } else {
            choiceList.remove(index);
        }
    }


    //MODIFIES: this
    //EFFECTS: change a choice's content based on the position of the item and String s that is passed to.
    //          if index out of bounds, throw new InvalidIndexException;
    public void setChoice(int before, String after) throws InvalidIndexException, NullChoiceException {
        if (before < 0 || before >= choiceList.size()) {
            throw new InvalidIndexException();
        }
        choiceList.get(before).setChoice(after);


    }


    //MODIFIES: this
    //EFFECTS: clear the list of tasks
    public void clear() {
        this.choiceList = new ArrayList<Choice>();

    }

    //EFFECTS: return the size of the list
    public int getSize() {
        return choiceList.size();
    }


    //EFFECTS: if index is out of bounds, throw InvalidIndexException. Else return the content at given index
    public String getString(int index) throws InvalidIndexException  {
        if (index < 0 || index >= choiceList.size()) {
            throw new InvalidIndexException();
        }
        return choiceList.get(index).getChoice();
    }


    //EFFECTS: append the JSONArray to JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject j = new JSONObject();
        j.put("List of choices",toJsonArray());
        return j;
    }

    //MODIFIES: arr
    //EFFECTS: convert the choiceList array into JSONArray and returns the JSONArray
    public JSONArray toJsonArray() {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < choiceList.size(); i++) {
            arr.put(choiceList.get(i).toJson());
        }
        return arr;
    }



}