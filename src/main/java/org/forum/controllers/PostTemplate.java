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

import java.util.Date;
import java.util.List;

public class PostTemplate {
    private Post post;
    private User logedUser;
    private UserDAO userTable;
    private PostService postService;
    private UserRankDAO userRankTable;
    private CallBack statusChanged;
    private boolean isBeingCreated;
    public PostTemplate()
    {
        logedUser = null;
        postService = null;
        userTable = null;
        statusChanged = null;
        post = null;
        isBeingCreated = false;
    }
    @FXML
    private Label autorField;

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

    }

    @FXML
    void editPost(MouseEvent event) {

    }
    private void setInEditMode()
    {
        postField.setEditable(true);
        saveButton.setVisible(true);
        saveButton.setManaged(true);
        editButton.setVisible(false);
        editButton.setManaged(false);
        deleteButton.setVisible(false);
        deleteButton.setManaged(false);
    }

    @FXML
    void savePost(MouseEvent event) {

    }
    public void initializeController(Post post, boolean isBeingCreated, User logedUser,UserDAO userTable, PostService postService, UserRankDAO rankTable, CallBack statusChanged)
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
        User user = null;
        try {
            user = userTable.getById(post.getUserId());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }
        autorField.setText(user.getName());
        dateField.setText((new Date(post.getTimestamp())).toString());
        if(isBeingCreated)
        {
            setInEditMode();
        }

        List<String> ranks = null;
        try
        {
            ranks = userRankTable.getByUser(user.getId());
            rankField.setText(Helpers.ListToString(ranks));
        }
        catch (RuntimeException e)
        {
            rankField.setText("");
        }
        if(!Helpers.hasAccesToChangeStandard(logedUser,user,ranks))
        {
            deleteButton.setVisible(false);
            editButton.setVisible(false);
        }
    }

}
