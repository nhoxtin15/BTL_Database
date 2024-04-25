package org.example.database_btl.Backend.model.menuArea;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.controller.AreaController;
import org.example.database_btl.Backend.model.controller.MenuAndTablesController;
import org.example.database_btl.Backend.model.menuArea.Area.Area;
import org.example.database_btl.Backend.model.menuArea.Area.AreaVipRoom;
import org.example.database_btl.Backend.model.menuArea.Area.VipRoom;
import org.example.database_btl.Backend.model.menuArea.Menu.Menu;
import org.example.database_btl.Backend.Sql_connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MenuAndTables {

    public ArrayList<Area> area;
    public AreaVipRoom areaVipRoom;


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


        //Init all the variable
        this.menu = new Menu();
        this.area = new ArrayList<>();

        synchronized (Sql_connector.lock) {
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
        }


        for (Area a : area) {
            ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(a.areaContainer);
        }

        //init the vip room
        areaVipRoom = new AreaVipRoom();
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(areaVipRoom.tabAreaVipRoomContainer);

        //button at the end of the pane
        menuAndTablesController.buttonCreateReceipt.setOnAction(e -> {
            if(menuAndTablesController.checkBoxOpenMenu.isSelected())
                menuAndTablesController.tabPaneMenuAndAreas.getSelectionModel().select(1);
        });

    }


}
