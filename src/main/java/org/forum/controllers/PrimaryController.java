package org.forum.controllers;
import org.forum.*;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        Main.setRoot("fxml/secondary");
    }
}
