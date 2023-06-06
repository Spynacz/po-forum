package org.forum.dao;

import org.forum.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class RankDAOImpl implements RankDAO {
    public List<String> getAll() {
        List<String> allRanks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetAll = conn.prepareStatement("SELECT name FROM " + TABLE)) {
            try (ResultSet rs = psGetAll.executeQuery()) {
                while (rs.next()) {
                    allRanks.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allRanks;
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
