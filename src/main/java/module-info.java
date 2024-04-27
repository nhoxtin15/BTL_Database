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
    exports org.example.database_btl.Backend.model.controller;
    opens org.example.database_btl.Backend.model.controller to javafx.fxml;
    exports org.example.database_btl.Backend.model.menuArea;
    opens org.example.database_btl.Backend.model.menuArea to javafx.fxml;
    exports org.example.database_btl.Backend.model.menuArea.Area;
    opens org.example.database_btl.Backend.model.menuArea.Area to javafx.fxml;
    exports org.example.database_btl.Backend.model.menuArea.Menu;
    opens org.example.database_btl.Backend.model.menuArea.Menu to javafx.fxml;
    exports org.example.database_btl.Backend.model.Receipt;
    opens org.example.database_btl.Backend.model.Receipt to javafx.fxml;
}