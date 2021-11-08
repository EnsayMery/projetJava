package view;

import model.QuantityForIngredient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AllQuantitiesForIngredientsModel extends AbstractTableModel {

    private ArrayList<String> columnNames;
    private ArrayList<QuantityForIngredient> contents;

    public AllQuantitiesForIngredientsModel(ArrayList<QuantityForIngredient> ingredientInformation){
        this.contents = ingredientInformation;
        columnNames = new ArrayList<>();
        columnNames.add("Nom");
        columnNames.add("Calories pour 100g");
        columnNames.add("Volume*");
        columnNames.add("Unité");
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
        QuantityForIngredient ingredientInformation = contents.get(row);
        return switch (column) {
            case 0 -> ingredientInformation.getName();
            case 1 -> ingredientInformation.getCalories();
            case 2 -> ingredientInformation.getVolume();
            case 3 -> ingredientInformation.getUnit();
            default -> null;
        };
    }
    @Override
    public Class getColumnClass (int column) {
        Class c = switch (column) {
            case 0, 3 -> String.class;
            case 1, 2 -> Double.class;
            default -> null;
        };
        return c;
    }
}
