package by.du.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DbConn {

    INSTANCE;

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                String url = Props.getValue("url").orElse("localhost:3306");
                String db = Props.getValue("db").orElse("data");
                String user = Props.getValue("user").orElse("root");
                String pass = Props.getValue("pass").orElse("root");
                connection = DriverManager.getConnection("jdbc:mysql://" + url + "/" + db, user, pass);
            } catch (SQLException exception) {
                throw new IllegalStateException();
            }
        }
        return connection;
    }
}
