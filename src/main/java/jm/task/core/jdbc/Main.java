package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        System.out.println();

        Thread.sleep(1000);

        userService.createUsersTable();
        System.out.println();

        Thread.sleep(1000);

        User user1 = new User("Ivan", "Ivanov", (byte) 30);
        User user2 = new User("Oleg", "Efremov", (byte) 25);
        User user3 = new User("Igor", "Ermolov", (byte) 20);
        User user4 = new User("Alex", "Alex", (byte) 15);

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println();

        Thread.sleep(1000);

        List<User> list = userService.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }
        System.out.println();

        Thread.sleep(1000);

        userService.cleanUsersTable();
        System.out.println();

        Thread.sleep(1000);

        if (userService.getAllUsers().isEmpty())
            System.out.println("В таблице пусто.");
        System.out.println();

        Thread.sleep(1000);

        userService.dropUsersTable();
    }
}
