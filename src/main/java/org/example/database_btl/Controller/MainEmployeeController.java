package org.example.database_btl.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.database_btl.Backend.model.Controller.TableController;
import org.example.database_btl.HelloApplication;

public class MainEmployeeController {
    @FXML
    private Pane HeaderPane;

    @FXML
    public TabPane tabPane;

    public void initialize() {
        try {

            // Create 3 tabs
            for (int i = 0; i < 3; i++) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Area.fxml"));
                Tab tab = loader.load();
                tab.setText("Area " + (i + 1));
                tabPane.getTabs().add(tab);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
