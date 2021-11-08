package data;

import exception.crud.AddException;
import exception.crud.GetException;
import model.Meal;
import model.Nutrition;
import util.MenuDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class BDAccessMenu implements MenuDataAccess {

    private Connection connection;
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public ArrayList<String> getAllDiets() throws GetException {
        try {
            sqlInstruction = "select label from keyword where type = 'Régime alimentaire' and label != 'Sans-Régime'";
            connection = SingletonConnexion.getInstance();
            preparedStatement = connection.prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();
            String keyword;
            ArrayList<String> allDiets = new ArrayList<>();
            while (data.next()) {
                keyword = data.getString("label");
                allDiets.add(keyword);
            }
            return allDiets;
        } catch (SQLException throwables) {
            throw new GetException("des régimes alimentaires");
        }
    }

    public void addNutrition(Nutrition nutrition) throws AddException {
        try {
            sqlInstruction = "insert into nutrition (person_id,label) values(?,?)";
            connection = SingletonConnexion.getInstance();
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, nutrition.getPersonId());
            preparedStatement.setString(2, nutrition.getLabel());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new AddException("des régimes alimentaires");
        }
    }

    public void addMenuAndComposition(String pseudo, String moment, GregorianCalendar date, int dishCode) throws AddException {
        try {
            sqlInstruction = "insert into menu (date,moment,person_id) values(?,?,?)";
            connection = SingletonConnexion.getInstance();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("Savepoint");
            preparedStatement = connection.prepareStatement(sqlInstruction, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new java.sql.Date(date.getTimeInMillis()));
            preparedStatement.setString(2, moment);
            preparedStatement.setString(3, pseudo);
            int nbRows = preparedStatement.executeUpdate();
            data = preparedStatement.getGeneratedKeys();

            if (nbRows == 1){
                int menuCode = -1;
                if(data.next())
                    menuCode = data.getInt(1);

                connection.commit();
                sqlInstruction = "insert into composition (dish_code,code_menu) values(?,?)";
                preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setInt(1, dishCode);
                preparedStatement.setInt(2, menuCode);
                nbRows += preparedStatement.executeUpdate();
                if (nbRows == 2)
                    connection.commit();
                else
                    connection.rollback(savepoint);

            }else{
                connection.rollback(savepoint);
            }

        } catch (Exception exception) {
            throw new AddException("des menus ou des compositions de menu de " + pseudo);
        }
    }

    public ArrayList<GregorianCalendar> getMenusDatesOfSomeone(String pseudo) throws GetException {
        try {
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select date from menu where person_id = ? ";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, pseudo);

            java.sql.Date sqlDate;
            GregorianCalendar calendar;
            ArrayList<GregorianCalendar> menus = new ArrayList<>();
            data = preparedStatement.executeQuery();
            while (data.next()){
                sqlDate = data.getDate("date");
                calendar = new GregorianCalendar();
                calendar.setTime(sqlDate);
                menus.add(calendar);

            }
            return menus;
        } catch (Exception e) {
            throw new GetException("des dates de menu de " + pseudo);
        }
    }



    public ArrayList<Meal> getPlanningOfSomeone(String pseudo) throws GetException {
        try {
            connection = SingletonConnexion.getInstance();
            sqlInstruction = "select menu.date, menu.moment, dish.label " +
                    "from menu join composition " +
                    "on menu.code = composition.code_menu " +
                    "join dish  " +
                    "on composition.dish_code = dish.dish_id " +
                    "where person_id = ?" +
                    "and date >= curdate() " +
                    "order by date;";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, pseudo);

            java.sql.Date sqlDate;
            String moment, dishName;

            GregorianCalendar calendar;

            ArrayList<Meal> meals = new ArrayList<>();
            data = preparedStatement.executeQuery();
            while (data.next()){
                sqlDate = data.getDate("date");
                moment = data.getString("moment");
                dishName = data.getString("label");
                calendar = new GregorianCalendar(); //if no reset, all date of meals will be equal at the last one
                calendar.setTime(sqlDate);
                meals.add(new Meal(calendar,moment, dishName));

            }
            return meals;
        } catch (Exception exception) {
            throw new GetException("du planning de " + pseudo);
        }
    }

}
