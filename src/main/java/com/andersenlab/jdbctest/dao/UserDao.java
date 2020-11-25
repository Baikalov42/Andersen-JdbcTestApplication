package com.andersenlab.jdbctest.dao;

import com.andersenlab.jdbctest.exceptions.DaoException;
import com.andersenlab.jdbctest.connection.DBConnection;
import com.andersenlab.jdbctest.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private DBConnection dbConnection;

    public UserDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public long insert(User user) {
        String query = "" +
                "INSERT INTO users (user_first_name, user_last_name) " +
                "VALUES ( ? , ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DaoException("Inserting user failed");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong("user_id");
            } else {
                throw new DaoException("Inserting user failed, no ID obtained.");
            }

        } catch (SQLException ex) {
            throw new DaoException("Failed to insert", ex);
        }
    }

    public User getById(long userId) {
        String query = "" +
                "SELECT * FROM users " +
                "WHERE user_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("user_id"));
                user.setFirstName(resultSet.getString("user_first_name"));
                user.setLastName(resultSet.getString("user_last_name"));
                return user;

            } else {
                throw new DaoException("user id not found");
            }
        } catch (SQLException ex) {
            throw new DaoException("Failed to get , id = " + userId, ex);
        }
    }

    public List<User> getAll() {
        String query = "SELECT * FROM users";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new DaoException("Users not found");
            }

            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("user_id"));
                user.setFirstName(resultSet.getString("user_first_name"));
                user.setLastName(resultSet.getString("user_last_name"));

                result.add(user);
            }
            return result;

        } catch (SQLException ex) {
            throw new DaoException("getAll not completed", ex);
        }
    }

    public List<User> getUsersByRoleId(long roleId) {
        String query = "" +
                "SELECT users.user_id, users.user_first_name, users.user_last_name " +
                "FROM users_roles " +
                "INNER JOIN users " +
                "ON users.user_id = users_roles.user_id " +
                "WHERE role_id= ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, roleId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new DaoException("users not found, role id= " + roleId);
            }

            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("user_id"));
                user.setFirstName(resultSet.getString("user_first_name"));
                user.setLastName(resultSet.getString("user_last_name"));

                result.add(user);
            }
            return result;

        } catch (SQLException ex) {
            throw new DaoException("getUsersByRoleId not completed, role id = " + roleId, ex);
        }
    }


    public void update(User user) {
        String query = "" +
                "UPDATE users " +
                "SET user_first_name = ? , user_last_name = ? " +
                "WHERE user_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setLong(3, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("nothing updated");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed update user: " + user, e);
        }
    }

    public void addRoleToUser(long userId, long roleId) {
        String query = "" +
                "INSERT INTO users_roles (user_id, role_id) " +
                "VALUES (?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.execute();

        } catch (SQLException ex) {
            throw new DaoException("Illegal parameters, user or role not exist", ex);
        }
    }

    public void removeRoleFromUser(long userId, long roleId) {
        String query = "" +
                "DELETE FROM users_roles " +
                "WHERE user_id = ? " +
                "AND role_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            int row = statement.executeUpdate();

            if (row == 0) {
                throw new DaoException("" +
                        "incorrect input, user =" + userId + " or role = " + roleId + " not exist");
            }
        } catch (SQLException ex) {
            throw new DaoException("failed to delete role from user", ex);
        }
    }

    public void deleteById(long userId) {
        String query = "" +
                "DELETE FROM users " +
                "WHERE user_id = ? ";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            int row = statement.executeUpdate();
            if (row == 0) {
                throw new DaoException("User not deleted, id = " + userId);
            }

        } catch (SQLException ex) {
            throw new DaoException("failed delete user, id = " + userId, ex);
        }
    }
}
