package org.forum.dao;

import org.forum.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class RankDAO {
    private static final String TABLE = "rank";

    public List<String> getByUser(int userID) {
        List<String> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetRank =
                     conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE userID = ?)")) {
            psGetRank.setInt(1, userID);
            try (ResultSet rs = psGetRank.executeQuery()) {
                while (rs.next())
                    users.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void insert(String rank) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psInsertRank =
                     conn.prepareStatement("INSERT INTO " + TABLE + "(name) VALUES(?)")) {
            psInsertRank.setString(1, rank);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String rank) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeleteRank = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE name = ?")) {
            psDeleteRank.setString(1, rank);
            psDeleteRank.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(String name, String newName) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psUpdateRank = conn.prepareStatement("UPDATE " + TABLE + " SET name = ? WHERE name = ?")) {
            psUpdateRank.setString(1, newName);
            psUpdateRank.setString(2, name);
            psUpdateRank.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
