package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/itm";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";
    private static final String DRIVER = "org.postgresql.Driver";

    private Util() {}

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Properties props = new Properties();
            props.setProperty("connection.driver", DRIVER);
            props.setProperty("hibernate.connection.url", URL);
            props.setProperty("hibernate.connection.username", USERNAME);
            props.setProperty("hibernate.connection.password", PASSWORD);
//            props.setProperty("hibernate.show_sql", "true");
            props.setProperty("hibernate.format_sql", "true");

            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(User.class);
            cfg.setProperties(props);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build();

            sessionFactory = cfg.buildSessionFactory(serviceRegistry);

            System.out.println("Соединение с БД установлено.");
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

        return sessionFactory;
    }

    public static Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение с БД установлено.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Не удалось установить соединение с БД.");
            throw new RuntimeException(e);
        }

        return connection;
    }
}
