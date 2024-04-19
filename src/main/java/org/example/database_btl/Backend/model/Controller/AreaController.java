package org.example.database_btl.Backend.model.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.database_btl.HelloApplication;

public class AreaController {
    @FXML
    public Tab Area;

    @FXML
    public GridPane tables;



    public void initialize() {
        try {
            int count =1;
            for (int i = 0; i < tables.getRowCount(); i++) {
                for (int j = 0; j < tables.getColumnCount(); j++) {
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Table.fxml"));
                    Pane pane = loader.load();
                    TableController controller = loader.getController();

                    controller.tableNumber.setText("" +(count++));
                    tables.add(pane, j, i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
