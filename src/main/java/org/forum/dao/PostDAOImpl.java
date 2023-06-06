package org.forum.dao;

import org.forum.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class PostDAOImpl implements PostDAO {
    @Override
    public Post getById(int id) {
        Post post = null;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetThreads = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE id = ?")) {
            psGetThreads.setInt(1, id);
            try (ResultSet rs = psGetThreads.executeQuery()) {
                if (!rs.isBeforeFirst())
                    post = new Post(rs.getInt("postID"), rs.getString("body"), rs.getLong("date"),
                            rs.getInt("threadID"), rs.getInt("userID"), rs.getInt("noInThread"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return post;
    }

    @Override
    public List<Post> getByUser(int userID) {
        List<Post> posts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetPosts =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE userID = ?)")) {
            psGetPosts.setInt(1, userID);
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
            System.out.println(e.getMessage());
        }
        return posts;
    }

    @Override
    public void insert(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psInsertPost =
                     conn.prepareStatement("INSERT INTO " + TABLE +
                             "(postID, body, date, threadID, userID, noInThread) VALUES(? ? ? ? ? ?)")) {
            psInsertPost.setInt(1, post.getId());
            psInsertPost.setString(2, post.getBody());
            psInsertPost.setLong(3, post.getTimestamp());
            psInsertPost.setInt(4, post.getThreadId());
            psInsertPost.setInt(5, post.getUserId());
            psInsertPost.setInt(6, post.getNoInThread());
            psInsertPost.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeletePost = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE postID = ?")) {
            psDeletePost.setInt(1, post.getId());
            psDeletePost.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdatePost = conn.prepareStatement("UPDATE " + TABLE + " SET body = ? WHERE postID = ?")) {
            psUpdatePost.setString(1, post.getBody());
            psUpdatePost.setInt(2, post.getId());
            psUpdatePost.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

