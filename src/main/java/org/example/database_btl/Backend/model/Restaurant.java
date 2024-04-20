package org.example.database_btl.Backend.model;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.example.database_btl.Backend.model.controller.RestaurantController;
import org.example.database_btl.Backend.model.menuArea.MenuAndTables;
import org.example.database_btl.Backend.model.receipt.Receipt;
import org.example.database_btl.HelloApplication;

public class Restaurant {
    public MenuAndTables menuAndTables;
    public Receipt receipt;

    public RestaurantController restaurantController;

    public HBox menuArea;

    private static  Restaurant instance = null;
    public static Restaurant getInstance(){
        if(instance == null){
            instance = new Restaurant();
        }
        return instance;
    }

    public Restaurant() {
        this.menuAndTables = new MenuAndTables();
        this.receipt = new Receipt();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Restaurant.fxml"));
        try {
            menuArea = loader.load();
            restaurantController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        restaurantController.mainHbox.getChildren().addFirst(menuAndTables.menuAndTables);

    }
}
