package database_btl.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import database_btl.Employee.Restaurant;
import javafx.scene.layout.VBox;
public class MainEmployeeController {

    ////////////////////////////////
    //                            //
    //     Main pane and Vbox     //
    //                            //
    ////////////////////////////////
        //mainPane
            //mainVbox
                //menuAndArea
                //header
                //footer

    @FXML
    public Pane mainPane;


    @FXML
    public VBox mainVbox;

    public void initialize() {
        Restaurant restaurant = Restaurant.getInstance();
        mainVbox.getChildren().add(1,restaurant.menuArea);
    }
}
