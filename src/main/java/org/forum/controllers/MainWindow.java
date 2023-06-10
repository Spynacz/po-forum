package org.forum.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.forum.User;
import org.forum.dao.*;

public class MainWindow{
    private User user;
    private UserDAO usersTable;
    private ThreadDAO threadsTable;

    private RankDAO rankDAO;
    @FXML
    private VBox ThreadsContainer;

    @FXML
    private Label login;

    @FXML
    private TextField searchField;
    public MainWindow()
    {
        user = null;
        usersTable = null;
        threadsTable = new ThreadDAOImpl();
        rankDAO = new RankDAOImpl();
    }
    @FXML
    void KeyPressedCheckIfEnterPressed(KeyEvent event) {

    }

    @FXML
    void SearchFor(MouseEvent event) {

    }
    public void initilizeController(User user, UserDAO usersTable)
    {
        this.user = user;
        this.usersTable = usersTable;
        login.setText(user.getName());
    }

}
