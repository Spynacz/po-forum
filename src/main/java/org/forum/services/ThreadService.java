package org.forum.services;

import org.forum.ForumThread;
import org.forum.dao.PostDAO;
import org.forum.dao.ThreadDAO;

public class ThreadService {
    public ThreadService(ThreadDAO threadDAO, PostDAO postDAO) {
        this.threadDAO = threadDAO;
        this.postDAO = postDAO;
    }

    public void createThread(ForumThread thread) {
        threadDAO.insert(thread);
    }

    public void removeThread(int threadID) {
        postDAO.getByThread(threadID).forEach(post -> postDAO.delete(post.getId()));
        threadDAO.delete(threadID);
    }

    public void editTitle(ForumThread thread) {
        threadDAO.update(thread);
    }

    public void nullifyUser(int userID) {
        // userID of -1 means user deleted
        threadDAO.getByUser(userID).forEach(thread -> {
            thread.setUserId(-1);
            threadDAO.update(thread);
        });
    }

    public void closeThread(int threadID) {
        ForumThread thread = threadDAO.getById(threadID);
        thread.setClosed(true);
        threadDAO.update(thread);
    }

    private final ThreadDAO threadDAO;
    private final PostDAO postDAO;
}
