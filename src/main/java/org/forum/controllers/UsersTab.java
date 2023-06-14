package org.forum.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import org.forum.Main;
import org.forum.Post;
import org.forum.User;
import org.forum.controllers.utils.CallBack;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.RankDAO;
import org.forum.dao.UserRankDAO;
import org.forum.services.UserService;

import java.io.IOException;
import java.util.List;

public class UsersTab {
    private  UserService userService;
    private CallBack dataBaseUpdated;
    private RankDAO ranksTable;
    private User logedUser;
    private UserRankDAO ranksUsersTable;
    private List<String> userRanks;
    public UsersTab()
    {
        userService = null;
        dataBaseUpdated = null;
        ranksTable = null;
        logedUser = null;
        ranksUsersTable = null;
        userRanks = null;
    }
    @FXML
    private Label login;
    @FXML
    private ScrollPane scrollP;
    @FXML
    private Label rankFieldLoged;

    @FXML
    private VBox usersContainer;
    private void fillInUsersContainer() throws IOException
    {
        usersContainer.getChildren().clear();
        List<User> list = userService.getAllUsers();
        for(User user : list)
        {
            FXMLLoader loader = Main.loadFXML("fxml/UserView");
            VBox v = loader.load();
            usersContainer.getChildren().add(v);
            UserView userView = loader.getController();
            userView.initializeController(logedUser,user,userService,ranksTable,ranksUsersTable,this::userChangedCallBack);
        }
    }
    private void userChangedCallBack(Object source)
    {
        try
        {
            fillInUsersContainer();
            dataBaseUpdated.invoked(source);
        }catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }

    }

    @FXML
    public void initializeController(User logedUser, UserService userService, RankDAO ranksTable, UserRankDAO ranksUsersTable, CallBack dataBaseUpdated)
    {
        usersContainer.minWidthProperty().bind(scrollP.widthProperty().subtract(25));

        usersContainer.minHeightProperty().bind(scrollP.heightProperty().subtract(2));
        this.logedUser = logedUser;
        this.userService = userService;
        this.ranksTable = ranksTable;
        this.dataBaseUpdated = dataBaseUpdated;
        this.ranksUsersTable = ranksUsersTable;
        login.setText(logedUser.getName());
        List<String> ranks = null;
        try {
            ranks = ranksUsersTable.getByUser(logedUser.getId());
            rankFieldLoged.setText(Helpers.ListToString(ranks));
        }
        catch (RuntimeException e)
        {
            rankFieldLoged.setText("");
        }
        try
        {
            fillInUsersContainer();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Helpers.showErrorWinowAndExitAplication();
        }
    }

}
