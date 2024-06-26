package database_btl.Employee.Receipt.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import database_btl.Employee.Receipt.ProductReceipt;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class ReceiptController {
    @FXML
    public VBox receiptProducts;

    @FXML
    public Label totalMoney;



    public void addProduct(ProductReceipt product){
        receiptProducts.getChildren().add(product.productReceiptContainer);
    }
}
