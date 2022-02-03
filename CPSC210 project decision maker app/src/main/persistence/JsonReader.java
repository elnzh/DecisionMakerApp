package persistence;

import exceptions.NullChoiceException;
import model.ListOfChoices;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


/**
 * a JsonReader class reads a JSON file and return back the corresponding ListOfChoices object
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReader {
    private String source;

    // EFFECTS: assign a given file path to the variable source
    public JsonReader(String source) {
        this.source = source;
    }


    // MODIFIES:contentBuilder
    // EFFECTS: reads source file as string and returns it.
    //          If error happens on reading the file, throws IOException.
    public ListOfChoices readFile() throws IOException, NullChoiceException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        //convert file to a String
        String jsonStr = contentBuilder.toString();
        //convert String to JSONObject
        JSONObject jsonObject = new JSONObject(jsonStr);
        return listOfChoiceParse(jsonObject);

    }

    //MODIFIES: lc
    //EFFECTS: Parse the JSONArray into ListOfChoices and return the ListOfChoices object
    private ListOfChoices listOfChoiceParse(JSONObject json) throws NullChoiceException {
        ListOfChoices lc = new ListOfChoices();
        JSONArray arr = json.getJSONArray("List of choices");
        for (int i = 0; i < arr.length(); i++) {
            lc.addChoice(choiceParse(arr.getJSONObject(i)));
        }

        return lc;

    }

    //EFFECTS: return the String of a Choice object that is previously stored as a JSONObject
    private String choiceParse(JSONObject json) {
        return json.get("Choice").toString();
    }


}




