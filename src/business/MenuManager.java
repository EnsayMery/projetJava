package business;

import data.BDAccessMenu;
import exception.FormException;
import exception.ObligatoryValueException;
import exception.crud.AddException;
import exception.crud.GetException;
import model.Meal;
import model.Nutrition;
import util.MenuDataAccess;
import util.Util;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MenuManager {

    MenuDataAccess dao;

    public MenuManager(){
        dao = new BDAccessMenu();
    }
    public ArrayList<String> getAllDiets() throws GetException {
        return dao.getAllDiets();
    }

    public void addMenuAndComposition(String pseudo, String moment, GregorianCalendar date, int dishCode) throws AddException, ObligatoryValueException, FormException {
        Util.validatePseudo(pseudo);
        Util.validateDishId(dishCode);
        dao.addMenuAndComposition(pseudo, moment, date, dishCode);
    }

    public ArrayList<GregorianCalendar> getMenusDatesOfSomeone(String pseudo) throws GetException, ObligatoryValueException {
        Util.validatePseudo(pseudo);
        return dao.getMenusDatesOfSomeone(pseudo);
    }

    public ArrayList<Meal> getPlanningOfSomeone(String pseudo) throws GetException, ObligatoryValueException {
        Util.validatePseudo(pseudo);
        return dao.getPlanningOfSomeone(pseudo);
    }

    public void addNutrition(Nutrition nutrition) throws AddException {
        dao.addNutrition(nutrition);
    }
}
