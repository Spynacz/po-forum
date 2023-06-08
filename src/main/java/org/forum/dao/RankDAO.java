package org.forum.dao;

import java.util.List;

public interface RankDAO {
    String TABLE = "rank";

    List<String> getAll();

    void insert(String rank);

    void delete(String rank);

    void update(String name, String newName);
}
