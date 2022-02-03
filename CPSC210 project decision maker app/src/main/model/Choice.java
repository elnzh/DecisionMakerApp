package model;

import exceptions.NullChoiceException;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Choice is a single String object, that is user input choice
 * This class offers the function to set the Choice or get the content
 */
public class Choice implements Writable {

    private String choice;

    //EFFECTS: create a new choice that has contents
    public Choice(String c)throws NullChoiceException {
        if (c == null) {
            throw new NullChoiceException();
        }
        this.choice = c;
    }

    //EFFECTS: return the String of choice
    public String getChoice() {
        return choice;
    }


    //MODIFIES: this
    //EFFECTS: change the content of choice to a new String. If the String is null, throw NullChoiceException
    public void setChoice(String c)throws NullChoiceException {
        if (c == null) {
            throw new NullChoiceException();
        }
        this.choice = c;
    }

    @Override
    public JSONObject toJson() {
        JSONObject j = new JSONObject();
        j.put("Choice", choice);
        return j;
    }

}
