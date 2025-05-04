package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.sql.Insert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS User (" +
                "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                "Name VARCHAR(40), " +
                "LastName VARCHAR(40), " +
                "Age INT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица User создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы User");
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE IF EXISTS User";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица User удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы User");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO User (Name, Lastname, Age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("User с именем - " + name + " НЕ добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM User WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User c id = " + id + " удален");
        } catch (SQLException e) {
            System.out.println("Не получилось удалить User c id = " + id);
        }
    }

    public List<User> getAllUsers() {
        String sqlCommand = "SELECT * FROM User";
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));
                users.add(user);
            }
            System.out.println("Список User сформирован");
            users.toString();
        } catch (SQLException e) {
            System.out.println("Ошибка при создании списка User");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sqlCommand = "TRUNCATE TABLE User";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица User очищена");
        } catch (SQLException e) {
            System.out.println("Ошибка при очищении таблицы User");
        }
    }
}
