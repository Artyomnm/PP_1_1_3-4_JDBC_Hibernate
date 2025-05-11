package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(40), " +
                    "LastName VARCHAR(40), " +
                    "Age INT)").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Таблица User создана");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Ошибка при создании таблицы User");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User удалена");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Ошибка при удалении таблицы User");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("User с именем - " + name + " НЕ добавлен в базу данных");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM User WHERE Id = " + id).executeUpdate();
            session.getTransaction().commit();
            System.out.println("User c id = " + id + " удален");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Не получилось удалить User c id = " + id);
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root);
            users = session.createQuery(cr).getResultList();
            session.getTransaction().commit();
            System.out.println("Список User сформирован");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Ошибка при создании списка User");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User очищена");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Ошибка при очищении таблицы User");
            e.printStackTrace();
        }
    }
}
