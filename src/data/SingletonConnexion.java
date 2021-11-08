package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexion {
    private static Connection uniqueConnection;
    public static Connection getInstance() {
        if(uniqueConnection == null){
            try {
                uniqueConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sport_diet", "professeur", "premier");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
       return uniqueConnection;
    }
}
