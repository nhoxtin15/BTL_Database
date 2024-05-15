package database_btl.Backend.model.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import database_btl.Backend.model.Receipt.ProductReceipt;
import database_btl.HelloApplication;

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

    @FXML
    public TextField customer;

    @FXML
    public TextField customerPoint;


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

    public void chooseCustomer(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Backend/model/Receipt/CustomerChooser.fxml"));
        try{
            Parent product = loader.load();
            ChooseCustomerController controller = loader.getController();
            Scene scene = new Scene(product);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            customer.setText(controller.customerName);
            customerPoint.setText(controller.customerPoint);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void initialize(){
        customer.setText("No customer");
        customerPoint.setText("0");

        this.point.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                point.setText(oldValue);
                return;
            }
            int pointHave = Integer.parseInt(customerPoint.getText());
            int pointUsed = Integer.parseInt(newValue);
            if(pointUsed > pointHave) {
                point.setText(oldValue);
                return;
            }

        });

    }
}
