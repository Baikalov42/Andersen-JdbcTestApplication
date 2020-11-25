package com.andersenlab.jdbctest.dao;

import com.andersenlab.jdbctest.exceptions.DaoException;
import com.andersenlab.jdbctest.connection.DBConnection;
import com.andersenlab.jdbctest.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {
    private DBConnection dbConnection;

    public RoleDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public long insert(Role role) {
        String query = "" +
                "INSERT INTO roles (role_name, role_description) " +
                "VALUES ( ? , ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Inserting role failed");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong("role_id");
            } else {
                throw new DaoException("Inserting role failed, no ID obtained.");
            }

        } catch (SQLException ex) {
            throw new DaoException("Failed to insert", ex);
        }
    }

    public Role getById(long roleId) {
        String query = "" +
                "SELECT * FROM roles " +
                "WHERE role_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, roleId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Role role = new Role();

                role.setId(resultSet.getLong("role_id"));
                role.setName(resultSet.getString("role_name"));
                role.setDescription(resultSet.getString("role_description"));
                return role;

            } else {
                throw new DaoException("role id not found");
            }
        } catch (SQLException ex) {
            throw new DaoException("Failed to get , id = " + roleId, ex);
        }
    }

    public List<Role> getAll() {
        String query = "SELECT * FROM roles";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new DaoException("Roles not found");
            }

            List<Role> result = new ArrayList<>();
            while (resultSet.next()) {
                Role role = new Role();

                role.setId(resultSet.getLong("role_id"));
                role.setName(resultSet.getString("role_name"));
                role.setDescription(resultSet.getString("role_description"));
                result.add(role);
            }
            return result;

        } catch (SQLException ex) {
            throw new DaoException("getAll not completed", ex);
        }
    }

    public List<Role> getRolesByUserId(long userId) {
        String query = "" +
                "SELECT roles.role_id, roles.role_name, roles.role_description " +
                "FROM users_roles " +
                "INNER JOIN roles " +
                "ON roles.role_id = users_roles.role_id " +
                "WHERE user_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new DaoException("roles not found, user id= " + userId);
            }

            List<Role> result = new ArrayList<>();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setName(resultSet.getString("role_name"));
                role.setDescription(resultSet.getString("role_description"));

                result.add(role);
            }
            return result;

        } catch (SQLException ex) {
            throw new DaoException("getRolesByUserId not completed, user id = " + userId, ex);
        }
    }


    public void update(Role role) {
        String query = "" +
                "UPDATE roles " +
                "SET role_name = ? , role_description = ? " +
                "WHERE role_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.setLong(3, role.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("nothing updated");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed update role: " + role, e);
        }
    }

    public void deleteById(long roleId) {
        String query = "" +
                "DELETE FROM roles " +
                "WHERE role_id = ? ";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, roleId);
            int row = statement.executeUpdate();
            if (row == 0) {
                throw new DaoException("Role not deleted, id = " + roleId);
            }

        } catch (SQLException ex) {
            throw new DaoException("failed delete role, id = " + roleId, ex);
        }
    }
}
