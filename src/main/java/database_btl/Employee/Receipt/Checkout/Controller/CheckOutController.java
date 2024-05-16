package database_btl.Employee.Receipt.Checkout.Controller;

import database_btl.Employee.Receipt.Checkout.CheckOutProduct;
import database_btl.Employee.Receipt.Checkout.Checkout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import database_btl.Employee.Receipt.ProductReceipt;

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

    @FXML
    public Button buttonCheckOut;

    public void addProduct(CheckOutProduct checkOutProduct){
        productContainer.getChildren().add(checkOutProduct.checkOutProductContainer);
    }

    public void chooseCustomer(){
        FXMLLoader loader = new FXMLLoader(Checkout.class.getResource("CustomerChooser.fxml"));
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
            int finalPrice = Integer.parseInt(totalPrice.getText()) - pointUsed;
            this.finalPrice.setText(String.valueOf(finalPrice));

        });

    }


}
