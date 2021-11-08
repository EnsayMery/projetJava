package util;

import exception.crud.GetException;
import model.QuantityForIngredient;

import java.util.ArrayList;
import java.util.HashMap;

public interface DishDataAccess {
     ArrayList<String> getDishLabelFor(String pseudo) throws GetException;
     HashMap<String, ArrayList<String>> getDishesWithTypes(ArrayList<String> dishes) throws GetException;
     int getDishCode(String dish) throws GetException;
     ArrayList<QuantityForIngredient>  getRecipeInformation(int dishId) throws GetException;
     String getDishRecipe(int dishId) throws GetException;
}
