package org.forum.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.forum.ForumThread;
import org.forum.Post;
import org.forum.User;
import org.forum.controllers.utils.CallBack;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.PostDAO;
import org.forum.dao.ThreadDAO;
import org.forum.dao.UserDAO;
import org.forum.dao.UserRankDAO;
import org.forum.services.PostService;
import org.forum.services.ThreadService;
import org.forum.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostTemplate {
    private Post post;
    private User logedUser;
    private UserDAO userTable;
    private PostService postService;
    private UserService userService;
    private UserRankDAO userRankTable;
    private CallBack statusChanged;
    private boolean isBeingCreated;
    List<String> ranks = null;
    public PostTemplate()
    {
        logedUser = null;
        postService = null;
        userTable = null;
        statusChanged = null;
        post = null;
        isBeingCreated = false;
        userService = null;
    }
    @FXML
    private Label autorField;
    @FXML
    private Button cancelButton;
    @FXML
    private Label dateField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TextArea postField;

    @FXML
    private Label rankField;

    @FXML
    private Button saveButton;

    @FXML
    void deletePost(MouseEvent event) {
        try {
            postService.removePost(post.getId());
            userService.updateUserPostCount(logedUser.getName());
            statusChanged.invoked(post);

        }
        catch (Exception e)
        {

            Helpers.showErrorWinowAndExitAplication();
        }
    }

    @FXML
    void editPost(MouseEvent event) {
        setInEditMode();
    }
    private void setInEditMode()
    {
        postField.setEditable(true);
        saveButton.setVisible(true);
        saveButton.setManaged(true);
        cancelButton.setVisible(true);
        cancelButton.setManaged(true);
        editButton.setVisible(false);
        editButton.setManaged(false);
        deleteButton.setVisible(false);
        deleteButton.setManaged(false);

    }
    private void setInViewModel()
    {
        postField.setEditable(false);
        saveButton.setVisible(false);
        saveButton.setManaged(false);
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);
        if(Helpers.hasAccesToChangeStandard(logedUser,post.getUserId(),ranks))
        {
            editButton.setVisible(true);
            editButton.setManaged(true);
            deleteButton.setVisible(true);
            deleteButton.setManaged(true);
        }
        else
        {
            editButton.setVisible(false);
            editButton.setManaged(false);
            deleteButton.setVisible(false);
            deleteButton.setManaged(false);
        }

    }

    @FXML
    void savePost(MouseEvent event) {
        post.setBody(postField.getText());
        try {
            if(!isBeingCreated)
            {
                postService.editPost(post);
            }
            else
            {
                post.setCurrentTime();
                postService.createPost(post);
                userService.updateUserPostCount(logedUser.getName());
                isBeingCreated = false;
            }
            setInViewModel();
            statusChanged.invoked(post);
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
    }
    @FXML
    void cancelAddingPost(MouseEvent event) {
        isBeingCreated = false;
        statusChanged.invoked(post);

    }
    public void initializeController(Post post, boolean isBeingCreated,User logedUser,UserRankDAO userRankTable, UserDAO userTable, PostService postService, UserRankDAO rankTable,UserService userService, CallBack statusChanged)
    {

        saveButton.setVisible(false);
        saveButton.setManaged(false);
        this.post = post;
        this.logedUser = logedUser;
        this.userTable = userTable;
        this.postService = postService;
        this.statusChanged = statusChanged;
        this.isBeingCreated = isBeingCreated;
        postField.setText(post.getBody());
        this.userService = userService;
        User user = null;
        try {
            user = userTable.getById(post.getUserId());
            ranks = userRankTable.getByUser(logedUser.getId());
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
        autorField.setText(user.getName());
        dateField.setText((new Date(post.getTimestamp())).toString());
        try
        {

            rankField.setText(Helpers.ListToString(userRankTable.getByUser(user.getId())));
        }
        catch (RuntimeException e)
        {
            rankField.setText("");
        }
        if(isBeingCreated)
        {
            setInEditMode();
        }
        else
        {
            setInViewModel();
        }
    }

}
