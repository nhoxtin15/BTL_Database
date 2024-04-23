package org.example.database_btl.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import org.example.database_btl.Backend.model.Restaurant;
import javafx.scene.layout.VBox;
public class MainEmployeeController {

    @FXML
    public Pane mainPane;

    @FXML
    public VBox mainVbox;

    public void initialize() {
        Restaurant restataurant = Restaurant.getInstance();
        mainVbox.getChildren().add(1,restataurant.menuArea);


    }
}
