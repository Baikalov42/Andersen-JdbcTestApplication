package com.andersenlab.jdbctest;

import com.andersenlab.jdbctest.connection.DBConnection;
import com.andersenlab.jdbctest.connection.PostgresConnection;
import com.andersenlab.jdbctest.dao.RoleDao;
import com.andersenlab.jdbctest.dao.UserDao;
import com.andersenlab.jdbctest.model.Role;
import com.andersenlab.jdbctest.model.User;

public class Main {
    public static void main(String[] args) {

        DBConnection postgresConnection = new PostgresConnection();
        UserDao userDao = new UserDao(postgresConnection);
        RoleDao roleDao = new RoleDao(postgresConnection);

        System.out.println("get user by id = 11");
        System.out.println(userDao.getById(11));
        System.out.println("----------------------------");

        System.out.println("get role by id = 11");
        System.out.println(roleDao.getById(11));
        System.out.println("----------------------------");

        System.out.println("insert user");
        System.out.println(userDao.insert(new User("Germiona", "Grainjer")));
        System.out.println("----------------------------");

        System.out.println("insert role");
        System.out.println(roleDao.insert(new Role("Woman", "feminist")));
        System.out.println("----------------------------");

        System.out.println("get all users");
        System.out.println(userDao.getAll());
        System.out.println("----------------------------");

        System.out.println("get all roles");
        System.out.println(roleDao.getAll());
        System.out.println("----------------------------");

        System.out.println("get all roles by user Albus");
        System.out.println(roleDao.getRolesByUserId(11));
        System.out.println("----------------------------");

        System.out.println("get all users by role student");
        System.out.println(userDao.getUsersByRoleId(11));
        System.out.println("----------------------------");

        System.out.println("update Harry Potter- > HARRY POTTER");
        User user = new User("HARRY", "POTTER");
        user.setId(14);
        userDao.update(user);
        System.out.println("----------------------------");

        System.out.println("get all users");
        System.out.println(userDao.getAll());
        System.out.println("----------------------------");

        System.out.println("update student student from h- > STUDENT DURMSTRANG");
        Role role = new Role("STUDENT", "DURMSTRANG");
        role.setId(11);
        roleDao.update(role);
        System.out.println("----------------------------");

        System.out.println("get all roles");
        System.out.println(roleDao.getAll());
        System.out.println("----------------------------");

        System.out.println("Add role to Albus -> dark lord");
        userDao.addRoleToUser(11,14);
        System.out.println("----------------------------");

        System.out.println("get all roles by user Albus");
        System.out.println(roleDao.getRolesByUserId(11));
        System.out.println("----------------------------");

        System.out.println("delete role from Albus -> dark lord");
        userDao.removeRoleFromUser(11,14);
        System.out.println("----------------------------");

        System.out.println("get all roles by user Albus");
        System.out.println(roleDao.getRolesByUserId(11));
        System.out.println("----------------------------");

        System.out.println("delete user by id 1");
        userDao.deleteById(1);
        System.out.println("----------------------------");

        System.out.println("get all users");
        System.out.println(userDao.getAll());
        System.out.println("----------------------------");

        System.out.println("delete role by id 1");
        roleDao.deleteById(1);
        System.out.println("----------------------------");

        System.out.println("get all roles");
        System.out.println(roleDao.getAll());
        System.out.println("----------------------------");
    }
}
