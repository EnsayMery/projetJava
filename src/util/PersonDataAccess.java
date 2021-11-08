package util;

import exception.crud.AddException;
import exception.crud.DeleteException;
import exception.crud.GetException;
import exception.crud.UpdateException;
import model.*;

import java.util.ArrayList;

public interface PersonDataAccess {
     void createPerson(Person person) throws AddException;
     ArrayList<Person> getAllPeople() throws GetException;
     void updatePerson(Person person) throws UpdateException;
     void deletePerson(String pseudo) throws DeleteException;
     ArrayList<Coach> getAllCoach() throws GetException;
     Person getPerson(String pseudo) throws GetException;
     String getNameCoach(int coach_id) throws GetException;
}
