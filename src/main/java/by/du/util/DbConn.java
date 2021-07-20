package by.du.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public enum DbConn {

    INSTANCE;

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            log.info("Create connection to DB");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = Props.getValue("url").orElse("localhost:3306");
                String db = Props.getValue("db").orElse("test");
                String user = Props.getValue("user").orElse("root");
                String pass = Props.getValue("pass").orElse("pass");
                connection = DriverManager.getConnection("jdbc:mysql://" + url + "/" + db, user, pass);
                log.info("Connection to DB is created");
            } catch (SQLException | ClassNotFoundException exception) {
                throw new IllegalStateException();
            }
        }
        return connection;
    }
}
