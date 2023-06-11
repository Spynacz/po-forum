package org.forum.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.forum.ForumThread;
import org.forum.Main;
import org.forum.User;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.*;
import org.forum.services.PostService;
import org.forum.services.ThreadService;

import java.io.IOException;
import java.util.List;

public class MainWindow{
    private User user;
    private UserDAO usersTable;
    private ThreadDAO threadsTable;

    private UserRankDAO userRankTable;
    private PostDAO postTable;
    private ThreadService threadService;
    private PostService postService;
    @FXML
    private VBox threadsContainer;
    @FXML
    private TabPane tabsContainer;
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
        userRankTable = new UserRankDAOImpl();
        postTable = new PostDAOImpl();
        threadService = new ThreadService(threadsTable, postTable);
        postService = new PostService(postTable,threadsTable);
    }

    @FXML
    void addThread(MouseEvent event) {
        //TODO:improve this version of ading new threads
        ForumThread forumThread = new ForumThread("Nowy wątek",user.getId());
        threadService.createThread(forumThread);
        try {
            fillInThreadsContainer();
        }catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
    }

    @FXML
    void keyPressedCheckIfEnterPressed(KeyEvent event) {

    }

    @FXML
    void searchFor(MouseEvent event) {

    }
    private void threadPreviewCallBack(Object source)
    {
        try {
            fillInThreadsContainer();
        }catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }

    }
    private void threadCallBack(Object source)
    {
        try {
            fillInThreadsContainer();
        }catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }

    }
    private void threadPreviewCallBackOpenNew(Object source)
    {
        try {
            ThreadPreview threadPreiew = (ThreadPreview) source;
            ForumThread forumThread = threadPreiew.getThread();
            FXMLLoader loader = Main.loadFXML("fxml/ThreadTab");
            Tab tab = loader.load();
            ThreadTab threadTab = loader.getController();;
            threadTab.initializeController(forumThread,false,user,usersTable,postService,userRankTable,this::threadCallBack);
            tabsContainer.getTabs().add(tab);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }

    }
    private void fillInThreadsContainer() throws IOException {
        threadsContainer.getChildren().clear();
        List<ForumThread> list = threadsTable.getAll();
        for(ForumThread thread : list)
        {
            FXMLLoader loader = Main.loadFXML("fxml/ThreadPreview");
            VBox v = loader.load();
            threadsContainer.getChildren().add(v);
            ThreadPreview threadPreview = loader.getController();
            threadPreview.initilizeController(user,thread,usersTable,threadsTable,postTable,userRankTable,threadService,this::threadPreviewCallBack, this::threadPreviewCallBackOpenNew);
        }
        System.out.println("wysoksoc"+threadsContainer.getPrefHeight());
    }
    public void initializeController(User user, UserDAO usersTable)
    {
        this.user = user;
        this.usersTable = usersTable;
        login.setText(user.getName());

        try {
            List<String> ranks = userRankTable.getByUser(user.getId());
            ranKfield.setText(Helpers.ListToString(ranks));
        }
        catch (RuntimeException e)
        {
            ranKfield.setText("");
        }
        try {
            fillInThreadsContainer();
        }catch (Exception e)
        {
            System.out.println("złąpano"+e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }

    }

}
