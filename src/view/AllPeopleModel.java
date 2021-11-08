package view;

import model.Person;

import javax.swing.table.*;
import java.util.*;

public class AllPeopleModel extends AbstractTableModel {

    private ArrayList<String> columnNames;
    private ArrayList<Person> contents;

    public AllPeopleModel(ArrayList<Person> people){
        this.contents = people;
        columnNames = new ArrayList<>();
        columnNames.add("Pseudo");
        columnNames.add("Nom");
        columnNames.add("Prénom");
        columnNames.add("Nationalité");
        columnNames.add("Anniversaire");
        columnNames.add("Genre");
        columnNames.add("Email");
        columnNames.add("Taille");
        columnNames.add("Poids");
        columnNames.add("Sportif de haut niveau");
        columnNames.add("Nom du coach");

    }

    @Override
    public int getRowCount() {
        return contents.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column){
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int row, int column) {
        Person person = contents.get(row);
        return switch (column) {
            case 0 -> person.getPseudo();
            case 1 -> person.getLastName();
            case 2 -> person.getFirstName();
            case 3 -> person.getNationality();
            case 4 -> person.getBirthday().getTime();
            case 5 -> person.getGender();
            case 6 -> person.getEmail();
            case 7 -> person.getHeight();
            case 8 -> person.getWeight();
            case 9 -> person.getIsHighLevelAthlete();
            case 10 -> person.getCoachName();
            default -> null;
        };
    }

    @Override
    public Class getColumnClass (int column){
        Class c = switch (column) {
            case 0, 1, 2, 3, 5, 6, 9, 10 -> String.class;
            case 7 -> Integer.class;
            case 4 -> Date.class;
            case 8 -> Double.class;
            default -> null;
        };
        return c;
    }
}
