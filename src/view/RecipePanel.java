package view;

import controller.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;


public class RecipePanel extends JPanel {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private JPanel informationPanel,resultPanel, recipePanel, southPanel;
    private ApplicationController controller;
    private JLabel dishRecipeLabel;
    private JLabel caloriesRecipeLabel;
    private JLabel informationLabel;
    private JLabel caloriesSportifLabel;
    private JComboBox  dishesComboBox;
    private String[] dishes;
    private String recipe;
    private AllQuantitiesForIngredientsPanel allQuantitiesForIngredientsPanel;
    private MenuWindow menu;
    private PeopleComboBoxPanel peopleComboBoxPanel;
    private String selectedPseudo;

    public RecipePanel(MenuWindow menu,String pseudo,String dish){
        this(menu);
        peopleComboBoxPanel.getPeopleComboBox().setSelectedItem(pseudo);
        dishesComboBox.setSelectedItem(dish);
    }
    public RecipePanel(MenuWindow menu) {

        informationPanel = new JPanel();
        resultPanel = new JPanel();
        recipePanel = new JPanel();
        dishRecipeLabel =  new JLabel();
        southPanel = new JPanel();
        this.menu = menu;

        this.setLayout(new BorderLayout());
        recipePanel.setLayout(new BorderLayout());
        informationPanel.setLayout(new GridLayout(2,2));
        resultPanel.setLayout(new BorderLayout());
        southPanel.setLayout(new BorderLayout());

        controller = new ApplicationController();

        informationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        caloriesRecipeLabel = new JLabel();
        caloriesSportifLabel = new JLabel();
        informationLabel = new JLabel();

        southPanel.add(caloriesRecipeLabel, BorderLayout.NORTH);
        southPanel.add(caloriesSportifLabel, BorderLayout.CENTER);
        southPanel.add(informationLabel, BorderLayout.SOUTH);

        resultPanel.add(southPanel, BorderLayout.SOUTH);
        resultPanel.add(dishRecipeLabel, BorderLayout.NORTH);

        createAndAddFieldsToInformationPanel();

        recipePanel.add(informationPanel, BorderLayout.NORTH);
        recipePanel.add(resultPanel,BorderLayout.CENTER);

        this.add(new ReturnButonPanel(menu, this),BorderLayout.SOUTH);
        this.add(recipePanel,BorderLayout.CENTER);
    }

    private void createAndAddFieldsToInformationPanel(){
        try {
            JLabel peopleLabel = new JLabel("Pseudo :");
            this.peopleComboBoxPanel = new PeopleComboBoxPanel(menu,this);
            peopleComboBoxPanel.getPeopleComboBox().addItemListener(new PeopleComboBoxListener());
            selectedPseudo  = peopleComboBoxPanel.getPeoplePseudo()[0];
            dishes = controller.getDishLabelFor(selectedPseudo).toArray(String[]::new);
            int dishId = controller.getDishCode(dishes[0]);
            recipe = controller.getDishRecipe(dishId);

            JLabel dishLabel = new JLabel("Plat : ");
            dishesComboBox = new JComboBox(dishes);
            dishesComboBox.addItemListener(new DishComboBoxListener());

            allQuantitiesForIngredientsPanel = new AllQuantitiesForIngredientsPanel(dishId);

            resultPanel.add(allQuantitiesForIngredientsPanel, BorderLayout.CENTER);

            createAndAddFieldsToResultPanel(dishId, recipe,selectedPseudo );

            informationPanel.add(peopleLabel);
            informationPanel.add(peopleComboBoxPanel);
            informationPanel.add(dishLabel);
            informationPanel.add(dishesComboBox);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    private class PeopleComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            selectedPseudo = peopleComboBoxPanel.getPeopleComboBox().getSelectedItem().toString();
            try {
                dishes = controller.getDishLabelFor(selectedPseudo).toArray(String[]::new);
                informationPanel.remove(dishesComboBox);
                dishesComboBox = new JComboBox(dishes);
                dishesComboBox.addItemListener(new DishComboBoxListener());
                int dishId = controller.getDishCode(dishes[0]);
                recipe = controller.getDishRecipe(dishId);
                createAndAddFieldsToResultPanel(dishId, recipe,selectedPseudo );

                informationPanel.add(dishesComboBox);
                informationPanel.revalidate();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,exception.getMessage(),"Erreur récupération des plats",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class DishComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {

            try {
                String dishNameSelect = dishesComboBox.getSelectedItem().toString();
                int dishId = controller.getDishCode(dishNameSelect);
                String dishRecipeSelect = controller.getDishRecipe(dishId);

                createAndAddFieldsToResultPanel(dishId, dishRecipeSelect,selectedPseudo);

                resultPanel.revalidate();

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createAndAddFieldsToResultPanel(int dishId, String dishRecipeSelect, String pseudo) {
        try {
            resultPanel.remove(dishRecipeLabel);
            resultPanel.remove(allQuantitiesForIngredientsPanel);
            recipePanel.remove(southPanel);
            southPanel.remove(caloriesRecipeLabel);
            southPanel.remove(informationLabel);
            southPanel.remove(caloriesSportifLabel);

            allQuantitiesForIngredientsPanel = new AllQuantitiesForIngredientsPanel(dishId);
            dishRecipeLabel = new JLabel(dishRecipeSelect);

            Double caloriesRecipe = controller.caloriesForDish(dishId);
            Double caloriesSportif = controller.getCaloriesFor(pseudo);

            caloriesRecipeLabel = new JLabel("Il y a " + df2.format(caloriesRecipe) + " calories pour une portion du plat");
            informationLabel = new JLabel("* Lorsque le volume est mit à 0 la quantité est à votre convenance");
            caloriesSportifLabel = new JLabel("Votre apport de calories pour une journée est de "+ df2.format(caloriesSportif) + " équivaut à plus ou moins " +
                    df2.format(caloriesSportif/5) + " par repas");

            southPanel.add(caloriesRecipeLabel, BorderLayout.NORTH);
            southPanel.add(caloriesSportifLabel, BorderLayout.CENTER);
            southPanel.add(informationLabel, BorderLayout.SOUTH);

            resultPanel.add(southPanel, BorderLayout.SOUTH);
            resultPanel.add(dishRecipeLabel, BorderLayout.NORTH);
            resultPanel.add(allQuantitiesForIngredientsPanel, BorderLayout.CENTER);
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
