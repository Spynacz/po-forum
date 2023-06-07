package org.forum.services;

import org.forum.Post;
import org.forum.dao.PostDAO;

public class PostService {
    public PostService(final PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public void createPost(Post post) {
        postDAO.insert(post);
    }

    public void removePost(int postID) {
        postDAO.delete(postID);
    }

    public void editPost(Post post) {
        postDAO.update(post);
    }
    
    public void nullifyUser(int userID) {
        // userID of -1 means user deleted
        postDAO.getByUser(userID).forEach(post -> {
            post.setUserId(-1);
            postDAO.update(post);
        });
    }
    
    private final PostDAO postDAO;
}
