package org.example.database_btl.Backend.model;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.example.database_btl.Backend.model.controller.RestaurantController;
import org.example.database_btl.Backend.model.menuArea.MenuAndTables;
import org.example.database_btl.Backend.model.receipt.Receipt;
import org.example.database_btl.HelloApplication;

public class Restaurant {

    // This class is a singleton class that contains the main components of the restaurant
    // It contains the menu and tables and the receipt
    // It also contains the controller for the restaurant

    ////////////////////////////////
    //                            //
    // Menu and area and Receipt  //
    //                            //
    ////////////////////////////////

        public MenuAndTables menuAndTables;
        public Receipt receipt;

    ////////////////////////////////
    //                            //
    //  Controller and Container  //
    //                            //
    ////////////////////////////////


        public RestaurantController restaurantController;

        public HBox menuArea;

    ////////////////////////////////
    //   Restaurant's SingleTon   //
    //           Stuff            //
    ////////////////////////////////

        private static  Restaurant instance = null;
        public static Restaurant getInstance(){
            if(instance == null){
                instance = new Restaurant();
            }
            return instance;
        }

    ////////////////////////////////
    //                            //
    //        Constructor         //
    //                            //
    ////////////////////////////////
        // This constructor initializes the menu and tables and the receipt
        // It also initializes the controller and the containers for the restaurant
    public Restaurant() {
        this.menuAndTables = new MenuAndTables();
        this.receipt = new Receipt();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Restaurant.fxml"));
        try {
            menuArea = loader.load();
            restaurantController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        restaurantController.mainHbox.getChildren().addFirst(menuAndTables.menuAndTables);
    }
}
