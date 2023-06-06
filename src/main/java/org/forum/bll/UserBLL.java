package org.forum.bll;

import org.forum.User;
import org.forum.dao.RankDAO;
import org.forum.dao.UserDAO;
import org.forum.dao.UserRankDAO;

import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserBLL {
    public UserBLL(final UserDAO userDAO, final RankDAO rankDAO, UserRankDAO userRankDAO) {
        this.userDAO = userDAO;
        this.rankDAO = rankDAO;
        this.userRankDAO = userRankDAO;
    }

    public void addUser(User user) {
        userDAO.insert(user);
    }

    public void addRank(String username, String rank) throws SQLDataException, SQLIntegrityConstraintViolationException {
        if (!rankDAO.getAll().contains(rank))
            throw new SQLDataException("Rank does not exist");

        User user = userDAO.getByUsername(username);
        List<String> ranks = userRankDAO.getByUser(user.getId());
        if (ranks.contains(rank))
            throw new SQLIntegrityConstraintViolationException("User already has this rank");

        ranks.add(rank);
        user.setRanks(ranks);
        userDAO.update(user);
        userRankDAO.insert(user.getId(), rank);
    }

    public void removeRank(String username, String rank) throws SQLDataException {
        User user = userDAO.getByUsername(username);
        List<String> ranks = userRankDAO.getByUser(user.getId());
        if (!ranks.contains(rank))
            throw new SQLDataException("User does not have this rank");

        ranks.remove(rank);
        user.setRanks(ranks);
        userDAO.update(user);
        userRankDAO.delete(user.getId(), rank);
    }

    public List<User> getAllUsers() {
        List<User> users = userDAO.getAll();
        users.forEach(user -> user.setRanks(userRankDAO.getByUser(user.getId())));
        return users;
    }

    private final UserDAO userDAO;
    private final RankDAO rankDAO;
    private final UserRankDAO userRankDAO;
}
