package view;

import controller.ApplicationController;
import exception.ObligatoryValueException;
import exception.crud.GetException;
import model.Meal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DisplayMealPlanning extends JPanel {
    private ApplicationController controller;
    private JPanel planningPanel;
    private PeopleComboBoxPanel peopleComboBoxPanel;


    public DisplayMealPlanning(MenuWindow menu){
        try {
            planningPanel = new JPanel();
            planningPanel.setLayout(new FlowLayout());

            this.setLayout(new BorderLayout());
            controller = new ApplicationController();

            this.peopleComboBoxPanel = new PeopleComboBoxPanel(menu,this);

            peopleComboBoxPanel.getPeopleComboBox().addItemListener(new ComboBoxListener());

            addPlanningIfNotEmpty();
            this.add(peopleComboBoxPanel,BorderLayout.NORTH);
            this.add(planningPanel,BorderLayout.CENTER);
            this.add(new ReturnButonPanel(menu,this), BorderLayout.SOUTH);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void addPlanningIfNotEmpty() throws GetException, ObligatoryValueException {
        String selectedPseudo = peopleComboBoxPanel.getPeopleComboBox().getSelectedItem().toString();
        ArrayList<Meal> planning = controller.getPlanningOfSomeone(selectedPseudo);

        if(planning.isEmpty()) {
            JOptionPane.showMessageDialog(null,String.format("Le pseudo %s n'a pas de planning. Veuillez en ajouter un ou sélectionner une autre personne : ", selectedPseudo),"Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JTable jTable = jTableCreation(planning);
            JScrollPane jScrollPane = scrollPaneCreation(jTable);
            planningPanel.add(jScrollPane);
        }
    }

    private JTable jTableCreation(ArrayList<Meal> planning) {
        Object[] datesHeader = tranformPlanningtoArrayOfDates(planning);

        Object[] breakfasts =  filterPlanningByMomentAndAddMomentLabel("Déjeuner", planning);
        Object[] morningSnacks =  filterPlanningByMomentAndAddMomentLabel("10h", planning);
        Object[] lunchs =  filterPlanningByMomentAndAddMomentLabel("Dîner", planning);
        Object[] afternoonSnacks =  filterPlanningByMomentAndAddMomentLabel("4h", planning);
        Object[] dinners =  filterPlanningByMomentAndAddMomentLabel("Souper", planning);

        Object [][] data = {breakfasts, morningSnacks, lunchs, afternoonSnacks, dinners};
        JTable jTable = new JTable(data, datesHeader);
        jTable.setEnabled(false);
        return jTable;
    }
    private Object[] tranformPlanningtoArrayOfDates(ArrayList<Meal> planning){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM");
        //transform Gregorian Calendar date to string with format dd MMM
        List<String> dateHeaders = planning.stream().map(meal -> dateFormat.format(meal.getDate().getTime())).distinct().toList();
        ArrayList<String> dateHeadersArraysList = new ArrayList<>(dateHeaders);
        dateHeadersArraysList.add(0, "");
        return dateHeadersArraysList.toArray();
    }

    private Object[] filterPlanningByMomentAndAddMomentLabel(String moment, ArrayList<Meal> planning) {
        List<String> list = planning.stream().filter(m -> m.getMoment().equals(moment)).map(meal -> meal.getDishName()).toList();
        ArrayList<String> listArraysList = new ArrayList<>(list);
        listArraysList.add(0,moment);

        return listArraysList.toArray();

    }

    private JScrollPane scrollPaneCreation(JTable jTable) {
        JScrollPane scrollPane = new JScrollPane(jTable);

        scrollPane.setPreferredSize(new Dimension(1500, 110));
        return scrollPane;
    }

    private class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    planningPanel.removeAll();
                    addPlanningIfNotEmpty();
                    planningPanel.revalidate();

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,exception.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
}
