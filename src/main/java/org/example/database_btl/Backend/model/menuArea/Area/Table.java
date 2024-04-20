package org.example.database_btl.Backend.model.menuArea.Area;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.model.controller.TableController;
import org.example.database_btl.HelloApplication;

public class Table{

    public String ID;
    public int Status;

    public TableController tableController;
    public Pane tableContainer;

    public Table(String ID, int Status){
        this.ID = ID;
        this.Status = Status;

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Table.fxml"));
        try {
            tableContainer = loader.load();
            tableController = loader.getController();

            tableController.tableNumber.setText(ID);
            if(Status == 1){
                tableController.rectangle.setFill(Color.RED);
            }

            tableController.pane.setOnMouseClicked(e->{
                if(this.Status == 0){
                    tableController.rectangle.setFill(Color.GREEN);
                    this.Status = -1;
                }


                else if(this.Status == 1){

                }
                else{
                    tableController.rectangle.setFill(Color.BLUE);
                    this.Status = 0;
                }

                System.out.println("Clicked");
            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
