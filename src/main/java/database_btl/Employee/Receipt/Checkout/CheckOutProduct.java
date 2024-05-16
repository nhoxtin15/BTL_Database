package database_btl.Employee.Receipt.Checkout;

import database_btl.Employee.Receipt.Checkout.Controller.CheckOutProductController;
import database_btl.Employee.Receipt.ProductReceipt;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 16/05/2024$
 */
public class CheckOutProduct {

    public String ID;
    public String Name;
    public String Quantity;
    public String Price;
    public String TotalPrice;

    public String table;


    public Pane checkOutProductContainer;
    public CheckOutProductController checkOutProductController;

    public CheckOutProduct(ProductReceipt productReceipt){
        this.ID = String.valueOf(productReceipt.ID);
        this.Name = productReceipt.name;
        this.Quantity = String.valueOf(productReceipt.quantity);
        this.Price = String.valueOf(productReceipt.price);
        this.TotalPrice = String.valueOf(productReceipt.quantity * productReceipt.price);
        this.table = productReceipt.tableName;

        initCheckOutProduct();
    }

    public void initCheckOutProduct(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOutProduct.fxml"));
        try{
            checkOutProductContainer = loader.load();
            checkOutProductController = loader.getController();

            checkOutProductController.ID.setText(ID);
            checkOutProductController.Name.setText(Name);
            checkOutProductController.Quantity.setText(Quantity);
            checkOutProductController.Price.setText(Price);
            checkOutProductController.totalPrice.setText(TotalPrice);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }




    }

}
