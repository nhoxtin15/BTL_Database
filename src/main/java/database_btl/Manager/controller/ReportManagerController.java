package database_btl.Manager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database_btl.Exception.PopUpMessage;
import database_btl.HelloApplication;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ReportManagerController {
    public void getReportQuarter(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Manager/Report.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();

        } catch (Exception e) {
            new PopUpMessage(e);
        }

    }
}
