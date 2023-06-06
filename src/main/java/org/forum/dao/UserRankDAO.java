package org.forum.dao;

import java.util.List;

public interface UserRankDAO {
    String TABLE = "user_rank";

    List<String> getByUser(int userID);

    void insert(int userID, String rank);

    void delete(int userID, String rank);
}
