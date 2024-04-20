package org.example.database_btl.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import org.example.database_btl.Backend.model.Restaurant;

public class MainEmployeeController {

    @FXML
    public Pane mainPane;

    public void initialize() {
        Restaurant restataurant = Restaurant.getInstance();
        mainPane.getChildren().addFirst(restataurant.menuArea);


    }
}
