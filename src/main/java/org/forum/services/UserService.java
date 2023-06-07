package org.forum.services;

import org.forum.User;
import org.forum.dao.PostDAO;
import org.forum.dao.RankDAO;
import org.forum.dao.UserDAO;
import org.forum.dao.UserRankDAO;

import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserService {
    public UserService(final UserDAO userDAO, final RankDAO rankDAO, UserRankDAO userRankDAO, PostDAO postDAO) {
        this.userDAO = userDAO;
        this.rankDAO = rankDAO;
        this.userRankDAO = userRankDAO;
        this.postDAO = postDAO;
    }

    public void addUser(User user) {
        userDAO.insert(user);
    }

    public void removeUser(String username) {
        int id = userDAO.getByUsername(username).getId();

        // delete user
        userDAO.delete(id);

        // delete all user's ranks
        userRankDAO.getByUser(id).forEach(r -> userRankDAO.delete(id, r));
    }

    public void addRank(String username, String rank) {
        if (!rankDAO.getAll().contains(rank))
            throw new RuntimeException("Rank does not exist");

        User user = userDAO.getByUsername(username);
        List<String> ranks = userRankDAO.getByUser(user.getId());
        if (ranks.contains(rank))
            throw new RuntimeException("User already has this rank");

        ranks.add(rank);
        user.setRanks(ranks);
        userDAO.update(user);
        userRankDAO.insert(user.getId(), rank);
    }

    public void removeRank(String username, String rank) {
        User user = userDAO.getByUsername(username);
        List<String> ranks = userRankDAO.getByUser(user.getId());
        if (!ranks.contains(rank))
            throw new RuntimeException("User does not have this rank");

        ranks.remove(rank);
        user.setRanks(ranks);
        userDAO.update(user);
        userRankDAO.delete(user.getId(), rank);
    }

    public void updateUserPostCount(String username) {
        User user = userDAO.getByUsername(username);
        user.setPostCount(postDAO.getByUser(user.getId()).size());
        userDAO.update(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userDAO.getAll();
        users.forEach(user -> user.setRanks(userRankDAO.getByUser(user.getId())));
        return users;
    }

    private final UserDAO userDAO;
    private final RankDAO rankDAO;
    private final UserRankDAO userRankDAO;
    private final PostDAO postDAO;
}
