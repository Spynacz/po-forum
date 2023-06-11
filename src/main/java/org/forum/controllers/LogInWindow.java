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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class LogInWindow {


    private UserDAO usersTable;
    private UserService userService;

    public LogInWindow() {
        usersTable = new UserDAOImpl();
        userService = new UserService(usersTable);
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

        try {
            User user = null;
            user = usersTable.getByUsername(logInText.getText());
            if (user != null && userService.passwordCorrect(user, passwordText.getText())) {
                FXMLLoader fxmlLoader = Main.setRoot("fxml/MainWindow");
                MainWindow mainWindow = fxmlLoader.getController();
                mainWindow.initializeController(user, usersTable);
            } else {
                throw new RuntimeException("Invalid user or login");
            }
        } catch (RuntimeException e) {
            infoLabel.setVisible(true);
        }
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        if (passwordText.getText().isBlank()) {
            nameTaken.setVisible(false);
            passwordRequired.setVisible(true);
        } else {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(passwordText.getText().toCharArray(), salt, 65536, 128);
            byte[] hash;
            try {
                SecretKeyFactory hasher = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                hash = hasher.generateSecret(spec).getEncoded();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            Base64.Encoder encoder = Base64.getEncoder();
            User newUser = new User(logInText.getText(), encoder.encodeToString(hash), encoder.encodeToString(salt));
            try {
                userService.addUser(newUser);
                FXMLLoader fxmlLoader = Main.setRoot("fxml/MainWindow");
                MainWindow mainWindow = fxmlLoader.getController();
                mainWindow.initializeController(newUser, usersTable);
            } catch (RuntimeException e) {
                passwordRequired.setVisible(false);
                nameTaken.setVisible(true);
            }
        }
    }

}
