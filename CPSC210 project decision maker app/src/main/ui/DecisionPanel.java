package ui;

import exceptions.InvalidIndexException;
import model.ListOfChoices;
import model.RandomChoice;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * DecisionPanel is the panel where random decision is generated and displayed in a text field. Within this panel, user
 * have two options: try again-- another random choice will be generated; return--the content panel will return to the
 * second panel
 */
public class DecisionPanel implements ActionListener {
    private ListOfChoices lc;
    private RandomChoice rc;
    private JPanel panel;
    private JPanel second;
    private AppFrame app;
    private JTextField result;
    private final JLabel label = new JLabel("\nDecision Maker has generated the result: \n");
    private GroupLayout layout;
    private JButton again;
    private JButton returnBt;


    //EFFECTS: create the Decision Maker panel and call the helper method setDecisionPanel()
    public DecisionPanel(AppFrame app, JPanel second) {
        panel = new JPanel();
        this.app = app;
        this.second = second;
        result = new JTextField();
        again = new JButton("start");
        returnBt = new JButton("return");

        layout = new GroupLayout(panel);
        panel.setLayout(layout);

        setDecisionPanel();
        app.getFrame().add(panel);
        initializeLc(app.getLc());
        app.getFrame().setLocationRelativeTo(null);
        app.getFrame().setVisible(true);
        app.getFrame().setResizable(false);
        app.getFrame().pack();
        //generateDecision();
    }

    //MODIFIES: this
    //EFFECTS:  the local variable lc now point to app's ListOfChoices object
    private void initializeLc(ListOfChoices choices) {
        lc = choices;
    }


    //MODIFIES: this
    //EFFECTS: add components to the panel and add the panel to frame
    public void setDecisionPanel() {
        result.setHorizontalAlignment(JTextField.CENTER);
        again.setActionCommand("again");
        again.addActionListener(this);
        returnBt.setActionCommand("return");
        returnBt.addActionListener(this);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(label)
                        .addComponent(result)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(125,130,140)
                                .addComponent(again)
                                .addComponent(returnBt)
                        )));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(result)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(again)
                        .addComponent(returnBt))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                         .addGap(6,9,10)));
    }


    //EFFECTS: return the current JPanel
    public JPanel getPanel() {
        return panel;
    }


    //MODIFIES: this
    //EFFECTS: generate the random decision based on the ListOfChoices object
    //         if the size of lc is 0, show error msg
    private void generateDecision() throws InvalidIndexException {
        if (lc.getSize() == 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(panel, "Empty Choice list, please add a choice", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            rc = new RandomChoice(lc);
            result.setText(rc.randChoice());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("return")) {
            app.getFrame().setContentPane(second);
            app.getFrame().validate();
        } else if (e.getActionCommand().equals("again")) {
            try {
                generateDecision();
            } catch (InvalidIndexException invalidIndexException) {
                invalidIndexException.printStackTrace();
            }
        }
    }

}
