package org.example.database_btl.Backend.model.Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.Restaurant;
import org.example.database_btl.Backend.model.controller.AllReceiptController;
import org.example.database_btl.Backend.model.controller.RestaurantController;
import org.example.database_btl.Exception.PopUpMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllReceipt {
    public ArrayList<Receipt> allReceipts;
    public static Map<String,Integer> receiptMap = new HashMap<>();
    public VBox allReceiptContainer;
    public AllReceiptController allReceiptController;

    public AllReceipt(){
        allReceipts = new ArrayList<>();
        initAllReceipt();
    }

    void initAllReceipt(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllReceipt.fxml"));
        try{
            allReceiptContainer = loader.load();
            allReceiptController = loader.getController();

        }
        catch (Exception e){
            new PopUpMessage(e);
        }
    }



    public static void addReceipt(String Area, AreaReceipt areaReceipt){
        String name = Area + "_" + receiptMap.get(Area);
        receiptMap.put(Area,receiptMap.get(Area)+1);
        Receipt newReceipt = new Receipt(name,areaReceipt);
        Restaurant.getInstance().allReceipt.allReceipts.add(newReceipt);
        Restaurant.getInstance().allReceipt.allReceiptController.receipts.getTabs().add(newReceipt.receiptContainer);
    }

    public static void removeReceipt(String name){
        for(Receipt receipt : Restaurant.getInstance().allReceipt.allReceipts){
            if(receipt.name.equals(name)){
                Restaurant.getInstance().allReceipt.allReceipts.remove(receipt);
                Restaurant.getInstance().allReceipt.allReceiptController.receipts.getTabs().remove(receipt.receiptContainer);
                break;
            }
        }

    }

    public static void addProduct(String name, int price) throws Exception{
        String currentName = Restaurant.getInstance().allReceipt.allReceiptController.receipts.getSelectionModel().getSelectedItem().getText();
        for(Receipt receipt : Restaurant.getInstance().allReceipt.allReceipts){
            if(receipt.name.equals(currentName)){
                receipt.addProduct(name,price);
            }
        }
    }
}
