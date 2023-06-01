package org.forum.dao;

import org.forum.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class UserDAO {
    private static final String TABLE = "user";

    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement statement = conn.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE)) {
                while (rs.next())
                    allUsers.add(new User(rs.getString("username"), rs.getString("password")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allUsers;
    }

    public User getByUsername(String username) {
        User ret = null;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetUser = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE username = ?")) {
            psGetUser.setString(1, username);
            try (ResultSet rs = psGetUser.executeQuery()) {
                if (!rs.isBeforeFirst())
                    ret = new User(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public void create(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:forum.db");
             PreparedStatement psUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?")) {
            psUserExists.setString(1, user.getName());
            try (ResultSet rs = psUserExists.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    System.out.println("User already exists!");

                    // TODO: UI message

                } else {
                    try (PreparedStatement psInsertUser =
                                 connection.prepareStatement("INSERT INTO user(username, password) VALUES(?, ?)")) {
                        psInsertUser.setString(1, user.getName());
                        psInsertUser.setString(2, user.getPassword());
                        psInsertUser.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psRemoveUser = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE name = ?")) {
            psRemoveUser.setString(1, user.getName());
            psRemoveUser.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(User user, String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdateUser = conn.prepareStatement("UPDATE " + TABLE + " SET username = ?, password = ?")) {
            psUpdateUser.setString(1, user.getName());
            psUpdateUser.setString(1, user.getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
