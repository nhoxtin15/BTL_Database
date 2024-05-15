package database_btl.Backend.model.Receipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import database_btl.Backend.model.Receipt.AllReceipt;
import database_btl.Exception.NoReceipt;
import database_btl.Exception.PopUpMessage;
import database_btl.HelloApplication;


/**
 * Description:
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class AllReceiptController {
    @FXML
    public TabPane receipts;

    public void mergeReceipt(ActionEvent event){
        if(receipts.getTabs().isEmpty()){
            new PopUpMessage(new NoReceipt());
            return;
        }
        String receiptA = receipts.getSelectionModel().getSelectedItem().getText();
        FXMLLoader mergeReceiptLoader = new FXMLLoader(HelloApplication.class.getResource("Backend/model/Receipt/MergeReceipt.fxml"));
        try{
            Parent root = mergeReceiptLoader.load();
            MergeReceiptController controller = mergeReceiptLoader.getController();

            controller.receiptA.setText(controller.receiptA.getText()+"\n" + receiptA);
            controller.receiptAString = receiptA;

            for (javafx.scene.control.Tab tab : receipts.getTabs()){
                controller.receiptB.getItems().add(tab.getText());
            }


            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            new PopUpMessage(e);
        }

    }
    public void checkOut(ActionEvent event){
        AllReceipt.checkout();
    }



}
