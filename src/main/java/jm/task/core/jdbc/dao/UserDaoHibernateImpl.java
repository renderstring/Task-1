package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Iterator;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
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

        try (Session session = sessionFactory.openSession())
        {
            var transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Таблица успешно создана.");
        } catch (HibernateException e) {
            System.out.println("Не удалось создать таблицу.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS itm.public.users";

        try (Session session = sessionFactory.openSession())
        {
            var transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Таблица удалена.");
        } catch (HibernateException e) {
            System.out.println("Не удалось удалить таблицу.");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession())
        {
            var transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();

            System.out.println("Пользователь с именем - " + name + " добавлен в БД.");
        } catch (HibernateException e) {
            System.out.println("Не удалось добавить пользователя.");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession())
        {
            var transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();

            System.out.println("Пользователь с ID - " + id + " удален.");
        } catch (HibernateException e) {
            System.out.println("Не удалось удалить пользователя с ID - " + id);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {

        List users = null;
        try (Session session = sessionFactory.openSession())
        {
            var transaction = session.beginTransaction();
            users = session.createQuery("from User").list();
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.openSession())
        {
            var transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();

            System.out.println("Таблица очищена.");
        } catch (HibernateException e) {
            System.out.println("Не удалось очистить таблицу.");
            throw new RuntimeException(e);
        }
    }
}
