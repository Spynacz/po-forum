package org.forum.dao;

import javafx.scene.control.FocusModel;
import org.forum.ForumThread;
import org.forum.Post;
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
                    allUsers.add(new User(rs.getInt("userID"), rs.getString("username"), rs.getString("password")));
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
                if (rs.isBeforeFirst())
                    ret = new User(rs.getInt("userID"), rs.getString("username"), rs.getString("password"));
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

    public void remove(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psRemoveUser = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE username = ?")) {
            psRemoveUser.setString(1, username);
            psRemoveUser.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(User user, String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdateUser =
                     conn.prepareStatement("UPDATE " + TABLE + " SET username = ?, password = ? WHERE userID = ?")) {
            psUpdateUser.setString(1, user.getName());
            psUpdateUser.setString(2, user.getPassword());
            psUpdateUser.setInt(3, user.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Post> getPosts(String username) {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetPosts =
                     conn.prepareStatement("SELECT * FROM post WHERE userID = (SELECT userID FROM user WHERE username = ?)")) {
            psGetPosts.setString(1, username);
            try (ResultSet rs = psGetPosts.executeQuery()) {
                while (rs.next())
                    posts.add(new Post(rs.getInt("postID"), rs.getString("body"), rs.getInt("date"),
                            rs.getInt("threadID"), rs.getInt("userID"), rs.getInt("noInThread")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }

    public List<ForumThread> getThreads(String username) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads =
                     conn.prepareStatement("SELECT * FROM thread WHERE userID = (SELECT userID FROM user WHERE username = ?)")) {
            psGetThreads.setString(1, username);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getInt("date"),
                            rs.getInt("userID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return threads;
    }
}
