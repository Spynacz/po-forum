module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.forum to javafx.fxml;
    exports org.forum;
}
