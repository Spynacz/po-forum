package org.forum.dao;

import org.forum.ForumThread;
import org.forum.Post;

import java.sql.*;

import static org.forum.Main.DB_URL;

public class PostDAO {
    private static final String TABLE = "post";

    public void create(Post post) {
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

    public void delete(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeletePost = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE postID = ?")) {
            psDeletePost.setInt(1, post.getId());
            psDeletePost.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void edit(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psEditPost = conn.prepareStatement("UPDATE " + TABLE + " SET body = ? WHERE postID = ?")) {
            psEditPost.setString(1, post.getBody());
            psEditPost.setInt(2, post.getId());
            psEditPost.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public void nullifyUser(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psNullUser =
                     conn.prepareStatement("UPDATE " + TABLE + " SET userID = NULL WHERE userID =" +
                             "(SELECT userID FROM user WHERE username = ?)")) {
            psNullUser.setString(1, username);
            psNullUser.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
