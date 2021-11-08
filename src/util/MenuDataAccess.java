package util;

import exception.crud.AddException;
import exception.crud.GetException;
import model.Meal;
import model.Nutrition;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface MenuDataAccess {
     ArrayList<String> getAllDiets() throws GetException;
     void addNutrition(Nutrition nutrition) throws AddException;
     void addMenuAndComposition(String pseudo, String moment, GregorianCalendar date, int dishCode) throws AddException;
     ArrayList<GregorianCalendar> getMenusDatesOfSomeone(String pseudo) throws GetException;
     ArrayList<Meal> getPlanningOfSomeone(String pseudo) throws GetException;
}
