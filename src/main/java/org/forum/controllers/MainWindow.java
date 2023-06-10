package org.forum.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.forum.User;
import org.forum.dao.ThreadDAO;
import org.forum.dao.ThreadDAOImpl;

public class MainWindow{

    @FXML
    private VBox ThreadsContainer;

    @FXML
    private Label login;

    @FXML
    private TextField searchField;

    @FXML
    void KeyPressedCheckIfEnterPressed(KeyEvent event) {

    }

    @FXML
    void SearchFor(MouseEvent event) {

    }
    public void initilizeController(User user)
    {
        //TODO: implementation
    }

}
