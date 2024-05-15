module org.example.database_btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens database_btl to javafx.fxml;
    exports database_btl;

    opens database_btl.Employee to javafx.fxml;
    exports database_btl.Employee;

    opens database_btl.Controller to javafx.fxml;
    exports database_btl.Controller;


    opens database_btl.Employee.controller to javafx.fxml;
    exports database_btl.Employee.controller;


    opens database_btl.Employee.menuArea to javafx.fxml;
    exports database_btl.Employee.menuArea;

    opens database_btl.Employee.menuArea.Area to javafx.fxml;
    exports database_btl.Employee.menuArea.Area;

    opens database_btl.Employee.menuArea.Menu to javafx.fxml;
    exports database_btl.Employee.menuArea.Menu;

    opens database_btl.Employee.Receipt to javafx.fxml;
    exports database_btl.Employee.Receipt;

    opens database_btl.Exception.Controller to javafx.fxml;
    exports database_btl.Exception.Controller;

    opens database_btl.Manager.controller to javafx.fxml;
    exports database_btl.Manager.controller;

    opens database_btl.Manager.model to javafx.fxml;
    exports database_btl.Manager.model;

    opens database_btl.Employee.menuArea.Area.Controller to javafx.fxml;
    exports database_btl.Employee.menuArea.Area.Controller;

    opens database_btl.Employee.menuArea.Menu.Controller to javafx.fxml;
    exports database_btl.Employee.menuArea.Menu.Controller;

    opens database_btl.Employee.Receipt.Controller to javafx.fxml;
    exports database_btl.Employee.Receipt.Controller;

    opens database_btl.Employee.Receipt.Checkout to javafx.fxml;
    exports database_btl.Employee.Receipt.Checkout;

    opens database_btl.Employee.Receipt.Checkout.Controller to javafx.fxml;
    exports database_btl.Employee.Receipt.Checkout.Controller;



}