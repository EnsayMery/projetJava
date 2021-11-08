package view;

import controller.ApplicationController;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class MealPlanningFormPanel extends JPanel {
    private JPanel buttonsPanel, formPanel, generalPanel;
    private JButton validateButton;
    private MenuWindow menu;
    private ApplicationController controller;

    private JSpinner startingDateSpinner, numberOfDaysSpinner;
    private PeopleComboBoxPanel peopleComboBoxPanel;


    public MealPlanningFormPanel(MenuWindow menu){
        this.menu = menu;
        generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());
        controller = new ApplicationController();
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3,2,10,10));

        createAndAddButtonsToButtonsPanel();
        createAndAddFieldsToRecipePanel();

        generalPanel.add(buttonsPanel,BorderLayout.SOUTH);
        generalPanel.add(formPanel,BorderLayout.CENTER);
        this.add(generalPanel);
        setVisible(true);
    }
    private void createAndAddButtonsToButtonsPanel(){
        validateButton = new JButton("Valider");
        validateButton.addActionListener(new ValidateButtonListener());

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(new ReturnButonPanel(menu, this));
        buttonsPanel.add(validateButton);
    }

    private void createAndAddFieldsToRecipePanel() {
        try {
            JLabel peopleLabel = new JLabel("Pseudo :");
            this.peopleComboBoxPanel = new PeopleComboBoxPanel(menu,this);

            JLabel startingDateLabel = new JLabel("Date de début : ");
            GregorianCalendar nextYear = new GregorianCalendar();
            nextYear.add(GregorianCalendar.YEAR,1);
            GregorianCalendar yesterday = new GregorianCalendar();
            yesterday.add(Calendar.DATE,-1);
            startingDateSpinner = new JSpinner(new SpinnerDateModel(new Date(),yesterday.getTime(),nextYear.getTime(),1));
            startingDateSpinner.setEditor(new JSpinner.DateEditor(startingDateSpinner, "dd-MM-yyyy"));

            JLabel numberOfDaysLabel = new JLabel("Nombre de jours : ");
            numberOfDaysSpinner = new JSpinner(new SpinnerNumberModel(1,1,7,1));

            formPanel.add(peopleLabel);
            formPanel.add(peopleComboBoxPanel);
            formPanel.add(startingDateLabel);
            formPanel.add(startingDateSpinner);
            formPanel.add(numberOfDaysLabel);
            formPanel.add(numberOfDaysSpinner);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMealPlanning(){
        try {
            int nbDays = (int)numberOfDaysSpinner.getValue();
            Date startingDate = (Date) startingDateSpinner.getValue();
            String pseudo = peopleComboBoxPanel.getPeopleComboBox().getSelectedItem().toString();
            String[] meals = {"Déjeuner", "10h", "Dîner", "4h", "Souper"};

            GregorianCalendar date = Util.tranformDateToGregorianCalendar(startingDate);
            HashMap<String, ArrayList<String>> dishesWithTypes = controller.getDishesWithTypes(controller.getDishLabelFor(pseudo));
            int dishNummer;
            ArrayList<String> dishes = new ArrayList<>();
            Random random = new Random();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<String> createdMenusDates = controller.getMenusDatesOfSomeone(pseudo).stream().map(dateMenu -> simpleDateFormat.format(dateMenu.getTime())).toList();
            for (int i = 0; i < nbDays; i++){
                if(i != 0)
                    date.add(Calendar.DATE, 1);
                String dateInString = simpleDateFormat.format(date.getTime());
                if(!createdMenusDates.contains(dateInString)) {
                    for (String meal : meals){
                        switch (meal){
                            case "Déjeuner" :
                                dishes = dishesWithTypes.get("Petit-déjeuner");
                                break;
                            case "10h", "4h" :
                                dishes = dishesWithTypes.get("Collation");
                                break;
                            case "Dîner", "Souper" :
                                dishes = dishesWithTypes.get("Plat");
                                break;
                        }
                        dishNummer = controller.getDishCode(dishes.get(random.nextInt(dishes.size())));
                        controller.addMenuAndComposition(pseudo,meal, date,dishNummer);
                    }
                    JOptionPane.showMessageDialog(null,"Ajout réussi pour la date "+ dateInString  ,"", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"Il existe déjà un planning pour le jour :" + dateInString,"Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() ,"Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private class ValidateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addMealPlanning();
        }
    }

}
