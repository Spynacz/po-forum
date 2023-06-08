package org.forum.dao;

import org.forum.ForumThread;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class ThreadDAOImpl implements ThreadDAO {
    @Override
    public List<ForumThread> getAll() {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE)) {
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID"), rs.getBoolean("closed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return threads;
    }
    @Override
    public ForumThread getById(int id) {
        ForumThread thread = null;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE threadID = ?")) {
            psGetThreads.setInt(1, id);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                if (rs.isBeforeFirst())
                    thread = new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID"), rs.getBoolean("closed"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return thread;
    }

    @Override
    public List<ForumThread> getByTitle(String title) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE title = ?")) {
            psGetThreads.setString(1, title);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID"), rs.getBoolean("closed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return threads;
    }

    @Override
    public List<ForumThread> getByDate(long timestamp) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE date(date) = date(?)")) {
            psGetThreads.setLong(1, timestamp);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID"), rs.getBoolean("closed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return threads;
    }

    @Override
    public List<ForumThread> getByUser(int userID) {
        List<ForumThread> threads = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE userID = ?")) {
            psGetThreads.setInt(1, userID);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                while (rs.next())
                    threads.add(new ForumThread(rs.getInt("threadID"), rs.getString("title"), rs.getLong("date"),
                            rs.getInt("userID"), rs.getBoolean("closed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return threads;
    }

    @Override
    public void insert(ForumThread thread) {
        if (thread.getTitle().isBlank())
            throw new RuntimeException("Thread must have a title");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psInsertThread =
                     conn.prepareStatement("INSERT INTO " + TABLE + "(title, date, userID, closed) VALUES(?, ?, ?, ?)")) {
            psInsertThread.setString(1, thread.getTitle());
            psInsertThread.setLong(2, thread.getTimestamp());
            psInsertThread.setInt(3, thread.getUserId());
            psInsertThread.setBoolean(4, thread.isClosed());
            psInsertThread.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int threadID) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeleteThread = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE threadID = ?")) {
            psDeleteThread.setInt(1, threadID);
            psDeleteThread.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ForumThread thread) {
        if (thread.getTitle().isBlank())
            throw new RuntimeException("Thread must have a title");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdateThread = conn.prepareStatement("UPDATE " + TABLE + " SET title = ?, userID = ?, closed = ? WHERE threadID = ?")) {
            psUpdateThread.setString(1, thread.getTitle());
            psUpdateThread.setInt(2, thread.getUserId());
            psUpdateThread.setBoolean(3, thread.isClosed());
            psUpdateThread.setInt(4, thread.getId());
            psUpdateThread.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
