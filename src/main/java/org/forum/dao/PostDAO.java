package org.forum.dao;

import org.forum.Post;

import java.util.List;

public interface PostDAO {
    String TABLE = "post";

    Post getById(int id);

    List<Post> getByUser(int userID);

    List<Post> getByThread(int threadID);

    void insert(Post post);

    void delete(Post post);

    void update(Post post);
}
