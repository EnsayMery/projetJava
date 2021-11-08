package view;

import controller.ApplicationController;
import model.Person;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class AllPeoplePanel extends JPanel{

    private ApplicationController controller;
    private ArrayList<Person> people;

    private ListSelectionModel listSelect;

    public AllPeoplePanel( String action) {

        try {

            this.controller = new ApplicationController();
            this.setLayout(new BorderLayout());

            people = controller.getAllPeople();


            /* ----- Mise en place JTable ----- */

            AllPeopleModel model = new AllPeopleModel(people);
            JTable table = new JTable(model);
            for(int i = 0; i < 11 ;i++){
                TableColumn column = table.getColumnModel().getColumn(i);
                column.setPreferredWidth(135);
            }

            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane scrollPane = new JScrollPane(table);

            JLabel listPerson;
            if(action.equals("modify")){
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listPerson = new JLabel("Listing pour modification");

            }else{
                if(action.equals("delete"))
                    listPerson = new JLabel("Listing pour suppression");
                else
                    listPerson = new JLabel("Listing personnes");
                table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            }

            listSelect = table.getSelectionModel( );

            /* ------ Ajout panel ----- */
            this.add(listPerson,BorderLayout.NORTH);
            this.add(scrollPane, BorderLayout.CENTER);

            setVisible(true);


        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,exception.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ListSelectionModel getListSelect() {
        return listSelect;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ApplicationController getController() {
        return controller;
    }
}
