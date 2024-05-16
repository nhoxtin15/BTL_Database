package database_btl.Employee.Receipt;

import database_btl.Employee.Receipt.Checkout.Checkout;
import database_btl.Employee.Restaurant;
import database_btl.Exception.NoTableSelected;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import database_btl.Employee.Receipt.Checkout.Controller.CheckOutController;
import database_btl.Employee.Receipt.Controller.ReceiptController;

import java.util.ArrayList;

/**
 * Description: Store each Receipt$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

public class Receipt {

    public String name;
    public ArrayList<ProductReceipt> productReceipts ;
    public Tab receiptContainer;
    public ReceiptController receiptController;

    public ArrayList<String> tables;
    public ArrayList<AreaReceipt> areaReceipts;


    public Receipt(String name, AreaReceipt areaReceipt){
        this.name = name;
        productReceipts = new ArrayList<>();
        this.areaReceipts = new ArrayList<>();
        this.areaReceipts.add(areaReceipt);
        this.tables = new ArrayList<>();
        for (String i : areaReceipt.tables){
            this.tables.add(areaReceipt.name + "_" + i);
        }

        initReceipt();
    }



    public void initReceipt(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
        try{
            receiptContainer = loader.load();
            receiptController = loader.getController();
            receiptContainer.setText(name);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProduct(String name, int price,int quantity){
        for (ProductReceipt productReceipt : productReceipts){
            if(productReceipt.name.equals(name)){
                productReceipt.increaseQuantity(quantity);
                return;
            }
        }

        ProductReceipt productReceipt = new ProductReceipt(this.productReceipts.size()+1,name, price,tables);
        productReceipt.productReceiptController.removeButton.setOnMouseClicked(event -> {
            removeProduct(name,price);
        });


        productReceipts.add(productReceipt);
        productReceipt.productReceiptController.totalPriceProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTotalMoney();
        });
        productReceipt.productReceiptController.quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            productReceipt.quantity =  (Integer.parseInt(newValue));
            updateTotalMoney();
        });


        updateTotalMoney();

        this.receiptController.addProduct(productReceipt);
    }


    public void removeProduct(String name, int price){
        for (int iter = 0; iter <productReceipts.size(); iter++){
            if(productReceipts.get(iter).equals(name,price)){
                for(int i = iter+1; i < productReceipts.size(); i++){
                    productReceipts.get(i).setID(i);
                }
                productReceipts.remove(iter);
                this.receiptController.receiptProducts.getChildren().remove(iter);
                break;
            }
        }
    }


    public synchronized void updateTotalMoney(){
        long totalMoney = 0;
        for (ProductReceipt receipt : this.productReceipts){
            totalMoney += (long) receipt.price * receipt.quantity;
        }
        this.receiptController.totalMoney.setText(String.valueOf(totalMoney));

    }


    public void mergeReceipt(Receipt receipt){
        for (ProductReceipt productReceipt : receipt.productReceipts){
            this.addProduct(productReceipt.name,productReceipt.price,productReceipt.quantity);
            this.productReceipts.getLast().setQuantity(productReceipt.quantity);
        }
        this.areaReceipts.addAll(receipt.areaReceipts);
        this.tables.addAll(receipt.tables);

    }


    public void checkout() throws  Exception {
        //get all the product

        for (ProductReceipt productReceipt : productReceipts) {
            if(productReceipt.quantity == 0){
                continue; //skip the product with quantity = 0
            }
            if (productReceipt.tableName == null) {
                throw new NoTableSelected(productReceipt.name);
            }
        }
        Restaurant.getInstance().checkout = new Checkout(this.productReceipts);

        Restaurant.getInstance().checkout.show();

    }

}
