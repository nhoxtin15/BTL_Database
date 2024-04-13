module org.example.database_btl {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.database_btl to javafx.fxml;
    exports org.example.database_btl;
}