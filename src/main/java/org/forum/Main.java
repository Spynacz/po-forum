package org.forum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {
    private static Scene scene;
    public static final String DB_URL = "jdbc:sqlite:forum.db";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/LogInWindow").load(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static FXMLLoader setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = loadFXML(fxml);
        scene.setRoot(fxmlLoader.load());
        return fxmlLoader;
    }
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

}