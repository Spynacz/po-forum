package org.forum.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.forum.*;
import org.forum.dao.*;
import org.forum.services.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserDAO ud = new UserDAOImpl();
    UserRankDAO urd = new UserRankDAOImpl();
    RankDAO rd = new RankDAOImpl();
    PostDAO pd = new PostDAOImpl();
    UserService us = new UserService(ud, rd, urd, pd);

    @BeforeEach
    void setUp() {
        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        User user3 = new User("user3", "pass");
        User user4 = new User("user4", "pass");
        ud.insert(user1);
        ud.insert(user2);
        ud.insert(user3);
        ud.insert(user4);

        rd.insert("Administrator");
        rd.insert("New user");
        rd.insert("Old user");

        urd.insert(ud.getByUsername("user1").getId(), "Administrator");
        urd.insert(ud.getByUsername("user2").getId(), "New user");
        urd.insert(ud.getByUsername("user3").getId(), "New user");
        urd.insert(ud.getByUsername("user4").getId(), "New user");
    }

    @AfterEach
    void tearDown() {
        ud.getAll().forEach(user -> {
            urd.getByUser(user.getId()).forEach(rank -> urd.delete(user.getId(), rank));
            ud.delete(user.getId());
        });

        rd.getAll().forEach(rank -> rd.delete(rank));
        pd.getAll().forEach(post -> pd.delete(post.getId()));
    }

    @Test
    void addNewUser() {

        User newUser = new User("user1", "pass");
        assertThrows(RuntimeException.class, () -> us.addUser(newUser));

        newUser.setName("user5");
        us.addUser(newUser);
        assertEquals(5, ud.getAll().size());
    }

    @Test
    void removeUser() {
        int id = ud.getByUsername("user1").getId();

        us.removeUser("user1");
        assertEquals(3, ud.getAll().size());
        assertEquals(0, urd.getByUser(id).size());

        assertThrows(RuntimeException.class,() -> us.removeUser("user69"));
    }

    @Test
    void addRank() {
        assertThrows(RuntimeException.class, () -> us.addRank("user1", "nonExistentRank"));

        assertThrows(RuntimeException.class, () -> us.addRank("user1", "Administrator"));

        us.addRank("user1", "Old user");
        assertEquals(2, urd.getByUser(ud.getByUsername("user1").getId()).size());
    }

    @Test
    void removeRank() {
        assertThrows(RuntimeException.class, () -> us.removeRank("user1", "Old user"));

        us.removeRank("user2", "New user");
        assertEquals(0, urd.getByUser(ud.getByUsername("user2").getId()).size());
    }

    @Test
    void updateUserPostCount() {
        assertThrows(RuntimeException.class, () -> us.updateUserPostCount("nonExistentUser"));

        assertEquals(0, pd.getByUser(ud.getByUsername("user1").getId()).size());

        Post post = new Post("Test post", ud.getByUsername("user1").getId(), 0);
        pd.insert(post);
        assertEquals(1, pd.getByUser(ud.getByUsername("user1").getId()).size());
    }

    @Test
    void getAllUsers() {
        assertEquals(4, us.getAllUsers().size());
    }
}