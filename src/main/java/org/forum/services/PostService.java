package org.forum.services;

import org.forum.Post;
import org.forum.dao.PostDAO;
import org.forum.dao.ThreadDAO;

public class PostService {
    public PostService(final PostDAO postDAO, ThreadDAO threadDAO) {
        this.postDAO = postDAO;
        this.threadDAO = threadDAO;
    }

    public void createPost(Post post) {
        if (threadDAO.getById(post.getThreadId()).isClosed())
            throw new RuntimeException("This thread is locked. Cannot add new posts");

        post.setNoInThread(countPostsInThread(post.getThreadId()) + 1);
        postDAO.insert(post);
    }

    public void removePost(int postID) {
        if (threadDAO.getById(postDAO.getById(postID).getThreadId()).isClosed())
            throw new RuntimeException("This thread is locked. Cannot remove posts");

        postDAO.delete(postID);
    }

    public void editPost(Post post) {
        if (threadDAO.getById(post.getThreadId()).isClosed())
            throw new RuntimeException("This thread is locked. Cannot edit posts");

        postDAO.update(post);
    }
    
    public void nullifyUser(int userID) {
        // userID of -1 means user deleted
        postDAO.getByUser(userID).forEach(post -> {
            post.setUserId(-1);
            postDAO.update(post);
        });
    }

    public int countPostsInThread(int threadID) {
        return postDAO.getByThread(threadID).size();
    }
    
    private final PostDAO postDAO;
    private final ThreadDAO threadDAO;
}
