package view;

import controller.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class DishListingPanel extends JPanel {

    private JPanel listingPanel;
    private PeopleComboBoxPanel peopleComboBoxPanel;
    private ApplicationController controller;

    private ArrayList<String> dishesName;
    private MenuWindow menu;
    private JList jList;
    private String selectedPseudo;

    public DishListingPanel(MenuWindow menu){
        try {

            this.listingPanel= new JPanel();
            JPanel buttonsPanel = new JPanel();
            this.dishesName = new ArrayList<>();
            this.controller = new ApplicationController();

            this.menu = menu;
            listingPanel.setLayout(new FlowLayout());
            this.setLayout(new BorderLayout());

            this.peopleComboBoxPanel = new PeopleComboBoxPanel(menu,this);
            peopleComboBoxPanel.getPeopleComboBox().addItemListener(new ComboBoxListener());

            JButton validateButton = new JButton("Valider");
            validateButton.addActionListener(new ButtonListener());
            buttonsPanel.add(validateButton);
            buttonsPanel.add(new ReturnButonPanel(menu, this));

            displayDishName();
            this.add(peopleComboBoxPanel, BorderLayout.NORTH);
            this.add(listingPanel, BorderLayout.CENTER);
            this.add(buttonsPanel,BorderLayout.SOUTH);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayDishName() {
        try {
            JLabel infoLabel = new JLabel("Cliquez sur un menu puis appuyer sur valider pour afficher la recette !");
            listingPanel.add(infoLabel);
            selectedPseudo = peopleComboBoxPanel.getPeopleComboBox().getSelectedItem().toString();
            dishesName = controller.getDishLabelFor(selectedPseudo);
            jList = new JList(dishesName.toArray());
            jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            jList.setFixedCellWidth(400);
            jList.setFixedCellHeight(30);
            jList.setSize(400,500);
            jList.setVisibleRowCount(10);
            listingPanel.add(jList);
            listingPanel.add( new JScrollPane(jList));
        }catch (Exception exception) {
            JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            try {
                listingPanel.removeAll();
                displayDishName();
                listingPanel.revalidate();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(jList.isSelectionEmpty()){
                JOptionPane.showMessageDialog(null,"Vous devez d'abord sélectionner un plat","Erreur ",JOptionPane.ERROR_MESSAGE);
            }else{
                menu.getContainer().removeAll();
                menu.getContainer().add(new RecipePanel(menu, selectedPseudo,jList.getSelectedValue().toString()));
                menu.getContainer().revalidate();
            }
        }
    }
}

