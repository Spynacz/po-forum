module org.forum {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires lombok;

    opens org.forum to javafx.fxml;
    exports org.forum;
    exports org.forum.controllers;
    opens org.forum.controllers to javafx.fxml;
    exports org.forum.dao;
    exports org.forum.services;
    opens org.forum.services to javafx.fxml;
}
