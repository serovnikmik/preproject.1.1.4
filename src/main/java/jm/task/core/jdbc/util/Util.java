package jm.task.core.jdbc.util;

import com.mysql.cj.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/1.1.3";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public Util() {
    }

    private static SessionFactory buildSessionFactory() {
        try{
            Configuration cfg = new Configuration();
            cfg.setProperty("hibernate.connection.url", URL);
            cfg.setProperty("hibernate.connection.username", USER);
            cfg.setProperty("hibernate.connection.password", PASSWORD);
            cfg.setProperty("hibernate.dialect", DIALECT);
            cfg.setProperty("hibernate.connection.driver_class", DRIVER);
            cfg.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            return cfg.buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("Ошибка при создании SessionFactory");
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        try {
            sessionFactory.close();
        } catch (Throwable ex) {
            System.out.println("Error closing SessionFactory");
        }
    }
}
