package org.forum.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.forum.ForumThread;
import org.forum.Main;
import org.forum.Post;
import org.forum.User;
import org.forum.controllers.utils.CallBack;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.PostDAO;
import org.forum.dao.UserDAO;
import org.forum.dao.UserRankDAO;
import org.forum.services.PostService;
import org.forum.services.ThreadService;
import org.forum.services.UserService;

import java.io.IOException;
import java.util.List;

public class ThreadTab {
    private ForumThread forumThread;
    private User logedUser;
    private UserDAO userTable;
    private ThreadService threadService;
    private PostDAO postsTable;
    private PostService postService;
    private UserRankDAO userRankTable;
    private CallBack statusChanged;
    private boolean isBeingCreated;
    private UserService userService;
    public ThreadTab()
    {
        forumThread = null;
        logedUser = null;
        postService = null;
        userRankTable = null;
        statusChanged = null;
        isBeingCreated = false;
        postsTable = null;
        threadService = null;
        userService = null;

    }
    @FXML
    private Label autorField;

    @FXML
    private Tab currentTab;

    @FXML
    private Button editButton;
    @FXML
    private ScrollPane scrollP;
    @FXML
    private Label login;

    @FXML
    private VBox postContainer;

    @FXML
    private Label rankField;

    @FXML
    private Label rankFieldLoged;

    @FXML
    private Button saveButton;

    @FXML
    private TextField topicField;
    @FXML
    private Button addPostButton;

    @FXML
    private Label currentTabLabel;

    @FXML
    private Button cancelButton;
    private void CancelTab()
    {
        currentTab.getTabPane().getTabs().remove(currentTab);
    }
    @FXML
    void cancelTab(MouseEvent event) {
        CancelTab();
    }

    @FXML
    void addPost(MouseEvent event) {

        forumThread.setTitle(topicField.getText());
        try {
            Post post = new Post("",logedUser.getId(),forumThread.getId());
            FXMLLoader loader = Main.loadFXML("fxml/PostTemplate");
            VBox v = loader.load();
            postContainer.getChildren().add(v);
            PostTemplate postTemplate = loader.getController();
            postTemplate.initializeController(post,true,logedUser,userRankTable,userTable,postService,userRankTable,userService, this::postChangeCallBack);
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
    }

    @FXML
    void editThread(MouseEvent event) {
        setInEditMode();

    }

    @FXML
    void saveThread(MouseEvent event) {
        forumThread.setTitle(topicField.getText());
        try {
            if(!isBeingCreated)
            {
                threadService.editTitle(forumThread);
                setInViewMode();
                currentTabLabel.setText(forumThread.getTitle());
                statusChanged.invoked(forumThread);
            }
            else
            {
                forumThread.setCurrentTime();
                threadService.createThread(forumThread);
                addPostButton.setManaged(true);
                addPostButton.setVisible(true);
                isBeingCreated = false;
                setInViewMode();
                currentTabLabel.setText(forumThread.getTitle());
                statusChanged.invoked(forumThread);
                CancelTab();
            }

        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
    }
    private void setInEditMode()
    {

        topicField.setEditable(true);
        saveButton.setVisible(true);
        saveButton.setManaged(true);
        editButton.setVisible(false);
        editButton.setManaged(false);
    }
    private void setInViewMode()
    {
        topicField.setEditable(false);
        saveButton.setVisible(false);
        saveButton.setManaged(false);
        editButton.setVisible(true);
        editButton.setManaged(true);
    }
    private void postChangeCallBack(Object source)
    {
        try
        {
            fillInpostContainer();
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
    }
    private void fillInpostContainer() throws IOException {
        postContainer.getChildren().clear();
        List<Post> list = postsTable.getByThread(forumThread.getId());
        for(Post post : list)
        {
            FXMLLoader loader = Main.loadFXML("fxml/PostTemplate");
            VBox v = loader.load();
            postContainer.getChildren().add(v);
            PostTemplate postTemplate = loader.getController();
            postTemplate.initializeController(post,false,logedUser,userRankTable,userTable,postService,userRankTable,userService,this::postChangeCallBack);
        }
    }

    public void initializeController(ForumThread forumThread, boolean isBeingCreated, User logedUser, UserDAO userTable, PostDAO postDAO, PostService postService, UserRankDAO userRankTable, ThreadService threadService, UserService userService, CallBack statusChanged)
    {
        setInViewMode();
        postContainer.minWidthProperty().bind(scrollP.widthProperty().subtract(25));
        postContainer.minHeightProperty().bind(scrollP.heightProperty().subtract(2));
        this.forumThread = forumThread;
        this.logedUser = logedUser;
        this.postService = postService;
        this.userRankTable = userRankTable;
        this.statusChanged = statusChanged;
        this.isBeingCreated = isBeingCreated;
        this.userService = userService;
        currentTabLabel.setText(forumThread.getTitle());
        this.postsTable =  postDAO;
        this.threadService = threadService;
        if(isBeingCreated)currentTabLabel.setText(forumThread.getTitle()+"*");
        else currentTabLabel.setText(forumThread.getTitle());
        this.userTable = userTable;
        User user = null;
        if(isBeingCreated)
        {
            setInEditMode();
        }
        if(forumThread.isClosed() || isBeingCreated)
        {
            addPostButton.setVisible(false);
            addPostButton.setManaged(false);
        }
        try {
            user = userTable.getById(forumThread.getUserId());
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
        autorField.setText(user.getName());
        login.setText(logedUser.getName());
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
        try
        {
            ranks = userRankTable.getByUser(logedUser.getId());
            rankFieldLoged.setText(Helpers.ListToString(ranks));
        }
        catch (RuntimeException e)
        {
            rankFieldLoged.setText("");
        }
        if(!Helpers.hasAccesToChangeStandard(logedUser,user,ranks))
        {
            editButton.setVisible(false);
            editButton.setManaged(false);
        }

        try
        {
            fillInpostContainer();
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
    }

}
