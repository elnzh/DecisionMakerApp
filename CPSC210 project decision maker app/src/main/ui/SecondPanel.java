package ui;

import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

/**
 * SecondPanel is the panel where user can choose either modify their choices or generate the decision maker
 */
public class SecondPanel implements ActionListener {
    private static final String JSON_FILE = "data/listOfChoices.json";
    private static final int WIDTH = 360;
    private JPanel panel;
    private final JLabel label = new JLabel("\nSelect from:\n");
    private AppFrame app;
    private JsonWriter jsonWriter;

    //EFFECTS: create the second panel and call the helper method setSecondPanel()
    public SecondPanel(AppFrame app) {
        panel = new JPanel();
        this.app = app;
        setSecondPanel();
        app.getFrame().add(panel);
        app.getFrame().setLocationRelativeTo(null);
        app.getFrame().setVisible(true);
        app.getFrame().setResizable(false);
        setQuit();
    }

    //MODIFIES: this
    //EFFECTS: add components to the panel and add the panel to frame
    public void setSecondPanel() {
        panel.add(Box.createRigidArea(new Dimension(WIDTH / 2, 16)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JButton add = new JButton("1 -> add or modify choices");
        add.setActionCommand("modify");
        add.addActionListener(this);
        JButton decisionMaker = new JButton("2 -> generate Decision Maker ");
        decisionMaker.setActionCommand("decisionMaker");
        decisionMaker.addActionListener(this);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(add);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(decisionMaker);
    }

    //EFFECTS: return the panel
    public JPanel getPanel() {

        return panel;
    }

    //MODIFIES:this
    //EFFECTS: show save choices message when user want to quit
    public void setQuit() {
        app.getFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                    if (app.getLc().getSize() == 0) {
                        app.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                        int result = JOptionPane.showConfirmDialog(app.getFrame(), "Do you want to save your choices?",
                                "save choice confirmation: ", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            quitClickYes();
                        } else if (result == JOptionPane.NO_OPTION) {
                        app.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        app.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
            }
        });
    }

    //MODIFIES:this
    //EFFECTS: when user want to quit and click yes option the system will quit
    //         if FileNotFoundException is thrown, show file not found error msg
    private void quitClickYes() {
        try {
            saveChoice();
            JOptionPane.showMessageDialog(app.getFrame(),"Saved.");
            app.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(panel, "File Not Found", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: write choices to JSON file.
    public void saveChoice() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonWriter.open();
        jsonWriter.write(app.getLc());
        jsonWriter.close();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("modify")) {
            ModifyChoicePanel third = new ModifyChoicePanel(app, panel);
            app.getFrame().setContentPane(third.getPanel());
            app.getFrame().validate();
        } else if (e.getActionCommand().equals("decisionMaker")) {
            DecisionPanel ds = new DecisionPanel(app, panel);
            app.getFrame().setContentPane(ds.getPanel());
            app.getFrame().validate();

        }


    }



}
