package data;

import exception.crud.GetException;
import model.QuantityForIngredient;
import util.DishDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DBAccesDish implements DishDataAccess {

    private Connection connection;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    @Override
    public ArrayList<String> getDishLabelFor(String pseudo) throws GetException {
        try {

            connection = SingletonConnexion.getInstance();
            sqlInstruction =
                    "select dish.label, keyword.label from nutrition nut join keyword on nut.label = keyword.label join information info " +
                            "on keyword.label = info.keyword join dish on info.dish_id = dish.dish_id where nut.person_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, pseudo);
            data = preparedStatement.executeQuery();

            String keyword;
            String dishName;
            ArrayList<String> keywords = new ArrayList<>();
            ArrayList<String> dishesName = new ArrayList<>();
            while (data.next()) {
                dishName = data.getString(1);
                dishesName.add(dishName);
                keyword = data.getString(2);
                if(!keywords.contains(keyword))
                    keywords.add(keyword);
            }
            if(keywords.size() > 1) {
                String[] distinctDishesName = dishesName.stream().distinct().toArray(String[] :: new);
                ArrayList<String> sortedDishesName = new ArrayList<>();
                int frequency;
                for (String dish: distinctDishesName) {
                    frequency = Collections.frequency(dishesName, dish);
                    if (frequency == keywords.size())
                        sortedDishesName.add(dish);

                }
                return sortedDishesName;
            }

            return dishesName;

        } catch (Exception e) {
            throw new GetException("des plats disponibles pour " + pseudo);
        }
    }

    public HashMap<String, ArrayList<String>> getDishesWithTypes(ArrayList<String> dishesList) throws GetException {
        try {
            HashMap<String, ArrayList<String>> dishesWithTypes = new HashMap<>();
            ArrayList<String> dishes = new ArrayList<>();
            ArrayList<String> breakfasts = new ArrayList<>();
            ArrayList<String> snacks = new ArrayList<>();
            String type;

            for (String dish : dishesList) {
                connection = SingletonConnexion.getInstance();
                sqlInstruction =
                        "select keyword.label " +
                                "from keyword join information info " +
                                "on keyword.label = info.keyword " +
                                "join dish " +
                                "on info.dish_id = dish.dish_id " +
                                "where dish.label = ? " +
                                "and keyword.type = 'Repas';";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setString(1, dish);

                data = preparedStatement.executeQuery();
                while (data.next()) {
                    type = data.getString("label");
                    switch (type) {
                        case "Plat" -> dishes.add(dish);
                        case "Collation" -> snacks.add(dish);
                        case "Petit-déjeuner" -> breakfasts.add(dish);
                    }
                }
                dishesWithTypes.put("Plat", dishes);
                dishesWithTypes.put("Petit-déjeuner", breakfasts);
                dishesWithTypes.put("Collation", snacks);
            }
            return dishesWithTypes;

        } catch (Exception e) {
            throw new GetException("des plats correspondant à chaque type");
        }
    }

    public int getDishCode(String dish) throws GetException {
        try {
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select dish_id from dish where label = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, dish);
            data = preparedStatement.executeQuery();
            int dishCode = - 1;
            if (data.next())
                dishCode = data.getInt("dish_id");
            return dishCode;
        } catch (Exception e) {
            throw new GetException("du numéro de plat du plat : " + dish);
        }
    }
    public ArrayList<QuantityForIngredient> getRecipeInformation(int dishId) throws GetException {
        try {
            ArrayList<QuantityForIngredient> quantityForIngredientArrayList = new ArrayList<>();
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select distinct quantity.volume, quantity.unit, ingredient.name, ingredient.calories "
                    + "from quantity join ingredient on quantity.ingredient = ingredient.name "
                    + "where quantity.dish_id = ?;";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, dishId);
            QuantityForIngredient  quantityForIngredient;
            data = preparedStatement.executeQuery();
            while (data.next()) {
                quantityForIngredient = new QuantityForIngredient(data.getString("name"),data.getDouble("calories"),
                        data.getDouble("volume"),data.getString("unit"));

                quantityForIngredientArrayList.add(quantityForIngredient);
            }

            return  quantityForIngredientArrayList;
        }catch (Exception e){
            throw new GetException(" d'information du plat");
        }
    }

    public String getDishRecipe(int dishId) throws GetException {
        try{
            String recipe = null;
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select recipe from dish where dish_id = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, dishId);
            data = preparedStatement.executeQuery();
            while (data.next())
                recipe = data.getString("recipe");

            return recipe;
        }catch (Exception e){
            throw new GetException("de la recette d'un plat");
        }
    }
}
