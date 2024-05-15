module org.example.database_btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens database_btl to javafx.fxml;
    exports database_btl;

    opens database_btl.Backend.model to javafx.fxml;
    exports database_btl.Backend.model;

    opens database_btl.Controller to javafx.fxml;
    exports database_btl.Controller;


    opens database_btl.Backend.model.controller to javafx.fxml;
    exports database_btl.Backend.model.controller;


    opens database_btl.Backend.model.menuArea to javafx.fxml;
    exports database_btl.Backend.model.menuArea;

    opens database_btl.Backend.model.menuArea.Area to javafx.fxml;
    exports database_btl.Backend.model.menuArea.Area;

    opens database_btl.Backend.model.menuArea.Menu to javafx.fxml;
    exports database_btl.Backend.model.menuArea.Menu;

    opens database_btl.Backend.model.Receipt to javafx.fxml;
    exports database_btl.Backend.model.Receipt;

    opens database_btl.Exception.Controller to javafx.fxml;
    exports database_btl.Exception.Controller;

    opens database_btl.Manager.controller to javafx.fxml;
    exports database_btl.Manager.controller;

    opens database_btl.Manager.model to javafx.fxml;
    exports database_btl.Manager.model;


}