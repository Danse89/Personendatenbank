package academy.mischok.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper class to give access to database connections.
 */
public class ConnectionHelper {

    /**
     * Opens a new database connection. Take care that connections are re-used and closed if not used
     * any longer.
     * @return connection instance
     * @throws SQLException thrown in case of database errors, you can easily handle using try-with-resource
     */
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://%s/%s".formatted(Main.SERVER, Main.DATABASE);

        Connection conn = DriverManager.getConnection(url, Main.USER, Main.PASSWORD);

        System.out.println("Connected to the Neondb server successfully.");

        return conn;
    }
}
