package persistence;


import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import model.ListOfChoices;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    private static final String  emptyFilePath ="data/EmptyFileJsonTest.json";
    private static final String nonEmptyFilePath ="data/notEmptyFileJsonTest.json";
    private static final String invalidFilePath="data/Empty1FileJsonTesT.jsoN";
    private JsonReader reader;
    private JsonWriter writer;
    private ListOfChoices choices;


    @Test
    public void IOExceptionTest(){
        reader = new JsonReader(invalidFilePath);
        try {
            choices = reader.readFile();
            fail("IOException doesn't throw");
        } catch (IOException ie) {
            //pass
        } catch (NullChoiceException e) {
            fail();
        }
    }

    @Test
    public void readEmptyFileTest() {
        reader = new JsonReader(emptyFilePath);
        try {
            choices = reader.readFile();
            assertEquals(0,choices.getSize());
        } catch (IOException e) {
            fail("Not expected IOException.");
        } catch (NullChoiceException e) {
            //pass
        }
    }

    @Test
    public void readNotEmptyFileTest() {
        writer = new JsonWriter(nonEmptyFilePath);
        reader = new JsonReader(nonEmptyFilePath);
        ListOfChoices lc = new ListOfChoices();
        addChoiceNullException(lc, "read books");
        addChoiceNullException(lc, "eat food");
        addChoiceNullException(lc, "watch a movie");
        try {
            writer.open();
            writer.write(lc);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Not expected FileNotFoundException");
        }

        try {
            choices = reader.readFile();
            assertEquals(lc.getSize(),choices.getSize());
            for(int i=0; i< lc.getSize();i++) {
                assertEquals(lc.getString(i),choices.getString(i));
            }
        } catch (IOException e) {
            fail("Not expected IOException.");
        } catch (NullChoiceException e) {
            fail("Not expected NullChoiceException.");
        } catch (InvalidIndexException e) {
            fail("caught InvalidIndexException");
        }
    }

    private void addChoiceNullException(ListOfChoices lc, String s)  {
        try {
            lc.addChoice(s);
        } catch (NullChoiceException e) {
            fail();
        }
    }

}
