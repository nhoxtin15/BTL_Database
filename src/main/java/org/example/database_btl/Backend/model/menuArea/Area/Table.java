package org.example.database_btl.Backend.model.menuArea.Area;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.model.controller.TableController;
import org.example.database_btl.HelloApplication;

public class Table{
    public enum EnumStatus{
        FREE(-1), SELECTED(0), OCCUPIED(1);
        private final int value;

        EnumStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public String ID;
    public int Status;

    public boolean isBuilt = false;

    public TableController tableController;
    public Pane tableContainer;

    public Table(String ID, int Status){
        this.ID = ID;
        this.Status = Status;
    }

    public void BuildTable(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Table.fxml"));
        try {
            //load Fxml
            tableContainer = loader.load();
            tableController = loader.getController();

            tableController.tableNumber.setText(ID);


            if(Status == EnumStatus.OCCUPIED.value){
                tableController.rectangle.setFill(Color.RED);
            }

            tableController.pane.setOnMouseClicked(e->{
                if(this.Status == EnumStatus.SELECTED.value){
                    tableController.rectangle.setFill(Color.GREEN);
                    this.Status = -1;
                }
                else if(this.Status == EnumStatus.FREE.value){
                    tableController.rectangle.setFill(Color.BLUE);
                    this.Status = 0;
                }
            });
            isBuilt = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setID(String ID){
        this.ID = ID;
        this.tableController.tableNumber.setText(ID);
    }

    public void setStatus(int Status){
        this.Status = Status;
        if(Status == EnumStatus.OCCUPIED.value){
            tableController.rectangle.setFill(Color.RED);
        }
        else if(Status == EnumStatus.FREE.value){
            tableController.rectangle.setFill(Color.GREEN);
        }
    }


    public boolean compare(Table newtable){
        return this.ID.equals(newtable.ID) && this.Status == newtable.Status;
    }
    public boolean compare(String ID, int Status){
        return this.ID.equals(ID) && this.Status == Status ;
    }


}
