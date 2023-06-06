package org.forum.dao;

import org.forum.ForumThread;
import org.forum.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class ThreadDAO {
    private static final String TABLE = "thread";

    public ForumThread getById(int id) {
        ForumThread thread = null;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE id = ?")) {
            psGetThreads.setInt(1, id);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                if (!rs.isBeforeFirst())
                    thread = new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return thread;
    }

    public List<ForumThread> getByTitle(String title) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE title = ?")) {
            psGetThreads.setString(1, title);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return threads;
    }

    public List<ForumThread> getByDate(long timestamp) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE date(date) = date(?)")) {
            psGetThreads.setLong(1, timestamp);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return threads;
    }

    public List<ForumThread> getByUser(User user) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE username = ?")) {
            psGetThreads.setString(1, user.getName());
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return threads;
    }

    public void insert(ForumThread thread) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psInsertThread =
                     conn.prepareStatement("INSERT INTO " + TABLE + "(title, date, userID) VALUES(?, ?, ?)")) {
            psInsertThread.setInt(1, thread.getId());
            psInsertThread.setString(2, thread.getTitle());
            psInsertThread.setLong(3, thread.getTimestamp());
            psInsertThread.setInt(4, thread.getUserID());
            psInsertThread.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int threadID) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeleteThread = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE threadID = ?")) {
            psDeleteThread.setInt(1, threadID);
            psDeleteThread.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void nullifyUser(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psNullUser = conn.prepareStatement("UPDATE " + TABLE + " SET userID = NULL WHERE userID =" +
                     "(SELECT userID FROM user WHERE username = ?")) {
            psNullUser.setString(1, username);
            psNullUser.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
