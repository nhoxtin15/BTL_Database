package org.example.database_btl.Backend.model.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class ReceiptProductController {

    @FXML
    public Label iD;

    @FXML
    public Label name;

    @FXML
    public ImageView removeButton;

    @FXML
    public Button plusButton;
    @FXML
    public Button minusButton;

    @FXML
    public TextField quantity;

    @FXML
    public Label nameProduct;

    @FXML
    public Label priceProduct;

    @FXML
    public Label totalPriceProduct;





    @FXML
    public void initialize() {


        plusButton.setOnMouseClicked(event -> {
            int currentQuantity = Integer.parseInt(quantity.getText());
            currentQuantity++;
            quantity.setText(String.valueOf(currentQuantity));
        });
        minusButton.setOnMouseClicked(event -> {
            int currentQuantity = Integer.parseInt(quantity.getText());
            if(currentQuantity > 0){
                currentQuantity--;
            }
            quantity.setText(String.valueOf(currentQuantity));
        });



        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                quantity.setText(oldValue);
            }
            if(newValue.length() > 4){
                quantity.setText(oldValue);
            }
            this.totalPriceProduct.setText(String.valueOf(Integer.parseInt(this.priceProduct.getText()) * Integer.parseInt(this.quantity.getText())));
        });
    }





}
