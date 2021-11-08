package view;

import controller.ApplicationController;
import model.QuantityForIngredient;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class AllQuantitiesForIngredientsPanel extends JPanel {

    public AllQuantitiesForIngredientsPanel(int dishId){
        try {

            ApplicationController controller = new ApplicationController();
            this.setLayout(new BorderLayout());

            ArrayList<QuantityForIngredient> quantityForIngredient = controller.getRecipeInformation(dishId);


            /* ----- Mise en place JTable ----- */

            AllQuantitiesForIngredientsModel model = new AllQuantitiesForIngredientsModel(quantityForIngredient);
            JTable table = new JTable(model);
            for(int i = 0; i < 4 ;i++){
                TableColumn column = table.getColumnModel().getColumn(i);
                column.setPreferredWidth(200);
            }

            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane scrollPane = new JScrollPane(table);

            /* ------ Ajout panel ----- */
            this.add(scrollPane, BorderLayout.CENTER);
            setVisible(true);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage() , "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
