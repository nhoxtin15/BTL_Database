package database_btl.Employee.Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import database_btl.Employee.Restaurant;
import database_btl.Employee.Receipt.Controller.AllReceiptController;
import database_btl.Exception.NoReceipt;
import database_btl.Exception.PopUpMessage;
import database_btl.Exception.SameReceipt;

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



    public static void addReceipt(AreaReceipt areaReceipt){
        String name = areaReceipt.name + "_" + receiptMap.get(areaReceipt.name);
        receiptMap.put(areaReceipt.name,receiptMap.get(areaReceipt.name)+1);
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
        Tab CurrentReceipt = Restaurant.getInstance().allReceipt.allReceiptController.receipts.getSelectionModel().getSelectedItem();
        if (CurrentReceipt == null){
            throw new NoReceipt();
        }
        String currentName = CurrentReceipt.getText();

        for(Receipt receipt : Restaurant.getInstance().allReceipt.allReceipts){
            if(receipt.name.equals(currentName)){
                receipt.addProduct(name,price,1);
            }
        }
    }

    public static void mergeReceipt(String receiptA,String receiptB) throws Exception{
        if (receiptA.equals(receiptB)){
            throw new SameReceipt();
        }
        for(Receipt receipt1 : Restaurant.getInstance().allReceipt.allReceipts){
            if(receipt1.name.equals(receiptA)){
                for(Receipt receipt2 : Restaurant.getInstance().allReceipt.allReceipts){
                    if(receipt2.name.equals(receiptB)){
                        receipt1.mergeReceipt(receipt2);
                        removeReceipt(receiptB);
                    }
                }
                break;
            }
        }
    }

    public static void checkout(){
        if (Restaurant.getInstance().allReceipt.allReceiptController.receipts.getSelectionModel().getSelectedItem() == null){
            new PopUpMessage(new NoReceipt());
            return;
        }


        String currentName = Restaurant.getInstance().allReceipt.allReceiptController.receipts.getSelectionModel().getSelectedItem().getText();
        for(Receipt receipt : Restaurant.getInstance().allReceipt.allReceipts){
            if(receipt.name.equals(currentName)){
                try{
                    receipt.checkout();
                }
                catch (Exception e){
                    new PopUpMessage(e);
                }

            }
        }
    }

}
