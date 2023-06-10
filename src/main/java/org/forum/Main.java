package org.forum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.forum.services.UserService;
import org.forum.dao.RankDAOImpl;
import org.forum.dao.UserDAOImpl;
import org.forum.dao.UserRankDAOImpl;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * JavaFX App
 */
public class Main extends Application {
    private static Scene scene;
    public static final String DB_URL = "jdbc:sqlite:forum.db";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/LogInWindow"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}