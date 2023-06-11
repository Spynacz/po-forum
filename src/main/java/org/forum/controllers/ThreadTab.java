package org.forum.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.forum.ForumThread;
import org.forum.Post;
import org.forum.User;
import org.forum.controllers.utils.CallBack;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.UserDAO;
import org.forum.dao.UserRankDAO;
import org.forum.services.PostService;

import java.util.Date;

public class ThreadTab {
    private ForumThread forumThread;
    private User logedUser;
    private UserDAO userTable;
    private PostService postService;
    private UserRankDAO userRankTable;
    private CallBack statusChanged;
    private boolean isBeingCreated;
    public ThreadTab()
    {
        forumThread = null;
        logedUser = null;
        postService = null;
        userRankTable = null;
        statusChanged = null;
        isBeingCreated = false;
    }
    @FXML
    private VBox ThreadBodyContainer;

    @FXML
    private Label autorField;

    @FXML
    private Button editButton;

    @FXML
    private Button freezeButton;

    @FXML
    private Label login;

    @FXML
    private Label rankField;

    @FXML
    private Label topicField;

    @FXML
    private Button saveButton;

    @FXML
    void addPost(MouseEvent event) {

    }

    @FXML
    void editThread(MouseEvent event) {

    }

    @FXML
    void freezeThread(MouseEvent event) {

    }

    @FXML
    void saveThread(MouseEvent event) {

    }
    private void setInEditMode()
    {
        //TODO: end this method
        autorField.setVisible(false);
        autorField.setManaged(false);
        saveButton.setVisible(true);
        saveButton.setManaged(true);
        editButton.setVisible(false);
        editButton.setManaged(false);
    }
    public void initializeController(ForumThread forumThread, boolean isBeingCreated, User logedUser, UserDAO userTable, PostService postService, UserRankDAO rankTable, CallBack statusChanged)
    {
        //TODO: to end this function
        this.forumThread = forumThread;
        this.logedUser = logedUser;
        this.postService = postService;
        this.userRankTable = userRankTable;
        this.statusChanged = statusChanged;
        this.isBeingCreated = isBeingCreated;
        User user = null;
        try {
            user = userTable.getById(forumThread.getUserId());
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
        autorField.setText(user.getName());
    }
}
