package org.forum.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.forum.ForumThread;
import org.forum.User;
import org.forum.controllers.utils.CallBack;
import org.forum.controllers.utils.Helpers;
import org.forum.dao.*;
import org.forum.services.ThreadService;

import java.util.Date;
import java.util.List;

public class ThreadPreview {
    private ForumThread thread;
    private ThreadDAO threadTable;
    private UserDAO userTable;
    private PostDAO postTable;
    private ThreadService threadService;
    private UserRankDAO userRankTable;
    private CallBack statusChanged;
    CallBack openThread;
    private ThreadTab theirsTab;
    public ThreadPreview()
    {
        thread = null;
        threadTable = null;
        userTable = null;
        postTable = null;
        threadService = null;
        userRankTable =null;
        setTheirsTab(null);
    }
    @FXML
    private Label autorField;

    @FXML
    private Label dataField;

    @FXML
    private Label date;

    @FXML
    private Label threadTitle;

    @FXML
    private Button deleteButton;

    @FXML
    private Button freezeButton;
    @FXML
    private Button unFreezeButton;

    @FXML
    private Label rankField;

    @FXML
    void deleteThread(MouseEvent event) {
        try{

        threadService.removeThread(getThread().getId());
        statusChanged.invoked(this);

        }
            catch (RuntimeException e)
        {
            //ToDO: error handling
        }
    }
    @FXML
    void freezeThread(MouseEvent event) {
        try {
            getThread().setClosed(true);
            threadTable.update(getThread());
            statusChanged.invoked(this);
        } catch (RuntimeException e) {
            //ToDO: error handling
        }
    }
        @FXML
        void unFreezeThread(MouseEvent event) {
            try {
                getThread().setClosed(false);
                threadTable.update(getThread());
                statusChanged.invoked(this);
            } catch (RuntimeException e) {
                //ToDO: error handling
            }
        }
    @FXML
    void openThread(MouseEvent event) {
        openThread.invoked(this);
    }


    public void initilizeController(User logedUser, ForumThread thread, UserDAO userTable, ThreadDAO threadTable, PostDAO postTable, UserRankDAO rankTable, ThreadService threadService, CallBack statusChanged, CallBack openThread)
    {
        this.thread = thread;
        this.userTable = userTable;
        this.threadTable =threadTable;
        this.postTable = postTable;
        this.userRankTable = rankTable;
        this.openThread = openThread;
        User user = null;
        try {
            user = userTable.getById(thread.getUserId());
        }
        catch (Exception e)
        {
            Helpers.showErrorWinowAndExitAplication();
        }
        threadTitle.setText(thread.getTitle());
        autorField.setText(user.getName());
        dataField.setText((new Date(thread.getTimestamp())).toString());
        this.threadService = threadService;
        this.statusChanged = statusChanged;
        if(thread.isClosed())
        {
            freezeButton.setVisible(false);
            freezeButton.setManaged(false);
        }
        else
        {
            unFreezeButton.setVisible(false);
            unFreezeButton.setManaged(false);
        }
        List<String> ranks = null;
        try {
            ranks = rankTable.getByUser(user.getId());
            rankField.setText(Helpers.ListToString(ranks));
        }
        catch (RuntimeException e)
        {
            rankField.setText("");
        }

        if(!Helpers.hasAccesToChangeStandard(logedUser,user,ranks))
        {
            deleteButton.setVisible(false);
            freezeButton.setVisible(false);
            unFreezeButton.setVisible(false);
        }
    }

    public ThreadTab getTheirsTab() {
        return theirsTab;
    }

    public void setTheirsTab(ThreadTab theirsTab) {
        this.theirsTab = theirsTab;
    }

    public ForumThread getThread() {
        return thread;
    }
}
