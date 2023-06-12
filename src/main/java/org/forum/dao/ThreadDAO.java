package org.forum.dao;

import org.forum.ForumThread;
import org.forum.User;

import java.util.List;


public interface ThreadDAO {
    String TABLE = "thread";

    List<ForumThread> getAll();
    ForumThread getById(int id);
    List<ForumThread> getByTitle(String title);

    List<ForumThread> getByUser(int userID);

    void insert(ForumThread thread);

    void delete(int threadID);

    void update(ForumThread thread);
}
