package dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static Connection connection;
    private String url="jdbc:mysql://localhost:3306/corriere";
    private String username="root";
    private String password="EasyTracking1+";

    private DatabaseManager() throws SQLException {
        // Inizializza la connessione al database
        connection = DriverManager.getConnection(url, username, password);
    }

    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}
