package database_btl.Employee.Receipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import database_btl.Employee.Receipt.AllReceipt;
import database_btl.Exception.PopUpMessage;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 02/05/2024$
 */
public class MergeReceiptController {
    @FXML
    Label receiptA;

    @FXML
    ChoiceBox<String> receiptB;

    String receiptAString;

    public void merge(ActionEvent event){
        if(receiptB.getValue() == null){
            new PopUpMessage("Error","Please select a receipt to merge");
            return ;
        }


        try{
            System.out.println(receiptA.getText());
            System.out.println(receiptB.getValue());
            AllReceipt.mergeReceipt(receiptAString,receiptB.getValue());
            new PopUpMessage("Success","Receipts merged successfully");
        } catch (Exception e) {
            new PopUpMessage(e);
        }
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
