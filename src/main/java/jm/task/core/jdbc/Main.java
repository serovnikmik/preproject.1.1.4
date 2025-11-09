package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Util util = new Util();

        List<User> users = new ArrayList<>();
        users.add(new User("Rick", "Sanchez", (byte) 70));
        users.add(new User("Morty", "Smith", (byte) 14));
        users.add(new User("Summer", "Smith", (byte) 18));
        users.add(new User("Jerry", "Smith", (byte) 35));

        UserServiceImpl userService = new UserServiceImpl();

        // table creation
        userService.createUsersTable();
        System.out.println("Таблица создана!");

        // adding users
        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("user с именем " + user.getName() + " добавлен в базу данных");
        }
        System.out.println("Юзеры добавлены!");

        // taking users from table
        List<User> usersFromTable = userService.getAllUsers();
        System.out.println("Юзеры из таблицы:");
        for (User user : usersFromTable) {
            System.out.println(user);
        }

        // clearing table
        userService.cleanUsersTable();
        System.out.println("Таблица очищена!");
        int usersInTable = userService.getAllUsers().size();
        System.out.println("Юзеров втаблице: " + usersInTable);

        // deleting table
        userService.dropUsersTable();
        System.out.println("Таблица удалена!");

        Util.shutdown();
    }
}
