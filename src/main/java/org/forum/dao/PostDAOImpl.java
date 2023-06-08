package org.forum.dao;

import org.forum.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class PostDAOImpl implements PostDAO {
    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetPosts =
                     conn.prepareStatement("SELECT * FROM " + TABLE)) {
            try (ResultSet rs = psGetPosts.executeQuery()) {
                while (rs.next())
                    posts.add(new Post(rs.getInt("postID"), rs.getString("body"), rs.getInt("date"),
                            rs.getInt("threadID"), rs.getInt("userID"), rs.getInt("noInThread")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }
    @Override
    public Post getById(int id) {
        Post post = null;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE postID = ?")) {
            psGetThreads.setInt(1, id);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                if (rs.isBeforeFirst())
                    post = new Post(rs.getInt("postID"), rs.getString("body"), rs.getLong("date"),
                            rs.getInt("threadID"), rs.getInt("userID"), rs.getInt("noInThread"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public List<Post> getByUser(int userID) {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetPosts =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE userID = ?")) {
            psGetPosts.setInt(1, userID);
            try (ResultSet rs = psGetPosts.executeQuery()) {
                while (rs.next())
                    posts.add(new Post(rs.getInt("postID"), rs.getString("body"), rs.getInt("date"),
                            rs.getInt("threadID"), rs.getInt("userID"), rs.getInt("noInThread")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public List<Post> getByThread(int threadID) {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetPosts = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE threadID = ?")) {
            psGetPosts.setInt(1, threadID);
            try (ResultSet rs = psGetPosts.executeQuery()) {
                while (rs.next())
                    posts.add(new Post(rs.getInt("postID"), rs.getString("body"), rs.getInt("date"),
                            rs.getInt("threadID"), rs.getInt("userID"), rs.getInt("noInThread")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public void insert(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psInsertPost =
                     conn.prepareStatement("INSERT INTO " + TABLE + "(body, date, threadID, userID, noInThread) VALUES(?, ?, ?, ?, ?)")) {
            psInsertPost.setString(1, post.getBody());
            psInsertPost.setLong(2, post.getTimestamp());
            psInsertPost.setInt(3, post.getThreadId());
            psInsertPost.setInt(4, post.getUserId());
            psInsertPost.setInt(5, post.getNoInThread());
            psInsertPost.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int postID) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeletePost = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE postID = ?")) {
            psDeletePost.setInt(1, postID);
            psDeletePost.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdatePost = conn.prepareStatement("UPDATE " + TABLE + " SET userID = ?, body = ? WHERE postID = ?")) {
            psUpdatePost.setInt(1, post.getUserId());
            psUpdatePost.setString(2, post.getBody());
            psUpdatePost.setInt(3, post.getId());
            psUpdatePost.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

