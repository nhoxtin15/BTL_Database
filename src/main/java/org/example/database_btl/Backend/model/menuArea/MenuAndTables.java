package org.example.database_btl.Backend.model.menuArea;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.controller.AreaController;
import org.example.database_btl.Backend.model.controller.MenuAndTablesController;
import org.example.database_btl.Backend.model.controller.VipRoomController;
import org.example.database_btl.Backend.model.menuArea.Area.Area;
import org.example.database_btl.Backend.model.menuArea.Area.Viproom;
import org.example.database_btl.Backend.model.menuArea.Menu.Menu;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.HelloApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MenuAndTables {

    public ArrayList<Area> area;

    public ArrayList<Viproom> vipRooms;

    public Menu menu;

    public MenuAndTablesController menuAndTablesController;
    public VBox menuAndTables;


    public MenuAndTables() {
        //load the menu and tables
            // the pane that contains the menu and the tables

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuAndTables.fxml"));
        try {
            menuAndTables = loader.load();
            menuAndTablesController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Init all the varaibel
        this.menu = new Menu();
        this.area = new ArrayList<>();
        this.vipRooms = new ArrayList<>();


        //get all the area
        try(ResultSet rs = Sql_connector.executeQuery("SELECT * FROM Area");){
            ArrayList<String> areaName = new ArrayList<>();
            while (rs.next()) {
                areaName.add(rs.getString("Area_ID"));
            }
            //for each area
                // init the new area
            for (String name : areaName) {
                area.add(new Area(name));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (Area a : area) {
            ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(a.areaContainer);
            this.vipRooms.addAll(a.VipRooms);
        }


        FXMLLoader VipRoomFxmlLoader = new FXMLLoader(getClass().getResource("Area/Area.fxml"));
        try{
            Tab VipRoomContainer = VipRoomFxmlLoader.load();
            AreaController controller = VipRoomFxmlLoader.getController();
            controller.Area.setText("Vip rooms");
            int countViproom =1;
            for(Viproom v : this.vipRooms){
                if(countViproom % 5 == 0){
                    controller.tables.addRow(countViproom / 5);
                }
                controller.tables.add(v.vipRoomContainer,countViproom % 5, countViproom / 5);
                countViproom++;
            }
            ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(VipRoomContainer);

        }
        catch (Exception e){
            e.printStackTrace();
        }







        //button at the end of the pane
        menuAndTablesController.buttonCreateReceipt.setOnAction(e -> {
            if(menuAndTablesController.checkBoxOpenMenu.isSelected())
                menuAndTablesController.tabPaneMenuAndAreas.getSelectionModel().select(1);
        });

    }


}
