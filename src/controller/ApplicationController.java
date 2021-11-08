package controller;

import business.DishManager;
import business.MenuManager;
import business.PersonManager;
import exception.FormException;
import exception.ObligatoryValueException;
import exception.crud.AddException;
import exception.crud.DeleteException;
import exception.crud.GetException;
import exception.crud.UpdateException;
import model.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ApplicationController {
    private PersonManager managerPerson;
    private DishManager managerDish;
    private MenuManager managerMenu;

    public ApplicationController(){
        managerPerson = new PersonManager();
        managerDish = new DishManager();
        managerMenu = new MenuManager();
    }

    public void addPerson(Person person) throws AddException {
        managerPerson.addPerson(person);
    }
    public ArrayList<Person> getAllPeople() throws GetException {
        return managerPerson.getAllPeople();
    }

    public ArrayList<Coach> getAllCoach() throws  GetException {
        return managerPerson.getAllCoach();
    }
    public ArrayList<String> getAllDiets() throws  GetException {
        return managerMenu.getAllDiets();
    }

    public void deletePerson(String pseudo) throws DeleteException, ObligatoryValueException {
        managerPerson.deletePerson(pseudo);
    }
    public void updatePerson(Person person) throws UpdateException {
        managerPerson.updatePerson(person);
    }

    public ArrayList<String> getDishLabelFor(String pseudo) throws GetException, ObligatoryValueException {
        return managerDish.getDishLabelFor(pseudo);
    }
    public void addNutrition(Nutrition nutrition) throws AddException {
        managerMenu.addNutrition(nutrition);
    }
    public HashMap<String, ArrayList<String>> getDishesWithTypes(ArrayList<String> dishes) throws GetException {
        return managerDish.getDishesWithTypes(dishes);
    }
    public void addMenuAndComposition(String pseudo, String moment, GregorianCalendar date, int dishCode) throws AddException, ObligatoryValueException, FormException {
        managerMenu.addMenuAndComposition(pseudo, moment, date, dishCode);
    }
    public int getDishCode(String dish) throws GetException, FormException {
        return managerDish.getDishCode(dish);
    }
    public ArrayList<GregorianCalendar> getMenusDatesOfSomeone(String pseudo) throws GetException, ObligatoryValueException {
        return managerMenu.getMenusDatesOfSomeone(pseudo);
    }
    public ArrayList<Meal> getPlanningOfSomeone(String pseudo) throws GetException, ObligatoryValueException {
        return managerMenu.getPlanningOfSomeone(pseudo);
    }

    public ArrayList<QuantityForIngredient>  getRecipeInformation(int dishId) throws GetException, FormException {
        return managerDish.getRecipeInformation(dishId);
    }

    public String getDishRecipe(int dishId) throws GetException, FormException {
        return managerDish.getDishRecipe(dishId);
    }

    public double caloriesForDish(int dishId) throws GetException, FormException {
        return managerDish.caloriesForDish(dishId);
    }

    public double getCaloriesFor(String pseudo) throws GetException, ObligatoryValueException {
        return managerPerson.getCaloriesFor(pseudo);
    }
}
