package view;
import controller.ApplicationController;
import exception.*;
import model.Coach;
import model.Nutrition;
import model.Person;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.*;
import java.util.List;


public class RegistrationPanel extends JPanel {
    private JLabel lastnameLabel;
    private JLabel firstnameLabel;
    private JLabel pseudoLabel;
    private JLabel nationalityLabel;
    private JLabel coachLabel;
    private JLabel birthdayLabel;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel emailLabel;
    private JLabel selectDietsLabel;
    private JLabel nonRequiredValues;
    private JTextField lastname, firstname,pseudo,email,height,weight;
    private JSpinner birthday;
    private JComboBox nationality, coaches;
    private ButtonGroup buttonGroup;
    private JRadioButton female, male;
    private JCheckBox isHeighLevelAthlete, hasAParticularDiet, withoutCoach;
    private JList diets;
    private JButton copy;
    private boolean isModifyPanel;

    private ArrayList<Coach> coachArrayList;




    public RegistrationPanel(boolean isModifyPanel) {
        this.isModifyPanel = isModifyPanel;
        this.setLayout(new GridLayout(15,2,50,5));
        createField();
        addFieldToPannel();
        addListener();
        if(!isModifyPanel) createAndAddListenerToDietsFields();
    }

    public void createField(){
        try {
            lastnameLabel = new JLabel("Nom : ");
            lastname = new JTextField();
            firstnameLabel = new JLabel("Prénom : *");
            firstname = new JTextField();
            pseudoLabel = new JLabel("Pseudo :");
            pseudo = new JTextField();
            emailLabel = new JLabel("Email : *");
            email = new JTextField();
            nationalityLabel = new JLabel("Nationalité :");
            nationality = new JComboBox(Locale.getISOCountries());
            nationality.setSelectedItem(Locale.getISOCountries()[19]);
            nationality.setMaximumRowCount(5);
            nationality.setEditable(false);
            birthdayLabel = new JLabel("Date de naissance :");
            birthday = new JSpinner(new SpinnerDateModel(Util.getFormat().parse(Util.getEndingDate()),Util.getFormat().parse(Util.getDATE()),Util.getFormat().parse(Util.getEndingDate()),1));
            birthday.setEditor(new JSpinner.DateEditor(birthday, "dd-MM-yyyy"));
            heightLabel = new JLabel("Taille :");
            height = new JTextField("170");
            weightLabel = new JLabel("Poids :");
            weight = new JTextField("70.2");
            isHeighLevelAthlete = new JCheckBox("Sportif de haut niveau");
            withoutCoach = new JCheckBox("Sans coach");
            male = new JRadioButton("Homme",false);
            female = new JRadioButton("Femme",true);
            buttonGroup = new ButtonGroup();
            buttonGroup.add(male);
            buttonGroup.add(female);
            coachLabel = new JLabel("Coach :");
            coachArrayList = new ApplicationController().getAllCoach();
            String[] firstnamesCoaches = coachArrayList.stream().map(coach -> coach.getFirstname()).toArray(String[]::new);
            coaches = new JComboBox(firstnamesCoaches);
            coaches.setSelectedIndex(0);
            coaches.setEditable(false);
            nonRequiredValues = new JLabel("* : valeurs non obligatoires");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addFieldToPannel(){
        this.add(lastnameLabel);
        this.add(lastname);
        this.add(firstnameLabel);
        this.add(firstname);
        this.add(pseudoLabel);
        this.add(pseudo);
        this.add(emailLabel);
        this.add(email);
        this.add(nationalityLabel);
        this.add(nationality);
        this.add(birthdayLabel);
        this.add(birthday);
        this.add(heightLabel);
        this.add(height);
        this.add(weightLabel);
        this.add(weight);
        this.add(new JLabel());
        this.add(withoutCoach);
        this.add(coachLabel);
        this.add(coaches);
        this.add(male);
        this.add(female);
        this.add(isHeighLevelAthlete);
        if(isModifyPanel){
            this.add(new JLabel());
            this.add(nonRequiredValues);
        }

    }
    private void createAndAddListenerToDietsFields(){
        try {
            //Create fields
            hasAParticularDiet = new JCheckBox("A un régime particuler");
            ArrayList<String> dietArrayList;
            dietArrayList = new ApplicationController().getAllDiets();
            diets = new JList(dietArrayList.toArray());
            diets.setVisibleRowCount(2);
            diets.setSelectedIndex(0);
            diets.setEnabled(false);
            copy = new JButton("Selectionner >>>");
            copy.setEnabled(false);
            JLabel dietLabel = new JLabel("Régime(s) alimentaire(s) (ctrl + clic pour en sélectionner plusieurs) : ");
            selectDietsLabel = new JLabel("[aucun]");

            //Add fields
            this.add(hasAParticularDiet);
            this.add(dietLabel);
            this.add(selectDietsLabel);
            this.add(new JScrollPane((diets)));
            this.add(copy);
            this.add(nonRequiredValues);

            //Add Listener
            copy.addActionListener(new TextListener());
            hasAParticularDiet.addItemListener(new CheckBoxListener());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void addListener() {
        withoutCoach.addItemListener(new CheckBoxListener());
        lastname.addActionListener(new TextListener());
        height.addActionListener(new TextListener());
        weight.addActionListener(new TextListener());

    }

    private int getHeightValue() throws ObligatoryValueException,FormException {
        if (Util.isBlankOrEmpty(height.getText()))
            throw new ObligatoryValueException("taille");
        if(!Util.isNumber(height.getText()))
            throw new FormException("La taille","un nombre");
        return Integer.parseInt(height.getText());
    }

    private double getWeightValue() throws ObligatoryValueException, FormException {
        if (Util.isBlankOrEmpty(weight.getText()))
            throw new ObligatoryValueException("poids");
        if(!Util.isNumber(weight.getText()))
            throw new FormException("Le poids","un nombre");
        return  Double.parseDouble(weight.getText());
    }


    public void setDefault(){
        lastname.setText("");
        firstname.setText("");
        pseudo.setText("");
        email.setText("");
        nationality.setSelectedItem(Locale.getISOCountries()[19]);
        height.setText("170");
        weight.setText("70.2");
        isHeighLevelAthlete.setSelected(false);
        hasAParticularDiet.setSelected(false);
        female.setSelected(true);
        coaches.setSelectedIndex(0);
        selectDietsLabel.setText("[aucun]");
        diets.setSelectedIndex(0);
        withoutCoach.setSelected(false);
        try {
            birthday.setValue(Util.getFormat().parse(Util.getEndingDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void validateFields() throws FormException, ObligatoryValueException {
        Util.validateLastname(lastname.getText());
        Util.validatePseudo(pseudo.getText());
        Util.validateFirstname(firstname.getText());
        Util.validateHeight(getHeightValue());
        Util.validateWeight(getWeightValue());
        Util.validateMail(email.getText());
    }

    public void setPerson(Person person){
        lastname.setText(person.getLastName());
        if (person.getFirstName() != null)
            firstname.setText(person.getFirstName());
        pseudo.setText(person.getPseudo());
        pseudo.setEnabled(false);
        if (person.getEmail() != null)
            email.setText(person.getEmail());
        List<String> country = Arrays.stream(Locale.getISOCountries()).toList();
        nationality.setSelectedItem(Locale.getISOCountries()[country.indexOf(person.getNationality())]);
        birthday.setValue(person.getBirthday().getTime());
        height.setText(person.getHeight().toString());
        weight.setText(person.getWeight().toString());
        isHeighLevelAthlete.setSelected(person.getHighLevelAthlete() == 1);
        if (person.getGender().equals("f"))
            female.setSelected(true);
        else
            male.setSelected(true);

        if(person.getCoach_id() != null) {
            List<Integer> coachesID = coachArrayList.stream().map(coach -> coach.getCoachId()).toList();
            coaches.setSelectedIndex(coachesID.indexOf(person.getCoach_id()));
        }else{
            withoutCoach.setSelected(true);
        }
    }

    public Person getNewlyRegisteredPerson() throws ObligatoryValueException, FormException, ParseException {
        String lastnameValue = lastname.getText();
        String firstnameValue = Util.isBlankOrEmpty(firstname.getText())? null :firstname.getText();
        String pseudoValue = pseudo.getText();
        String nationalityValue = Locale.getISOCountries()[nationality.getSelectedIndex()];
        String genderValue = buttonGroup.getSelection() == male.getModel() ? "m":"f";
        String emailValue = Util.isBlankOrEmpty(email.getText())? null :email.getText();
        Integer heightValue = getHeightValue();
        Double weightValue = getWeightValue();
        Integer isHighLevelValue = isHeighLevelAthlete.isSelected() ? 1 : 0;
        Integer coachIdValue;
        if(withoutCoach.isSelected())
            coachIdValue = null;
        else
            coachIdValue = coaches.getSelectedIndex()+1;
        GregorianCalendar bithdayValue = Util.tranformDateToGregorianCalendar((Date) birthday.getValue());
        return new Person(pseudoValue,lastnameValue,firstnameValue,nationalityValue,bithdayValue,genderValue,emailValue,heightValue,weightValue,isHighLevelValue,null,coachIdValue);
    }

    public ArrayList<Nutrition> getNewlyDietList() throws ObligatoryValueException {
        ArrayList<Nutrition> nutritions = new ArrayList<>();
        if(hasAParticularDiet.isSelected()){
            for(Object diet : diets.getSelectedValuesList()){
                nutritions.add(new Nutrition(pseudo.getText(),diet.toString()));
            }
        }
        else {
            nutritions.add(new Nutrition(pseudo.getText(),"Sans-Régime"));
        }
        return nutritions;
    }


    private class TextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == lastname) {
                if(lastname.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un nom !","Entrez un nom",JOptionPane.ERROR_MESSAGE);
            }
            if(e.getSource() == height) {
                if(height.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Veuillez entrer une taille en cm !","Entrez une taille",JOptionPane.ERROR_MESSAGE);
            }
            if(e.getSource() == weight) {
                if(weight.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un poids en kg !","Entrez un poids",JOptionPane.ERROR_MESSAGE);
            }
            if(e.getSource() == pseudo) {
                if(pseudo.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Veuillez entrer un pseudo !","Entrez un nom",JOptionPane.ERROR_MESSAGE);
            }
            if(e.getSource() == copy) {
                String data = diets.getSelectedValuesList().toString();
                selectDietsLabel.setText(data);

            }

        }
    }
    private class CheckBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getSource() == hasAParticularDiet) {
                diets.setEnabled(hasAParticularDiet.isSelected());
                copy.setEnabled(hasAParticularDiet.isSelected());
                if(!hasAParticularDiet.isSelected())
                    selectDietsLabel.setText("[aucun]");
            }
            if(e.getSource() == withoutCoach) {
                coaches.setEnabled(!withoutCoach.isSelected());
            }

        }
    }

}
