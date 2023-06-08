package org.forum.services;

import org.forum.ForumThread;
import org.forum.Post;
import org.forum.User;
import org.forum.dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    UserDAO ud = new UserDAOImpl();
    ThreadDAO td = new ThreadDAOImpl();
    PostDAO pd = new PostDAOImpl();
    PostService ps = new PostService(pd, td);

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
    void createPost() {
        Post newPost = new Post("New post", ud.getByUsername("user1").getId(), td.getByTitle("3. Closed thread").get(0).getId());

        assertThrows(RuntimeException.class, () -> ps.createPost(newPost));

        newPost.setThreadId(td.getByTitle("1. Open thread").get(0).getId());
        ps.createPost(newPost);
        assertEquals(3, ps.countPostsInThread(td.getByTitle("1. Open thread").get(0).getId()));

        newPost.setThreadId(td.getByTitle("2. Open thread zero posts").get(0).getId());
        ps.createPost(newPost);
        assertEquals(1, ps.countPostsInThread(td.getByTitle("2. Open thread zero posts").get(0).getId()));
    }

    @Test
    void removePost() {
        int postId1 = pd.getByThread(td.getByTitle("3. Closed thread").get(0).getId()).get(0).getId();
        assertThrows(RuntimeException.class, () -> ps.removePost(postId1));

        int postId2 = pd.getByThread(td.getByTitle("1. Open thread").get(0).getId()).get(0).getId();
        ps.removePost(postId2);
        assertEquals(1, ps.countPostsInThread(td.getByTitle("1. Open thread").get(0).getId()));

        int postId3 = 69420;
        assertThrows(RuntimeException.class, () -> ps.removePost(postId3));
    }

    @Test
    void editPost() {
        Post editedPost1 = pd.getByThread(td.getByTitle("3. Closed thread").get(0).getId()).get(0);
        editedPost1.setBody("Edited body");

        assertThrows(RuntimeException.class, () -> ps.editPost(editedPost1));

        Post editedPost2 = pd.getByThread(td.getByTitle("1. Open thread").get(0).getId()).get(0);
        editedPost2.setBody("Edited body");
        ps.editPost(editedPost2);
        assertEquals("Edited body", pd.getById(editedPost2.getId()).getBody());

        Post editedPost3 = new Post();
        editedPost3.setId(1337);
        assertThrows(RuntimeException.class, () -> ps.editPost(editedPost3));
    }

    @Test
    void nullifyUser() {
        int postId1 = pd.getByThread(td.getByTitle("1. Open thread").get(0).getId()).get(0).getId();
        ps.nullifyUser(ud.getByUsername("user1").getId());
        assertEquals(-1, pd.getById(postId1).getUserId());
    }
}