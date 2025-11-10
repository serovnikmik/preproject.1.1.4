package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS `user`(" +
            "id BIGINT(1) AUTO_INCREMENT, " +
            "name VARCHAR(45) NOT NULL, " +
            "lastName VARCHAR(45) NOT NULL, " +
            "age TINYINT(1) NOT NULL, " +
            "PRIMARY KEY(id) " +
            ")";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS `user`";
    private static final String CLEAN_TABLE = "DELETE FROM `user`";

//    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session currentSession = Util.getSessionFactory().openSession()){
            transaction = currentSession.beginTransaction();
            currentSession.createSQLQuery(CREATE_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы:" + e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session currentSession = Util.getSessionFactory().openSession()){
            transaction = currentSession.beginTransaction();
            currentSession.createSQLQuery(DROP_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка приудалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session currentSession = Util.getSessionFactory().openSession()){
            transaction = currentSession.beginTransaction();
            currentSession.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Transaction is rolled back.");
            }
            System.out.println("Ошибка придобавлении user-а: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session currentSession = Util.getSessionFactory().openSession()){
            transaction = currentSession.beginTransaction();
            User userToBeDeleted = currentSession.get(User.class, id);
            if (userToBeDeleted != null){
                currentSession.delete(userToBeDeleted);
                System.out.println("User with id " + id + " has been deleted.");
            } else {
                System.out.println("User with id " + id + " is not found.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Transaction is rolled back.");
            }
            System.out.println("Ошибка при удалении user-а: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session currentSession = Util.getSessionFactory().openSession()){
            transaction = currentSession.beginTransaction();
            users = currentSession.createQuery("from User").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Transaction is rolled back.");
            }
            System.out.println("Ошибка при получении всех user-ов: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session currentSession = Util.getSessionFactory().openSession()){
            transaction = currentSession.beginTransaction();
            currentSession.createSQLQuery(CLEAN_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }
    }
}
