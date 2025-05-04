package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDaoJDBCImpl = new UserDaoJDBCImpl();
        userDaoJDBCImpl.createUsersTable();
        userDaoJDBCImpl.saveUser("Иван", "Иванов", (byte) 18);
        userDaoJDBCImpl.saveUser("Петр", "Петров", (byte) 20);
        userDaoJDBCImpl.saveUser("Мария", "Мариева", (byte) 22);
        userDaoJDBCImpl.saveUser("Кирилл", "Кириллов", (byte) 24);
        userDaoJDBCImpl.getAllUsers();
        userDaoJDBCImpl.cleanUsersTable();
        userDaoJDBCImpl.dropUsersTable();
    }
}
