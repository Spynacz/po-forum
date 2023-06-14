package org.forum.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
import org.forum.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainWindow{
    private User user;
    private UserDAO usersTable;
    private ThreadDAO threadsTable;
    private UserService userService;
    private UserRankDAO userRankTable;
    private RankDAO ranksTable;
    private PostDAO postTable;
    private ThreadService threadService;
    private PostService postService;
    @FXML
    private ComboBox<String> comboSearch;

    @FXML
    private VBox threadsContainer;
    @FXML
    private ScrollPane scrollP;
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
        threadsTable = null;
        userRankTable = null;
        postTable = null;
        ranksTable = null;
        postService = null;
        threadService = null;
        userService = null;
    }

    @FXML
    void addThread(MouseEvent event) {
        try {
            ForumThread forumThread = new ForumThread("Nowy wątek",user.getId());
            FXMLLoader loader = Main.loadFXML("fxml/ThreadTab");
            Tab tab = loader.load();
            ThreadTab threadTab = loader.getController();
            threadTab.initializeController(forumThread,true,user,usersTable,postTable,postService,userRankTable,threadService,userService,this::threadCallBack);
            tabsContainer.getTabs().add(tab);
            tabsContainer.getSelectionModel().select(tab);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }
    }


    @FXML
    void searchChange(ActionEvent event) {
        if(comboSearch.getValue().equals("Tytuł")||comboSearch.getValue().equals("Autor"))
        {
            searchField.setVisible(true);
            searchField.setManaged(true);
        } else if (comboSearch.getValue().equals("brak filtra"))
        {
            searchField.setVisible(false);
            searchField.setManaged(false);
        }
    }
    private List<ForumThread> getThreads()
    {
        try
        {
            if(comboSearch.getValue().equals("brak filtra"))
            {
                return threadsTable.getAll();
            }else if(comboSearch.getValue().equals("Tytuł"))
            {
                return threadsTable.getByTitle(searchField.getText());
            }else if(comboSearch.getValue().equals("Autor"))
            {
                return  threadsTable.getByUser(usersTable.getByUsername(searchField.getText()).getId());

            }
        }
        catch (Exception e)
        {
            return  new ArrayList<>();
        }
        return  new ArrayList<>();
    }
    @FXML
    void searchFor(MouseEvent event) {
        try {
            fillInThreadsContainer();
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
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
    private void userCallBack(Object source)
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
            threadTab.initializeController(forumThread,false,user,usersTable,postTable,postService,userRankTable,threadService,userService, this::threadCallBack);
            tabsContainer.getTabs().add(tab);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }

    }
    private void fillInThreadsContainer() throws IOException {
        threadsContainer.getChildren().clear();
        List<ForumThread> list = getThreads();
        for(ForumThread thread : list)
        {
            FXMLLoader loader = Main.loadFXML("fxml/ThreadPreview");
            VBox v = loader.load();
            threadsContainer.getChildren().add(v);
            ThreadPreview threadPreview = loader.getController();
            threadPreview.initilizeController(user,thread,usersTable,threadsTable,postTable,userRankTable,threadService,this::threadPreviewCallBack, this::threadPreviewCallBackOpenNew);
        }

    }
    private void addUsersTab() throws IOException
    {
        FXMLLoader loader = Main.loadFXML("fxml/UsersTab");
        Tab tab = loader.load();
        UsersTab usersTab = loader.getController();
        usersTab.initializeController(user,userService,ranksTable,userRankTable,this::userCallBack);
        tabsContainer.getTabs().add(tab);
    }
    public void initializeController(User user, UserDAO usersTable, ThreadDAO threadsTable, UserRankDAO userRankTable, PostDAO postTable, RankDAO ranksTable, PostService postService, ThreadService threadService, UserService userService)
    {

        threadsContainer.minWidthProperty().bind(scrollP.widthProperty().subtract(25));

        threadsContainer.minHeightProperty().bind(scrollP.heightProperty().subtract(2));
        this.user = user;
        this.usersTable = usersTable;
        login.setText(user.getName());
        this.usersTable = usersTable;
        this.threadsTable = threadsTable;
        this.userRankTable = userRankTable;
        this.postTable = postTable;
        this.ranksTable = ranksTable;
        this.postService = postService;
        this.threadService = threadService;
        this.userService = userService;
        List<String> l = new ArrayList<String>();
        l.add("brak filtra");
        l.add("Tytuł");
        l.add("Autor");
        ObservableList list = FXCollections.observableList(l);
        comboSearch.setItems(list);
        comboSearch.getSelectionModel().select(0);
        searchField.setVisible(false);
        searchField.setManaged(false);
        try {
            List<String> ranks = userRankTable.getByUser(user.getId());
            ranKfield.setText(Helpers.ListToString(ranks));
            if(Helpers.isAdmin(user,ranks))
            {
                try {
                    addUsersTab();
                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    Helpers.showErrorWinowAndExitAplication();
                }
            }
        }
        catch (RuntimeException e)
        {
            ranKfield.setText("");
        }

        try {
            fillInThreadsContainer();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }

    }

}
