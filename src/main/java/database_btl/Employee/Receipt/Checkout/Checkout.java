package database_btl.Employee.Receipt.Checkout;

import database_btl.Employee.Receipt.Checkout.Controller.CheckOutController;
import database_btl.Employee.Receipt.ProductReceipt;
import database_btl.Employee.menuArea.Menu.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Description: Perform the checkout process
 * Author: nhoxtin15$
 * Date: 16/05/2024$
 */

public class Checkout {
    public ArrayList<CheckOutProduct> products;


    public String totalPrice = "0";
    public String pointUsed = "0";
    public String pointHave = "0";
    public String FinalPrice = "0";


    public AnchorPane checkoutContainer;
    public CheckOutController checkOutController;




    public void show(){
        Stage stage = new Stage();
        stage.setTitle("Checkout");
        stage.setScene(new Scene(checkoutContainer));
        stage.show();
    }



    public Checkout(){
        products = new ArrayList<>();
    }

    public Checkout(ArrayList<ProductReceipt> productReceipts){
        this.products = new ArrayList<>();

        long totalPriceTemp = 0;

        for(ProductReceipt product : productReceipts){
            this.products.add(new CheckOutProduct(product));
            totalPriceTemp += (long) product.price * product.quantity;

        }
        this.totalPrice = String.valueOf(totalPriceTemp);
        this.FinalPrice = String.valueOf(totalPriceTemp);
        initCheckOut();
    }
    public void initCheckOut(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
        try{
            checkoutContainer = loader.load();
            checkOutController = loader.getController();

            checkOutController.totalPrice.setText(totalPrice);
            checkOutController.point.setText(pointUsed);

            checkOutController.finalPrice.setText(FinalPrice);

            for (CheckOutProduct product : products){
                checkOutController.addProduct(product);
            }
            checkOutController.buttonCheckOut.setOnAction(this::checkOut);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkOut(ActionEvent event){



    }




}
