package ui;

import exceptions.NullChoiceException;
import model.ListOfChoices;
import org.json.JSONException;
import persistence.JsonReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

/**
 * FirstPanel is the first menu penal that user can choose whether start the app directly or load the file
 */
public class FirstPanel  implements ActionListener {
    private static final String JSON_FILE = "data/listOfChoices.json";
    private static final int WIDTH = 360;
    private JPanel panel;
    private AppFrame app;
    private final JLabel label = new JLabel("\nSelect from:\n");
    private JsonReader jsonReader;
    private ListOfChoices lc;

    //EFFECTS: create the first panel and call the helper method setFirstPanel()
    public FirstPanel(AppFrame app) {
        panel = new JPanel();
        this.app = app;
        lc = app.getLc();
        setFirstPanel();

    }


    //MODIFIES: this
    //EFFECTS: add components to the panel and add the panel to frame
    public void setFirstPanel() {
        panel.add(Box.createRigidArea(new Dimension(WIDTH / 2, 16)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JButton start = new JButton("1 -> start Decision Maker");
        start.setActionCommand("start");
        start.addActionListener(this);
        JButton load = new JButton("2 -> load Decision Maker from file ");
        load.setActionCommand("loadFile");
        load.addActionListener(this);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(start);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(load);
        app.getFrame().add(panel);
        app.getFrame().setLocationRelativeTo(null);
        app.getFrame().setVisible(true);
        app.getFrame().setResizable(false);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {
            //go to second panel
            SecondPanel second = new SecondPanel(app);
            app.getFrame().setContentPane(second.getPanel());

        } else if (e.getActionCommand().equals("loadFile")) {
            try {
                readFile();
                SecondPanel second = new SecondPanel(app);
                app.getFrame().setContentPane(second.getPanel());
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(panel, "File Not Found", "Error",
                        JOptionPane.WARNING_MESSAGE);
            } catch (JSONException jsonException) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(panel, "Empty file. Please save your choices first.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } catch (NullChoiceException n) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(panel, "Invalid file. Please save your choices first.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    //MODIFIES: this
    //EFFECTS: read JSON file and assign the list to lc
    private void readFile() throws IOException, NullChoiceException {
        this.jsonReader = new JsonReader(JSON_FILE);
        lc = jsonReader.readFile();
        app.setLc(lc);

    }
}

