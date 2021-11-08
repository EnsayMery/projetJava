package view;

import controller.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DeletePersonPanel extends JPanel {
    private AllPeoplePanel allPeoplePanel;
    private MenuWindow menu;
    private JPanel buttonPanel;
    private JButton deleteButton;
    private ApplicationController controller;


    public DeletePersonPanel(MenuWindow menu) {

        this.setLayout(new BorderLayout());
        this.menu = menu;
        this.allPeoplePanel = new AllPeoplePanel("delete");
        this.buttonPanel = new JPanel();
        this.controller = new ApplicationController();
        /* ------ Initialisation deletebutton  ------*/

        deleteButton = new JButton("Suppression");
        deleteButton.addActionListener(new DeleteButtonListener());

        /* ------ Mise en place buttonPanel ------ */

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new ReturnButonPanel(menu, this));
        buttonPanel.add(deleteButton);

        /* ------ Ajout panel ------ */

        this.add(allPeoplePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);


    }
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                int []indicesLineSelections = allPeoplePanel.getListSelect().getSelectedIndices();

                if(indicesLineSelections.length != 0) {
                    for (int person : indicesLineSelections) {
                        String pseudo = allPeoplePanel.getPeople().get(person).getPseudo();

                        int result = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir supprimer " + pseudo, "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

                        if (result == 0) {
                            controller.deletePerson(pseudo);

                            menu.getContainer().removeAll();
                            menu.getContainer().add(new DeletePersonPanel(menu));
                            menu.getContainer().revalidate();

                            JOptionPane.showMessageDialog(null, "Suppression effectuée", "information suppresion", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Suppression annulée", "information suppresion", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Pour supprimer vous devez choisir une ligne");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
