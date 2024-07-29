package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    // Создание таблицы
    public void createUsersTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS itm.public.users
                (
                    ID bigserial primary key not null,
                    NAME text not null,
                    LASTNAME text not null,
                    AGE integer not null
                )
                """;

        try (Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sql);
            System.out.println("Таблица успешно создана.");
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу.");
            throw new RuntimeException(e);
        }
    }

    // Удаление таблицы
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS itm.public.users";

        try (Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена.");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу.");
        }
    }

    // Добавление в таблицу
    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO itm.public.users (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            System.out.println("Пользователь с именем - " + name + " добавлен в БД.");
        } catch (SQLException e) {
            System.out.println("Не удалось добавить пользователя.");
        }
    }

    // Удаление из таблицы по id
    public void removeUserById(long id) {

        String sql = "DELETE FROM itm.public.users WHERE ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, id);
            ps.executeUpdate();
            System.out.println("Пользователь с ID - " + id + " удален.");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя с ID - " + id);
            throw new RuntimeException(e);
        }
    }

    // Получение содержимого таблицы
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM itm.public.users";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql))
        {
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("NAME");
                String lastName = rs.getString("LASTNAME");
                byte age = rs.getByte("AGE");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);

                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить содержимое таблицы.");
            throw new RuntimeException(e);
        }

        return list;
    }

    // Очищение таблицы
    public void cleanUsersTable() {

        String sql = "DELETE FROM itm.public.users";

        try (Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена.");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу.");
            throw new RuntimeException(e);
        }
    }
}
