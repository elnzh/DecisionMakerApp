package persistence;

import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import model.ListOfChoices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    private String emptyFilePath ="data/EmptyFileJsonTest.json";
    private String nonEmptyFilePath ="data/notEmptyFileJsonTest.json";
    private String invalidFilePath="data/Empty1FileJsonTesT.jsoN";
    private JsonWriter writer;
    private JsonReader reader;
    private ListOfChoices choices;


    @BeforeEach
    public void setUp() {
        choices = new ListOfChoices();
        addChoiceNoException("do homework");
        addChoiceNoException("eat lunch");
        addChoiceNoException("reading");
        addChoiceNoException("do housework");
    }

    private void addChoiceNoException(String s){
        try {
            choices.addChoice(s);
            //pass
        } catch (NullChoiceException e) {
           fail();
        }
    }

    @Test
    public void FileNotFoundExceptionTest(){
        try {
            writer = new JsonWriter(invalidFilePath);
            writer.open();
            writer.write(choices);
            writer.close();
            fail("FileNotFoundException doesn't throw");
        } catch (IOException e) {
            //pass
        }

    }

    @Test
    public void writeEmptyFileTest() {
        writer = new JsonWriter(emptyFilePath);
        try {
            writer.open();
            writer.write(new ListOfChoices());
            writer.close();
        } catch (FileNotFoundException e) {
            fail("not expected a FileNotFoundException");
        }

        reader = new JsonReader(emptyFilePath);
        try {
            choices=reader.readFile();
            assertEquals(choices.getSize(), 0);
        } catch (IOException e) {
            fail("Exception shouldn't be thrown");
        } catch (NullChoiceException e) {
            fail("NullChoiceException shouldn't be thrown");
        }

    }

    @Test
    public void writeOnExistingFile(){
        writer = new JsonWriter(nonEmptyFilePath);
        try {
            writer.open();
            writer.write(choices);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("not expected a FileNotFoundException");
        }

        ListOfChoices tempChoice;
        try {
            reader = new JsonReader(nonEmptyFilePath);
            tempChoice = reader.readFile();
            assertTrue(tempChoice.getSize() == 4);
            assertTrue(choices.getSize() == 4);
            equalString(tempChoice, 0);
            equalString(tempChoice, 1);
            equalString(tempChoice, 2);
            equalString(tempChoice, 3);
        } catch (IOException e) {
            fail("IOException shouldn't be thrown");
        } catch (NullChoiceException e) {
            fail("NullChoiceException shouldn't be thrown");
        }


    }

    private void equalString(ListOfChoices tempChoice, int i){
        try {
            assertEquals(tempChoice.getString(i), choices.getString(i));
        } catch (InvalidIndexException e) {
            fail("caught InvalidIndexException");
        }
    }


}

