package data;

import exception.crud.AddException;
import exception.crud.DeleteException;
import exception.crud.GetException;
import exception.crud.UpdateException;
import model.*;
import util.PersonDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DBAccessPerson implements PersonDataAccess {
    private Connection connection;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;
    private Savepoint savepointBeforeDelete;

    @Override
    public void createPerson(Person person) throws AddException {
        try {
            sqlInstruction = "insert into person (pseudo,lastname,nationality,birthday,height,weight,is_high_level_athlete,gender) values(?,?,?,?,?,?,?,?)";
            connection = SingletonConnexion.getInstance();
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, person.getPseudo());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getNationality());
            preparedStatement.setDate(4, new java.sql.Date(person.getBirthday().getTimeInMillis()));
            preparedStatement.setInt(5, person.getHeight());
            preparedStatement.setDouble(6, person.getWeight());
            preparedStatement.setInt(7, person.getHighLevelAthlete());
            preparedStatement.setString(8, person.getGender());
            preparedStatement.executeUpdate();
            if (person.getFirstName() != null) {
                sqlInstruction = "update person set firstname = ? where pseudo = ? ";
                preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getPseudo());
                preparedStatement.executeUpdate();
            }

            if (person.getEmail() != null) {
                sqlInstruction = "update person set email = ? where pseudo = ? ";
                preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setString(1, person.getEmail());
                preparedStatement.setString(2, person.getPseudo());
                preparedStatement.executeUpdate();
            }
            if (person.getCoach_id() != null) {
                sqlInstruction = "update person set coach_id = ? where pseudo = ? ";
                preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setInt(1, person.getCoach_id());
                preparedStatement.setString(2, person.getPseudo());
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            throw new AddException("de "+person.getPseudo());
        }
    }

    @Override
    public ArrayList<Person> getAllPeople() throws GetException {
        try {
            // Exécuter le select sur la table person
            String sqlInstruction = "select * from person";
            connection = SingletonConnexion.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Person person;
            String firstName;
            String email;
            int coach_id;
            String coachName;

            ArrayList<Person> allPeople = new ArrayList<>();
            // Boucler sur toutes les lignes du ResultSet
            while (data.next()) {

                Date birthday = data.getDate("birthday");
                GregorianCalendar birthdayGrego = new GregorianCalendar();
                birthdayGrego.setTime(birthday);

                person = new Person(data.getString("pseudo"), data.getString("lastname"),
                        data.getString("nationality"), birthdayGrego,
                        data.getString("gender"), data.getInt("height"),
                        data.getDouble("weight"), data.getInt("is_high_level_athlete"));

                firstName = data.getString("firstname");
                if (!data.wasNull()) {
                    person.setFirstName(firstName);
                }

                email = data.getString("email");
                if (!data.wasNull()) {
                    person.setEmail(email);
                }

                coach_id = data.getInt("coach_id");
                if (!data.wasNull()) {
                    person.setCoach_id(coach_id);
                }

                if(person.getCoach_id() != null) {
                    coachName = getNameCoach(coach_id);
                    person.setCoachName(coachName);
                }

                allPeople.add(person);
            }
            return allPeople;
        } catch (Exception throwables) {
            throw new GetException("de la table person");
        }
    }

    @Override
    public void updatePerson(Person person) throws UpdateException {
        try {
            connection = SingletonConnexion.getInstance();
            connection.setAutoCommit(false);
            sqlInstruction = "update person set " +
                    "lastname = ?, " +
                    "firstname = ?, " +
                    "nationality = ?, " +
                    "birthday = ?, " +
                    "gender = ?, " +
                    "email = ?, " +
                    "height = ?, " +
                    "weight = ?, " +
                    "is_high_level_athlete = ?, " +
                    "coach_id = ? " +
                    "where pseudo = ?";
            PreparedStatement prepStat = connection.prepareStatement(sqlInstruction);
            prepStat.setString(1, person.getLastName());
            if (person.getFirstName() != null)
                prepStat.setString(2, person.getFirstName());
            else
                prepStat.setNull(2, Types.VARCHAR);
            prepStat.setString(3, person.getNationality());
            prepStat.setDate(4, new java.sql.Date(person.getBirthday().getTimeInMillis()));
            prepStat.setString(5, person.getGender());
            if (person.getEmail() != null)
                prepStat.setString(6, person.getEmail());
            else
                prepStat.setNull(6, Types.VARCHAR);
            prepStat.setInt(7, person.getHeight());
            prepStat.setDouble(8, person.getWeight());
            prepStat.setInt(9, person.getHighLevelAthlete());
            if (person.getCoach_id() != null)
                prepStat.setInt(10, person.getCoach_id());
            else
                prepStat.setNull(10, Types.INTEGER);
            prepStat.setString(11, person.getPseudo());
            int nbOfRowsEdited = prepStat.executeUpdate();
            if (nbOfRowsEdited != 1) {
                connection.rollback();
                throw new UpdateException("de " + person.getPseudo());
            }
            connection.commit();

        } catch (Exception e) {
            throw new UpdateException("de " + person.getPseudo());
        }
    }

    @Override
    public void deletePerson(String pseudo) throws DeleteException {

        try {
            connection = SingletonConnexion.getInstance();
            this.savepointBeforeDelete = connection.setSavepoint();
            sqlInstruction = "delete from nutrition where person_id = ?";

            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, pseudo);
            preparedStatement.executeUpdate();

            sqlInstruction = "delete  compo " +
                    "from composition as compo join menu " +
                    "on compo.code_menu = menu.code " +
                    "where menu.person_id = ? ";
            preparedStatement = connection.prepareStatement(sqlInstruction);

            preparedStatement.setString(1, pseudo);
            preparedStatement.executeUpdate();

            sqlInstruction = "delete from menu where person_id = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);

            preparedStatement.setString(1, pseudo);
            preparedStatement.executeUpdate();

            sqlInstruction = "delete from person where pseudo = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);

            preparedStatement.setString(1, pseudo);
            preparedStatement.executeUpdate();




        } catch (Exception e) {
            try {
                connection.rollback(savepointBeforeDelete);
                throw new DeleteException("de " + pseudo);
            } catch (Exception exception) {
                throw new DeleteException("de " + pseudo);
            }
        }
    }

    @Override
    public ArrayList<Coach> getAllCoach() throws GetException {
        try {
            // Exécuter le select sur la table person
            String sqlInstruction = "select * from coach";
            connection = SingletonConnexion.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();
            Coach coach;

            ArrayList<Coach> allCoaches = new ArrayList<>();
            // Boucler sur toutes les lignes du ResultSet
            while (data.next()) {

                coach = new Coach(data.getInt("coach_id"), data.getString("firstname"),
                        data.getString("lastname"));

                allCoaches.add(coach);
            }
            return allCoaches;
        } catch (Exception throwables) {
            throw new GetException(" des coachs");
        }
    }

    public Person getPerson(String pseudo) throws GetException {
        try{
            Person person = null;
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select * from person where pseudo = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, pseudo);
            data = preparedStatement.executeQuery();
            while (data.next()) {

                Date birthday = data.getDate("birthday");
                GregorianCalendar birthdayGrego = new GregorianCalendar();
                birthdayGrego.setTime(birthday);

                person = new Person(data.getString("pseudo"), data.getString("lastname"),
                        data.getString("nationality"), birthdayGrego,
                        data.getString("gender"), data.getInt("height"),
                        data.getDouble("weight"), data.getInt("is_high_level_athlete"));
            }
            return person;
        }catch (Exception e){
            throw new GetException("de " + pseudo);
        }
    }

    public String getNameCoach(int coach_id) throws GetException {
        try{
            String nameCoach = null;
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select firstname from coach where coach_id = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, coach_id);
            data = preparedStatement.executeQuery();
            while (data.next()) {
                nameCoach = data.getString("firstname");
            }
            return nameCoach;
        }catch (Exception e){
            throw new GetException("du coach numéro " + coach_id);
        }
    }
}
