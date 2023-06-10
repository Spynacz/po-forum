package org.forum.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.forum.User;
import org.forum.dao.*;

import java.util.List;

public class MainWindow{
    private User user;
    private UserDAO usersTable;
    private ThreadDAO threadsTable;

    private UserRankDAO rankDAO;
    @FXML
    private VBox ThreadsContainer;

    @FXML
    private Label login;
    @FXML
    private Label ranKfield;

    @FXML
    private TextField searchField;
    public MainWindow()
    {
        user = null;
        usersTable = null;
        threadsTable = new ThreadDAOImpl();
        rankDAO = new UserRankDAOImpl();
    }
    @FXML
    void addPost(MouseEvent event) {

    }

    @FXML
    void addThread(ActionEvent event) {

    }

    @FXML
    void keyPressedCheckIfEnterPressed(KeyEvent event) {

    }

    @FXML
    void searchFor(MouseEvent event) {

    }
    public void initilizeController(User user, UserDAO usersTable)
    {
        this.user = user;
        this.usersTable = usersTable;
        login.setText(user.getName());

        try {
            List<String> ranks = rankDAO.getByUser(user.getId());
            String val  = "";
            for(String rank : ranks)
            {
                val += rank;
            }
            ranKfield.setText(val);
        }
        catch (RuntimeException e)
        {
            ranKfield.setText("");
        }

    }

}
