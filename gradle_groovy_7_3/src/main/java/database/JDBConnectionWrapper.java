package database;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/"; // 127.0.0.1
    private static final String USER = "root";
    private static final String PASS = "root";
    private static int TIMEOUT = 5;
    private Connection connection;

    public JDBConnectionWrapper(String schema) {
        try {
            Class.forName(JDBC_DRIVER); // reflection tenchique
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASS);
            // bootstrap - pregatim aplicatia pt rulare
            createTables();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "create table if not exists book (" +
                "id bigint not null auto_increment, " +
                "author varchar(500) not null, " +
                "title varchar(500) not null, " +
                "publishedDate datetime default null, " +
                "primary key(id), " +
                "unique key id_unique(id)" +
                ") " + "engine = InnoDB auto_increment = 0 default charset = utf8;";

        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }


    public Connection getConnection() {
        return connection;
    }
}
