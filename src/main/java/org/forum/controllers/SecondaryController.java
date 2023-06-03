package org.forum.controllers;
import org.forum.*;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        Main.setRoot("fxml/primary");
    }
}