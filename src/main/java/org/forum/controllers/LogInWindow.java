package org.forum.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.forum.Main;
import org.forum.User;
import org.forum.dao.ThreadDAO;
import org.forum.dao.UserDAO;
import org.forum.dao.UserDAOImpl;

import java.io.IOException;

public class LogInWindow {




    private UserDAO userTable;
    public LogInWindow() {
    userTable = new UserDAOImpl();
    }
    @FXML
    private Label infoLabel;

    @FXML
    private TextField logInText;

    @FXML
    private PasswordField passwordText;
    @FXML
    void logIn(ActionEvent event) throws IOException {

        try
        {
            User user = null;
        user = userTable.getByUsername(logInText.getText());
            if(user != null && user.getPassword().equals(passwordText.getText()))
            {
                FXMLLoader fxmlLoader = Main.setRoot("fxml/MainWindow");
                MainWindow mainWindow = fxmlLoader.getController();
                mainWindow.initilizeController(user);
            }
            else {
                throw new RuntimeException("Invalid user or login");
            }
        }
        catch (RuntimeException e)
        {
        infoLabel.setVisible(true);
        }



    }

}
