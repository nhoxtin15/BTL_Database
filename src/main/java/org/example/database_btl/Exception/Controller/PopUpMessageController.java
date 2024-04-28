package org.example.database_btl.Exception.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 28/04/2024$
 */
public class PopUpMessageController {
    @FXML
    public Label Error_name;
    @FXML
    public Label Error_description;


    public void ExitPopup(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
    }

}
