package org.forum.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.forum.Main.DB_URL;

public class UserRankDAOImpl implements UserRankDAO {

    @Override
    public List<String> getByUser(int userID) {
        List<String> ranks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psGetRank =
                     conn.prepareStatement("SELECT rank FROM " + TABLE + " WHERE userID = ?")) {
            psGetRank.setInt(1, userID);
            try (ResultSet rs = psGetRank.executeQuery()) {
                while (rs.next())
                    ranks.add(rs.getString("rank"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ranks;
    }

    @Override
    public void insert(int userID, String rank) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psInsertRank =
                     conn.prepareStatement("INSERT INTO " + TABLE + "(userID, rank) VALUES(?, ?)")) {
            psInsertRank.setInt(1, userID);
            psInsertRank.setString(2, rank);
            psInsertRank.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int userID, String rank) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement psDeleteRank =
                     conn.prepareStatement("DELETE FROM " + TABLE + " WHERE userID = ? AND rank = ?")) {
            psDeleteRank.setInt(1, userID);
            psDeleteRank.setString(2, rank);
            psDeleteRank.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
