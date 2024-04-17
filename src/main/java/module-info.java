module org.example.database_btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.database_btl to javafx.fxml;
    exports org.example.database_btl;
    opens org.example.database_btl.Backend.model to javafx.fxml;
    exports org.example.database_btl.Backend.model;
    opens org.example.database_btl.Controller to javafx.fxml;
    exports org.example.database_btl.Controller;
}