package business;

import data.DBAccessPerson;
import exception.ObligatoryValueException;
import exception.crud.AddException;
import exception.crud.DeleteException;
import exception.crud.GetException;
import exception.crud.UpdateException;
import model.*;
import util.PersonDataAccess;
import util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class PersonManager {
    PersonDataAccess dao;
    public PersonManager(){
        dao = new DBAccessPerson();

    }
    public void addPerson(Person person) throws AddException {
        dao.createPerson(person);
    }

    public ArrayList<Person> getAllPeople() throws GetException {
        return dao.getAllPeople();
    }
    public ArrayList<Coach> getAllCoach() throws GetException {
        return dao.getAllCoach();
    }

    public void deletePerson(String pseudo) throws DeleteException, ObligatoryValueException {
        Util.validatePseudo(pseudo);
        dao.deletePerson(pseudo);
    }
    public void updatePerson(Person person) throws UpdateException {
        dao.updatePerson(person);
    }

    public double getCaloriesFor(String pseudo) throws GetException, ObligatoryValueException {
        Util.validatePseudo(pseudo);
        Person person = dao.getPerson(pseudo);
        int year ;
        int height = person.getHeight();
        double weight  = person.getWeight();
        String gender = person.getGender();
        GregorianCalendar birthday = person.getBirthday();
        GregorianCalendar todayDate = new GregorianCalendar();

        if(birthday.get(Calendar.DAY_OF_YEAR) < todayDate.get(Calendar.DAY_OF_YEAR)){
            year = todayDate.get(Calendar.YEAR) - birthday.get(Calendar.YEAR) -1;
        }else{
            year = todayDate.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        }

        double nutritionalNeeds;

        if(gender.equals("f")){
            if(year < 18){
                nutritionalNeeds = (9.4*weight + 249*height+462)*2;
            }else{
                if(year < 30){
                    nutritionalNeeds = (10.4*weight + 625*height-282)*2;
                }else{
                    if(year < 60){
                        nutritionalNeeds = (8.18*weight + 502*height -11.6)*2;
                    }else{
                        nutritionalNeeds = (8.52*weight + 421*height+10.7)*2;
                    }
                }
            }
        }else{
            if(year < 18){
                nutritionalNeeds = (15.6*weight + 266*height+299)*2;
            }else{
                if(year < 30){
                    nutritionalNeeds = (14.4*weight + 313*height+113)*2;
                }else{
                    if(year < 60){
                        nutritionalNeeds = (11.4*weight + 541*height -137)*2;
                    }else{
                        nutritionalNeeds = (11.4*weight + 541*height-256)*2;
                    }
                }
            }
        }
        nutritionalNeeds /= 4.184;
        nutritionalNeeds /= 7;
        return nutritionalNeeds;
    }

}
