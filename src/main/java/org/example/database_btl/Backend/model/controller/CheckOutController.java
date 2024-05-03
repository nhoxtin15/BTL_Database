package org.example.database_btl.Backend.model.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.Receipt.ProductReceipt;
import org.example.database_btl.HelloApplication;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 02/05/2024$
 */
public class CheckOutController {
    @FXML
    public VBox productContainer;

    @FXML
    public TextField totalPrice;

    @FXML
    public TextField point;

    @FXML
    public TextField finalPrice;

    public void addProduct(ProductReceipt productReceipt){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Backend/model/Receipt/CheckOutProduct.fxml"));
        try{
            Pane product = loader.load();
            CheckOutProductController controller = loader.getController();
            controller.ID.setText(String.valueOf(productReceipt.ID));
            controller.Name.setText(productReceipt.name);
            controller.Quantity.setText(String.valueOf(productReceipt.quantity));
            controller.Price.setText(String.valueOf(productReceipt.quantity));
            controller.totalPrice.setText(String.valueOf(productReceipt.quantity * productReceipt.price));

            productContainer.getChildren().add(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
