package persistence;

import model.Choice;
import model.ListOfChoices;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * Write JSONObject to the file
 * reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonWriter {
    private static final int TAB = 4;
    private String file;
    private PrintWriter printWriter;

    //EFFECTS: initialize the filename
    public JsonWriter(String filename) {
        this.file = filename;

    }

    //EFFECTS: opens the file with given filename
    public void open() throws FileNotFoundException {
        File f = new File(file);
        if (!f.exists()) {
            throw new FileNotFoundException("File not found");
        }
        printWriter = new PrintWriter(f);
    }

    //MODIFIES: the file
    //EFFECTS: write the ListOfChoices in json representation into a file
    public void write(ListOfChoices c) {
        printWriter.write(c.toJson().toString(TAB));

    }

    //EFFECTS: close the file after writing to it.
    public void close() {
        printWriter.close();
    }



}
