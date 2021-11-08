package view;

import controller.ApplicationController;
import exception.crud.GetException;
import exception.EmptyArrayListException;

import javax.swing.*;
import java.awt.*;

public class PeopleComboBoxPanel extends JPanel {
    private JComboBox peopleComboBox;
    private String[] peoplePseudo;

    public PeopleComboBoxPanel(MenuWindow menu,JPanel panel) throws EmptyArrayListException, GetException {
        ApplicationController controller = new ApplicationController();
        peoplePseudo = controller.getAllPeople().stream().map(person -> person.getPseudo()).toArray(String[] :: new);
        if(peoplePseudo.length == 0){
            menu.dispose();
            menu.getContentPane().add(menu.getWestPanel(), BorderLayout.WEST);
            menu.getContentPane().add(menu.getEastPanel(), BorderLayout.EAST);
            menu.getContentPane().add(menu.getCenterPanel(), BorderLayout.CENTER);
            panel.setVisible(false);
            menu.setVisible(true);
            throw new EmptyArrayListException("Veuillez ajouter des personnes avant de commencer");
        }
        peopleComboBox = new JComboBox(peoplePseudo);
        peopleComboBox.setMaximumRowCount(5);
        peopleComboBox.setSelectedItem(peoplePseudo[0]);
        peopleComboBox.setEditable(false);
        this.add(peopleComboBox);
    }

    public JComboBox getPeopleComboBox() {
        return peopleComboBox;
    }
    public String[] getPeoplePseudo() {
        return peoplePseudo;
    }
}
