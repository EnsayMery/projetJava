package business;

import data.DBAccesDish;
import exception.FormException;
import exception.ObligatoryValueException;
import exception.crud.GetException;
import model.QuantityForIngredient;
import util.DishDataAccess;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class DishManager {

    DishDataAccess dao;
    public DishManager(){
        dao = new DBAccesDish();
    }

    public ArrayList<String> getDishLabelFor(String pseudo) throws GetException, ObligatoryValueException {
        Util.validatePseudo(pseudo);
        return dao.getDishLabelFor(pseudo);
    }

    public HashMap<String, ArrayList<String>> getDishesWithTypes(ArrayList<String> dishes) throws GetException {
        return dao.getDishesWithTypes(dishes);
    }

    public int getDishCode(String dish) throws GetException, FormException {
        if(Util.isBlankOrEmpty(dish))
            throw new FormException("Le nom du plat", "une valeur");
        return dao.getDishCode(dish);
    }

    public ArrayList<QuantityForIngredient> getRecipeInformation(int dishId) throws GetException, FormException {
        Util.validateDishId(dishId);
        return dao.getRecipeInformation(dishId);
    }

    public String getDishRecipe(int dishId) throws GetException, FormException {
        Util.validateDishId(dishId);
        return dao.getDishRecipe(dishId);
    }

    public double caloriesForDish(int dishId) throws GetException, FormException {
        Util.validateDishId(dishId);
        ArrayList<QuantityForIngredient> quantityForIngredient = dao.getRecipeInformation(dishId);
        double calories =0;

        for (QuantityForIngredient quantity:quantityForIngredient) {
            calories += (quantity.getCalories()/100)*quantity.getVolume();
        }

        return calories;
    }
}
