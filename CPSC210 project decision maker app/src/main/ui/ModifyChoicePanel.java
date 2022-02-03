package ui;

import exceptions.InvalidIndexException;
import exceptions.NullChoiceException;
import model.ListOfChoices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ModifyChoicePanel is the panel where user is able to add, remove, update, clear their choices. A table will display
 * all the choices and a text field will allow any user input.
 */
public class ModifyChoicePanel implements ActionListener {
    private ListOfChoices lc;
    private JPanel panel;
    private JPanel second;
    private JTable table;
    private AppFrame app;
    private int index;
    private DefaultTableModel model;
    private Object[] columns = {"index", "choices"};
    private Object[] row;
    private JTextField text;
    private JScrollPane scrollPane;
    private JButton add;
    private JButton update;
    private JButton remove;
    private JButton clear;
    private  JButton returnBt;
    private  GroupLayout layout;

    //EFFECTS: create the third panel and call the helper method setThirdPanel()
    public ModifyChoicePanel(AppFrame app, JPanel second) {
        this.app = app;
        this.second = second;
        panel = new JPanel();
        table = new JTable();
        scrollPane = new JScrollPane(table);
        layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        initializeButtons();
        initializeModel();
        initializeList(app.getLc());
        groupLayout();

        app.getFrame().add(panel);
        app.getFrame().setLocationRelativeTo(null);
        app.getFrame().setVisible(true);
        app.getFrame().setResizable(false);

    }

    //MODIFIES: this
    //EFFECTS: initialize JButtons
    private void initializeButtons() {
        add = new JButton("add");
        update = new JButton("update");
        remove = new JButton("remove");
        clear = new JButton("clear");
        returnBt = new JButton("return");
    }

    //MODIFIES: this
    //EFFECTS: initialize model, set its column and row
    private void initializeModel() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        model.setColumnIdentifiers(columns);
        row = new Object[2];
    }

    //MODIFIES: this
    //EFFECTS: initialize lc. lc is not pointing to app's ListOfChoices object
    private void initializeList(ListOfChoices choices) {
        lc = choices;
        index = choices.getSize();

    }

    //MODIFIES: this
    //EFFECTS: set the table model
    public void setTable() {
        table.setModel(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                text.setText(model.getValueAt(row,1).toString());
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: set the panel's layout.
    public void groupLayout() {
        text = new JTextField(15);
        setTable();
        setJButtons();
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane)
                        .addComponent(text)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(add)
                                .addComponent(update)
                                .addComponent(remove)
                                .addComponent(clear)
                                .addComponent(returnBt))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addComponent(text)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(add)
                        .addComponent(update)
                        .addComponent(remove)
                        .addComponent(clear)
                        .addComponent(returnBt)));
        showExistingChoice();

    }


    //MODIFIES: this
    //EFFECTS: set JButtons
    private void setJButtons() {
        add.setActionCommand("add");
        add.addActionListener(this);
        update.setActionCommand("update");
        update.addActionListener(this);
        remove.setActionCommand("remove");
        remove.addActionListener(this);
        clear.setActionCommand("clear");
        clear.addActionListener(this);
        returnBt.setActionCommand("return");
        returnBt.addActionListener(this);
    }

    //EFFECTS: return the panel
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "return":
                app.getFrame().setContentPane(second);
                app.getFrame().validate();
                break;
            case "clear":
                clearChoice();
                break;
            case "remove":
                removeChoice();
                break;
            case "add":
                addChoice();
                break;
            case "update":
                updateChoice();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: remove the entire table and all elements in listOfChoices
    private void clearChoice() {
        model.setRowCount(0);
        lc.clear();
        text.setText(null);
        index = lc.getSize();
    }


    //MODIFIES: this
    //EFFECTS: when user click update button, the string is updated in the above table and also the list
    private void updateChoice() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            if (!text.getText().isEmpty()) {
                model.setValueAt(text.getText(), row, 1);
                try {
                    lc.setChoice(row, text.getText());
                } catch (NullChoiceException e) {
                    System.out.println("caught NullChoiceException");
                } catch (InvalidIndexException e) {
                    System.out.println("caught InvalidIndexException");
                }
                text.setText(null);
            }
        }
        text.setText(null);
        showExistingChoice();

    }


    //MODIFIES: this
    //EFFECTS: when user click remove button, the string is removed from the above table and also the list
    private void removeChoice() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            model.removeRow(r);
            try {
                lc.removeChoice(r);
            } catch (InvalidIndexException e) {
                System.out.println("caught InvalidIndexException");
            }
            index--;
        } else {
            System.out.println("delete error");
        }
        text.setText(null);
        showExistingChoice();

    }



    //MODIFIES: this
    //EFFECTS: when user click add button, the string is shown in the above table and also stored inside the list
    private void addChoice() {
        if (!text.getText().isEmpty()) {
            row[0] = index + 1;
            row[1] = text.getText();
            try {
                lc.addChoice(text.getText());
            } catch (NullChoiceException e) {
                e.printStackTrace();
            }
            model.addRow(row);
            text.setText(null);
            index++;
        }
        showExistingChoice();


    }


    //MODIFIES: this
    //EFFECTS:  if there are choices stored in system, display before user can add new choices
    public void showExistingChoice() {
        model.setRowCount(0);
        //first print the list we have
        int n = 0;
        if (lc.getSize() != 0) {
            for (int i = 0; i < lc.getSize(); i++) {
                n++;
                row[0] = n;
                try {
                    row[1] = lc.getString(i);
                } catch (InvalidIndexException e) {
                    System.out.println("caught InvalidIndexException");
                }
                model.addRow(row);
            }
        }
    }
}
