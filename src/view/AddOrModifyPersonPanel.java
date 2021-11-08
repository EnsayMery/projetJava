package view;

import controller.ApplicationController;
import model.Nutrition;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddOrModifyPersonPanel extends JPanel {
    private ApplicationController controller;
    private MenuWindow menu;
    private RegistrationPanel registrationPanel;
    private Person person;
    private boolean isModifyPanel;

    public AddOrModifyPersonPanel(MenuWindow menu){
        this(menu,null);
    }

    public AddOrModifyPersonPanel(MenuWindow menu, Person person) {
        this.person = person;
        this.isModifyPanel = this.person != null;
        this.menu = menu;
        this.setLayout(new BorderLayout());

        controller = new ApplicationController();
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());
        JPanel buttonsPannel = new JPanel();
        this.registrationPanel = new RegistrationPanel(isModifyPanel);
        if (this.isModifyPanel) registrationPanel.setPerson(person);

        /*-----BUTTON Validation ------ */
        JButton validationButton = new JButton("Validation");
        validationButton.addActionListener(new ValidationListener());
        buttonsPannel.add(validationButton);

        /*-----BUTTON  ------ */
        JButton resetButton = new JButton("Réinitialisation");
        resetButton.addActionListener(new ResetListener());
        buttonsPannel.add(resetButton);


        buttonsPannel.add(new ReturnButonPanel(menu,this));
        generalPanel.add(registrationPanel,BorderLayout.CENTER);
        generalPanel.add(buttonsPannel,BorderLayout.SOUTH);

        this.add(generalPanel,BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setPreferredSize(new Dimension(500,500));

        setVisible(true);

    }
    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isModifyPanel) {
                registrationPanel.setPerson(person);
            } else {
                registrationPanel.setDefault();
            }
        }
    }
    private class ValidationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                registrationPanel.validateFields();
                Person newPerson = registrationPanel.getNewlyRegisteredPerson();
                int result = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir " + (isModifyPanel ? "modifier " : "ajouter ") + newPerson.getPseudo(), "Confirmation " + (isModifyPanel ? "de modification." : "d'ajout."), JOptionPane.YES_NO_OPTION);
                if(result == 0){
                    if(isModifyPanel){
                        controller.updatePerson(newPerson);
                        JOptionPane.showMessageDialog(null, "Modification de " + newPerson.getPseudo() +" réussie !", "Personne modifiée", JOptionPane.INFORMATION_MESSAGE);
                        menu.getContainer().removeAll();
                        menu.getContainer().add(new UpdatePersonPanel(menu));
                        menu.getContainer().revalidate();

                    } else {
                        controller.addPerson(newPerson);
                        ArrayList<Nutrition> nutritions = registrationPanel.getNewlyDietList();
                        for(Nutrition nutrition : nutritions)
                            controller.addNutrition(nutrition);
                        JOptionPane.showMessageDialog(null, "Ajout réussi !", "Personne ajoutée", JOptionPane.INFORMATION_MESSAGE);
                        registrationPanel.setDefault();
                    }
                }

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
