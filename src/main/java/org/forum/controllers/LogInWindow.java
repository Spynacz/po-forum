package org.forum.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.forum.Main;
import org.forum.User;
import org.forum.dao.UserDAO;
import org.forum.dao.UserDAOImpl;
import org.forum.services.NoPasswordException;
import org.forum.services.UserService;

import java.io.IOException;

public class LogInWindow {




    private UserDAO usersTable;
    private UserService userService;
    public LogInWindow() {
    usersTable = new UserDAOImpl();
    }
    @FXML
    private Label infoLabel;
    @FXML
    private Label nameTaken;
    @FXML
    private Label passwordRequired;
    @FXML
    private TextField logInText;

    @FXML
    private PasswordField passwordText;
    @FXML
    void logIn(ActionEvent event) throws IOException {

        try
        {
            User user = null;
        user = usersTable.getByUsername(logInText.getText());
            if(user != null && user.getPassword().equals(passwordText.getText()))
            {
                FXMLLoader fxmlLoader = Main.setRoot("fxml/MainWindow");
                MainWindow mainWindow = fxmlLoader.getController();
                mainWindow.initializeController(user,usersTable);
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

    @FXML
    void register(ActionEvent event) throws IOException {
        User newUser = new User(logInText.getText(), passwordText.getText());
        try {
            userService.addUser(newUser);
            FXMLLoader fxmlLoader = Main.setRoot("fxml/MainWindow");
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.initializeController(newUser,usersTable);
        } catch (NoPasswordException e) {
            nameTaken.setVisible(false);
            passwordRequired.setVisible(true);
        } catch (RuntimeException e) {
            passwordRequired.setVisible(false);
            nameTaken.setVisible(true);
        }
    }

}
