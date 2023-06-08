package org.forum.dao;

import org.forum.User;

import java.sql.SQLDataException;
import java.util.List;

public interface UserDAO {
    String TABLE = "user";

    List<User> getAll();

    User getByUsername(String username);

    void insert(User user);

    void delete(int userID);

    void update(User user);
}
