package database_btl.Employee.Receipt;

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
                System.out.println("Increase quantity");
                productReceipt.increaseQuantity(quantity);
                return;
            }
        }

        ProductReceipt productReceipt = new ProductReceipt(this.productReceipts.size()+1,name, price);
        productReceipt.productReceiptController.removeButton.setOnMouseClicked(event -> {
            removeProduct(name,price);
        });
        for (String i : this.tables){
            productReceipt.productReceiptController.tables.getItems().add(i);
        }

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


    public void checkout(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
        try{
            Parent checkout = loader.load();
            CheckOutController checkOutController = loader.getController();
            long totalPrice = 0;
            for (ProductReceipt productReceipt : productReceipts){
                checkOutController.addProduct(productReceipt);
                totalPrice += (long) productReceipt.price * productReceipt.quantity;
            }
            checkOutController.totalPrice.setText(String.valueOf(totalPrice));
            checkOutController.finalPrice.setText(String.valueOf(totalPrice));
            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(checkout));

            //show and wait
            stage.showAndWait();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
