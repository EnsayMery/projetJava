package view;

import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePersonPanel extends JPanel{
    private AllPeoplePanel allPeoplePanel;
    private MenuWindow menu;
    private JPanel buttonPanel;
    private JButton updateButton;
    public UpdatePersonPanel(MenuWindow menu) {

        this.setLayout(new BorderLayout());
        this.menu = menu;
        this.allPeoplePanel = new AllPeoplePanel("modify");
        this.buttonPanel = new JPanel();

        updateButton = new JButton("Modifier");
        updateButton.addActionListener(new UpdateButtonListener());

        /* ------ Mise en place Button panel ------ */

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new ReturnButonPanel(menu, this));
        buttonPanel.add(updateButton);

        /* ------ Ajout panel ------ */

        this.add(allPeoplePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);


    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = allPeoplePanel.getListSelect().getMinSelectionIndex();
            if(selectedIndex != -1){
                Person selectedPerson = allPeoplePanel.getPeople().get(selectedIndex);
                menu.getContainer().removeAll();
                menu.getContainer().add(new AddOrModifyPersonPanel(menu,selectedPerson));
                menu.getContainer().revalidate();
            }
        }
    }

}
