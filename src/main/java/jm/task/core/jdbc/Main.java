package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Иван", "Иванов", (byte) 18);
        userDaoHibernate.saveUser("Петр", "Петров", (byte) 20);
        userDaoHibernate.saveUser("Мария", "Мариева", (byte) 22);
        userDaoHibernate.saveUser("Кирилл", "Кириллов", (byte) 24);
        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
    }
}
