package model;

import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomChoiceTest {

    private  ListOfChoices lc;
    private RandomChoice rc;
    private static String s1 = "finish my CPSC210 project";
    private static String s2 = "finish my CPSC210 lab";

    @BeforeEach
    public void setUp(){
        lc = new ListOfChoices();
        handleNullException(s1);


    }

    @Test
    public void constructorTest(){

        assertEquals(1, lc.getSize());

        //calling the RandomChoice constructor and pss in the list
        RandomChoice rc= new RandomChoice(lc);
        assertEquals(-1,rc.getRandInt());

    }

    @Test
    public void randChoiceTest(){
        int index;
        String str;


        assertEquals(1, lc.getSize());

        //test when there is only one choice (list size=1)
        rc= new RandomChoice(lc);
        try {
            assertEquals(rc.randChoice(), lc.getString(rc.getRandInt()));
        } catch (InvalidIndexException e) {
            fail("caught InvalidIndexException");
        }

        //test when there are many choice (list size>1)
        handleNullException(s1);
        handleNullException(s2);
        handleNullException(s1);
        rc= new RandomChoice(lc);

        try {
            assertEquals(rc.randChoice(), lc.getString(rc.getRandInt()));
        } catch (InvalidIndexException e) {
            fail("caught InvalidIndexException");
        }


    }

    private void handleNullException(String s1) {
        try {
            lc.addChoice(s1);
        } catch (NullChoiceException e) {
            fail();
        }
    }
}