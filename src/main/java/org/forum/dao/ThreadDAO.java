package org.forum.dao;

import org.forum.ForumThread;
import org.forum.User;

import java.util.List;


public interface ThreadDAO {
    String TABLE = "thread";

    ForumThread getById(int id);

    List<ForumThread> getByTitle(String title);

    List<ForumThread> getByDate(long timestamp);

    List<ForumThread> getByUser(User user);

    void insert(ForumThread thread);

    void delete(int threadID);
}
