package org.example.database_btl.Backend.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.model.Controller.TableController;
import org.example.database_btl.HelloApplication;

public class Table{

    public String ID;
    public boolean Status;

    public TableController tableController;
    public Pane tableContainer;

    public Table(String ID, boolean Status){
        this.ID = ID;
        this.Status = Status;
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Table.fxml"));
        try {
            tableContainer = loader.load();
            tableController = loader.getController();

            tableController.tableNumber.setText(ID);
            if(Status){
                tableController.rectangle.setFill(Color.RED);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
