package org.forum.dao;

import org.forum.Post;

import java.util.List;

public interface PostDAO {
    String TABLE = "post";

    List<Post> getAll();

    Post getById(int id);

    List<Post> getByUser(int userID);

    List<Post> getByThread(int threadID);

    void insert(Post post);

    void delete(int postID);

    void update(Post post);
}
