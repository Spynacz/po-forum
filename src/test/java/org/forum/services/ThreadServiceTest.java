package org.forum.services;

import org.forum.ForumThread;
import org.forum.Post;
import org.forum.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.forum.dao.*;

import static org.junit.jupiter.api.Assertions.*;

class ThreadServiceTest {

    UserDAO ud = new UserDAOImpl();
    ThreadDAO td = new ThreadDAOImpl();
    PostDAO pd = new PostDAOImpl();
    ThreadService ts = new ThreadService(td, pd);

    @BeforeEach
    void setUp() {
        User user1 = new User("user1", "pass");
        ud.insert(user1);

        ForumThread thread1 = new ForumThread("1. Open thread", ud.getByUsername("user1").getId());
        ForumThread thread2 = new ForumThread("2. Open thread zero posts", ud.getByUsername("user1").getId());
        ForumThread thread3 = new ForumThread("3. Closed thread", ud.getByUsername("user1").getId());
        thread3.setClosed(true);
        td.insert(thread1);
        td.insert(thread2);
        td.insert(thread3);

        Post post1 = new Post("Post in thread 1", ud.getByUsername("user1").getId(), td.getByTitle("1. Open thread").get(0).getId());
        Post post2 = new Post("Post in thread 1", ud.getByUsername("user1").getId(), td.getByTitle("1. Open thread").get(0).getId());
        Post post3 = new Post("Post in thread 3", ud.getByUsername("user1").getId(), td.getByTitle("3. Closed thread").get(0).getId());
        Post post4 = new Post("Post in thread 3", ud.getByUsername("user1").getId(), td.getByTitle("3. Closed thread").get(0).getId());
        pd.insert(post1);
        pd.insert(post2);
        pd.insert(post3);
        pd.insert(post4);
    }

    @AfterEach
    void tearDown() {
        ud.getAll().forEach(user -> {
            ud.delete(user.getId());
        });

        td.getAll().forEach(thread -> td.delete(thread.getId()));
        pd.getAll().forEach(post -> pd.delete(post.getId()));
    }

    @Test
    void createThread() {
        ts.createThread(new ForumThread("New thread", ud.getByUsername("user1").getId()));
        assertEquals(4, td.getAll().size());
    }

    @Test
    void removeThread() {
        ts.removeThread(td.getByTitle("1. Open thread").get(0).getId());
        assertEquals(2, td.getAll().size());
    }

    @Test
    void editTitle() {
        ForumThread editedThread = td.getById(1);
        editedThread.setTitle("New title");
        ts.editTitle(editedThread);
        assertEquals("New title", td.getById(1).getTitle());

        editedThread.setTitle("");
        assertThrows(RuntimeException.class, () -> ts.editTitle(editedThread));
    }

    @Test
    void nullifyUser() {
        ts.nullifyUser(ud.getByUsername("user1").getId());
        assertEquals(-1, td.getByTitle("1. Open thread").get(0).getUserId());
        assertEquals(-1, td.getByTitle("2. Open thread zero posts").get(0).getUserId());
        assertEquals(-1, td.getByTitle("3. Closed thread").get(0).getUserId());
    }

    @Test
    void closeThread() {
        ts.closeThread(1);
        assertTrue(td.getById(1).isClosed());
        assertFalse(td.getById(2).isClosed());
    }
}