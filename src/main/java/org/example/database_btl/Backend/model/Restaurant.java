package org.example.database_btl.Backend.model;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.controller.RestaurantController;
import org.example.database_btl.Backend.model.menuArea.MenuAndTables;
import org.example.database_btl.Backend.model.Receipt.ProductReceipt;
import org.example.database_btl.Backend.model.Receipt.Receipt;

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


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Restaurant.fxml"));
        try {
            menuArea = loader.load();
            restaurantController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        restaurantController.mainHbox.getChildren().addFirst(menuAndTables.menuAndTables);


        FXMLLoader ReceiptLoader = new FXMLLoader(getClass().getResource("Receipt/AllReceipt.fxml"));
        try{
            VBox receiptArea = ReceiptLoader.load();
            restaurantController.mainHbox.getChildren().add(receiptArea);
            ProductReceipt productReceipt = new ProductReceipt(1, "Coca", 10000);
            productReceipt.initReceiptProduct();
            receiptArea.getChildren().add(productReceipt.productReceiptContainer);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
