package org.example.database_btl.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.database_btl.HelloApplication;

public class MainEmployeeController {
    @FXML
    private Pane HeaderPane;

    @FXML
    GridPane Table;

    public void initialize() {

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/product.fxml"));
            Pane pane = loader.load();
            for (int i = 0; i < Table.getRowCount();i++){
                for (int j = 0; j < Table.getColumnCount();j++){

                    HBox hBox= (HBox) pane.getChildren().getFirst();
                    hBox.getChildren().getFirst().setOnMouseClicked(e -> {
                        HBox hbox = ((HBox)((Button)e.getSource()).getParent());
                        TextField textField = (TextField) hbox.getChildren().get(1);
                        if(Integer.parseInt(textField.getText()) > 0) textField.setText((Integer.parseInt(textField.getText())-1)+"");
                    });
                    hBox.getChildren().get(2).setOnMouseClicked(e -> {
                        HBox hbox = ((HBox)((Button)e.getSource()).getParent());
                        TextField textField = (TextField) hbox.getChildren().get(1);
                        textField.setText((Integer.parseInt(textField.getText())+1)+"");
                    });
                    //Table.setMargin(pane, new Insets(10, 10, 10, 10));



                    Table.add(pane, j, i);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
