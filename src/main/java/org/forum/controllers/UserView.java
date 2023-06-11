package org.forum.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.forum.ForumThread;
import org.forum.User;
import org.forum.controllers.utils.CallBack;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.*;
import org.forum.services.ThreadService;
import org.forum.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserView {
    private  UserService userService;
    private CallBack dataBaseUpdated;
    private RankDAO ranksTable;
    private User user;
    private UserRankDAO ranksUsersTable;
    private List<String> userRanks;
    public UserView()
    {
        userService = null;
        dataBaseUpdated = null;
        ranksTable = null;
        user = null;
        ranksUsersTable = null;
        userRanks = null;
    }

    @FXML
    private Button addRangeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button deleteRangeButton;
    @FXML
    private TextField addingRank;

    @FXML
    private Label postCount;

    @FXML
    private Label rankField;

    @FXML
    private ComboBox<String> removingRank;

    @FXML
    private Label userName;

    @FXML
    void addRank(MouseEvent event) {
        try {
            try
            {
                userService.addRank(user.getName(),addingRank.getText());
                dataBaseUpdated.invoked(user);
            }catch (Exception e)
            {
                if(e.getMessage().equals("Rank does not exist"))
                {
                    ranksTable.insert(addingRank.getText());
                    userService.addRank(user.getName(),addingRank.getText());
                    dataBaseUpdated.invoked(user);
                }
                else throw e;
            }

        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }

    }

    @FXML
    void deleteRank(MouseEvent event) {
    try
    {

        userService.removeRank(user.getName(),removingRank.getValue());
        dataBaseUpdated.invoked(user);
    }
    catch (Exception e)
    {
        userService.addRank(user.getName(),addingRank.getText());
        dataBaseUpdated.invoked(user);
    }
    }

    @FXML
    void deleteUser(MouseEvent event) {
        try
        {
            userService.removeUser(user.getName());
            dataBaseUpdated.invoked(user);
        }
        catch (Exception e)
        {
            userService.addRank(user.getName(),addingRank.getText());
        }
    }
    public void initializeController(User logedUser, User user, UserService userService, RankDAO ranksTable,UserRankDAO ranksUsersTable, CallBack dataBaseUpdated)
    {
        this.userService = userService;
        this.ranksTable = ranksTable;
        this.dataBaseUpdated = dataBaseUpdated;
        this.ranksUsersTable = ranksUsersTable;

        //if user is the admin than this is open by, don't let change in his account
        if(user.getId() == logedUser.getId())
        {
        addRangeButton.setVisible(false);
        addRangeButton.setManaged(false);
        deleteButton.setVisible(false);
        deleteButton.setManaged(false);
        deleteRangeButton.setManaged(false);
        deleteRangeButton.setVisible(false);
        }
        this.user = user;
        userName.setText(user.getName());
        postCount.setText(String.valueOf(user.getPostCount()));
        try {
            userRanks = ranksUsersTable.getByUser(user.getId());
            rankField.setText(Helpers.ListToString(userRanks));
            ObservableList list = FXCollections.observableList(userRanks);
            removingRank.setItems(list);
        }
        catch (RuntimeException e)
        {
            //nothing
        }



    }

}
