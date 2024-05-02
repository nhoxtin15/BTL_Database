package org.example.database_btl.Backend.model.menuArea.Area;
/**
 * Description: Storing table$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.model.controller.TableController;
import org.example.database_btl.Exception.PopUpMessage;
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

    private String ID;
    private int Status;

    private boolean isBuilt = false;

    public TableController tableController;
    public Pane tableContainer;

    public Table(String ID, int Status){
        this.ID = ID;
        this.Status = Status;
    }


    public String getResource(){
        return "Table.fxml";
    }
    public void BuildTable(){
        if(isBuilt) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(getResource()));
        try {
            //load Fxml
            tableContainer = loader.load();
            tableController = loader.getController();

            tableController.tableName.setText(ID);


            if(Status == EnumStatus.OCCUPIED.value){
                tableController.rectangle.setFill(Color.RED);
            }

            tableController.paneContainer.setOnMouseClicked(e->{
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
            new PopUpMessage(e);
        }
    }

    public void setID(String ID){
        this.ID = ID;
        this.tableController.tableName.setText(ID);
    }
    public String getID(){
        return this.ID;
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
    public int getStatus(){
        return this.Status;
    }


    public boolean compare(Table newtable){
        return this.ID.equals(newtable.ID) && this.Status == newtable.Status;
    }
    public boolean compare(String ID, int Status){
        return this.ID.equals(ID) && this.Status == Status;
    }

    public void update(Table newTable){
        if(!this.ID.equals(newTable.ID)){
            this.setID(newTable.ID);
            this.setStatus(newTable.Status);
        }
        if (this.Status != newTable.Status){
            this.setStatus(newTable.Status);
        }
    }
}
