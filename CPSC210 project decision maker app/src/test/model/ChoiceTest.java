package model;

import exceptions.NullChoiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChoiceTest {

    private static String p = "finish my CPSC210 project";
    private static String l= "finish my CPSC210 lab";

    @Test
    public void constructorTest(){
        Choice myChoice= null;
        try {
            myChoice = new Choice(null);
            fail();
        } catch (NullChoiceException e) {
            //pass
        }
        try {
            myChoice = new Choice(p);
        } catch (NullChoiceException e) {
            fail();
        }
        assertEquals(p, myChoice.getChoice());
    }

   @Test
    public void setChoiceTest() {
       Choice myChoice= null;
       try {
           myChoice = new Choice(p);
       } catch (NullChoiceException e) {
           fail();
       }
       assertEquals(p, myChoice.getChoice());

       try {
           myChoice.setChoice(l);
       } catch (NullChoiceException e) {
           fail();
       }
       assertEquals(l, myChoice.getChoice());

   }

}