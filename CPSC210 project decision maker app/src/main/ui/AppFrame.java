package ui;

import model.ListOfChoices;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * AppFrame is the class that set the app's frame and calls the first panel and passes ListOfChoices
 * object lc to each panel.
 */
public class AppFrame {
    private static final int HEIGHT = 230;
    private static final int WIDTH = 440;
    private static final int LABEL_LOCATION_X = 100;
    private static final int LABEL_LOCATION_Y = 50;
    private JFrame frame;
    private FirstPanel firstPanel;
    private ListOfChoices lc;
    private final JLabel label;


    //EFFECTS: create the frame of the project and call the first panel
    public AppFrame() {
        lc = new ListOfChoices();
        frame = new JFrame("Decision Maker");
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));
        //frame.setLayout(new FlowLayout());
        label = new JLabel("\n Select from:\n");
        label.setPreferredSize(new Dimension(LABEL_LOCATION_X, LABEL_LOCATION_Y));
        frame.add(label);
        firstPanel = new FirstPanel(this);

    }

    //EFFECTS: return the frame
    public JFrame getFrame() {
        return frame;
    }

    //EFFECTS: return the ListOfChoices object lc
    public ListOfChoices getLc() {
        return lc;
    }

    //MODIFIES: this
    //EFFECTS: set the ListOfChoices object lc
    public void setLc(ListOfChoices c) {
        lc = c;
    }



}






