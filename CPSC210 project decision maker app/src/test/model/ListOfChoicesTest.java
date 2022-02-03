package model;

import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ListOfChoicesTest {
    private  ListOfChoices lc;
    private static String s1 = "finish my CPSC210 project";
    private static String s2 = "finish my CPSC210 lab";

    @BeforeEach
    public void setUp(){
        lc = new ListOfChoices();
    }


    @Test
    public void addChoiceTest(){
        notCaughtNullException(s1);
        assertEquals(1, lc.getSize());
        //adding second object
        notCaughtNullException(s2);
        assertEquals(2, lc.getSize());
        try {
            assertEquals(s2, lc.getString(1));
        } catch (InvalidIndexException e) {
            fail();
        }

    }

    private void notCaughtNullException(String s1)  {
        try {
            lc.addChoice(s1);
        } catch (NullChoiceException e) {
            fail();
        }
    }
    @Test
    public void nullExceptionTest(){
        notCaughtNullException(s1);
        notCaughtNullException(s2);
        assertEquals(2, lc.getSize());
        try {
            lc.addChoice(null);
            fail();
        } catch (NullChoiceException e) {
            //pass
        }
        assertEquals(2, lc.getSize());
        try {
            lc.setChoice(0,null);
            fail();
        } catch (NullChoiceException e) {
            //pass
        } catch (InvalidIndexException e) {
            fail();
        }

    }


    @Test
    public void removeChoiceTest(){
        notCaughtNullException(s1);
        assertEquals(1, lc.getSize());

        //test if item is not in the list
        try {
            lc.removeChoice(1);
            fail();
        } catch (InvalidIndexException e) {

        }
        try {
            lc.removeChoice(-1);
            fail();
        } catch (InvalidIndexException e) {

        }

        notCaughtNullException(s2);
        assertEquals(2, lc.getSize());

        //test if item is in the list
        try{
            lc.removeChoice(0);
        } catch (InvalidIndexException e) {
             fail();
        }

        assertEquals(1, lc.getSize());
        try {
            assertEquals(s2,lc.getString(0));
        } catch (InvalidIndexException e) {
            fail();
        }
        try {
            lc.removeChoice(0);
        } catch (InvalidIndexException e) {
            fail();
        }
        assertEquals(0, lc.getSize());
    }


    @Test
    public void setChoiceTest() {
        notCaughtNullException(s1);
        assertEquals(1, lc.getSize());

        //test when the item to found DNE
        try {
            lc.setChoice(1,s1);
        } catch (NullChoiceException e) {
            fail();
        }  catch (InvalidIndexException e) {

    }
        try {
            lc.setChoice(-1,s1);
        } catch (NullChoiceException e) {
            fail();
        } catch (InvalidIndexException e) {

    }
        notCaughtNullException(s2);
        assertEquals(2, lc.getSize());

        //test when the item is found
        try {
            lc.setChoice(1,s1);
        } catch (NullChoiceException e) {
            fail();
        }catch(InvalidIndexException n){
            System.out.println("caught InvalidIndexException");
        }
        assertEquals(2, lc.getSize());
        try {
            assertEquals(s1,lc.getString(0));
        } catch (InvalidIndexException e) {
            System.out.println("caught InvalidIndexException");
        }
        try{
            assertEquals(s1,lc.getString(1));
        } catch (InvalidIndexException e) {
            System.out.println("caught InvalidIndexException");
        }

    }
    @Test
    public void getStringTest(){
        notCaughtNullException(s1);
        assertEquals(1, lc.getSize());
        try {
            lc.getString(-1);
            fail();
        } catch (InvalidIndexException e) {
           //pass
        }
        try {
            lc.getString(3);
            fail();
        } catch (InvalidIndexException e) {
            //pass
        }
        try {
            assertEquals(s1 ,lc.getString(0));

        } catch (InvalidIndexException e) {
            fail();
        }

    }

    @Test
    public void clearTest(){
        notCaughtNullException(s1);
        notCaughtNullException(s2);
        assertEquals(2, lc.getSize());
        //test after calling the clear method the list will have no items inside.
        lc.clear();
        assertEquals(0, lc.getSize());
    }





}

