package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user (" +
            "id INT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(255) NULL, " +
            "lastname VARCHAR(255) NULL, " +
            "age TINYINT NULL, " +
            "PRIMARY KEY (id)" +
            ");";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user;";
    private static final String CLEAR_TABLE = "TRUNCATE user;";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user;";
    private static final String INSERT_USER = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(DROP_TABLE);
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = new Util().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении user-а: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = new Util().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении user-а: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                User newUser = new User();
                newUser.setId(resultSet.getLong("id"));
                newUser.setName(resultSet.getString("name"));
                newUser.setLastName(resultSet.getString("lastname"));
                newUser.setAge(resultSet.getByte("age"));
                users.add(newUser);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(CLEAR_TABLE);
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }
    }
}
