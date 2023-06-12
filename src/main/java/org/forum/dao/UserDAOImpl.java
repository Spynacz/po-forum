package org.forum.dao;

import org.forum.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class UserDAOImpl implements UserDAO {
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetAll = conn.prepareStatement("SELECT * FROM " + TABLE)) {
            try (ResultSet rs = psGetAll.executeQuery()) {
                while (rs.next()) {
                    allUsers.add(new User(rs.getInt("userID"), rs.getString("username"), rs.getString("pass_hash"), rs.getString("salt"), rs.getInt("post_count")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    throw new SQLDataException("User doesn't exist");
                ret = new User(rs.getInt("userID"), rs.getString("username"), rs.getString("pass_hash"), rs.getString("salt"), rs.getInt("post_count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
    public User getById(int id) {
        User ret = null;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetUser = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE userID = ?")) {
            psGetUser.setString(1, String.valueOf(id));
            try (ResultSet rs = psGetUser.executeQuery()) {
                if (!rs.isBeforeFirst())
                    throw new SQLDataException("User doesn't exist");
                ret = new User(rs.getInt("userID"), rs.getString("username"), rs.getString("pass_hash"), rs.getString("salt"), rs.getInt("post_count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public void insert(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:forum.db");
             PreparedStatement psUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?")) {
            psUserExists.setString(1, user.getName());
            try (ResultSet rs = psUserExists.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    throw new SQLIntegrityConstraintViolationException("User already exists!");
                } else {
                    try (PreparedStatement psInsertUser =
                                 connection.prepareStatement("INSERT INTO user(username, pass_hash, salt, post_count) VALUES(?, ?, ?, ?)")) {
                        psInsertUser.setString(1, user.getName());
                        psInsertUser.setString(2, user.getPassHash());
                        psInsertUser.setString(3, user.getSalt());
                        psInsertUser.setInt(4, 0);
                        psInsertUser.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int userID) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psRemoveUser = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE userID = ?")) {
            psRemoveUser.setInt(1, userID);
            psRemoveUser.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdateUser =
                     conn.prepareStatement("UPDATE " + TABLE + " SET username = ?, pass_hash = ?, salt = ?, post_count = ? WHERE userID = ?")) {
            psUpdateUser.setString(1, user.getName());
            psUpdateUser.setString(2, user.getPassword());
            psUpdateUser.setInt(3, user.getPostCount());
            psUpdateUser.setInt(4, user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}