package org.example.database_btl.Backend.model.menuArea;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.controller.AreaController;
import org.example.database_btl.Backend.model.controller.MenuAndTablesController;
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
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/MenuAndTables.fxml"));
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
        ResultSet rs = Sql_connector.executeQuery("SELECT * FROM Area");
        try{
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

        //add all the area to the tab pane, and collected
        for (Area a : area) {
            menuAndTablesController.tabPaneAreas.getTabs().add(a.areaContainer);

            vipRooms.addAll(a.Viprooms);
            System.out.println(this.vipRooms);
        }
        //load create the vip room

        int countVipRoom = 0;
        FXMLLoader loaderVipRoom = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Area.fxml"));
        try {
             Tab AreaVipRoom = loaderVipRoom.load();
             AreaController areaVipRoomController = loaderVipRoom.getController();
             for (Viproom v : vipRooms) {
                 if(countVipRoom % 5 == 0){
                        areaVipRoomController.tables.addRow(countVipRoom / 5);
                 }
                areaVipRoomController.tables.add(v.viproomContainer, countVipRoom % 5, countVipRoom / 5);

             }
                menuAndTablesController.tabPaneAreas.getTabs().add(AreaVipRoom);

        } catch (Exception e) {
            e.printStackTrace();
        }






        //button at the end of the pane
        menuAndTablesController.buttonCreateReceipt.setOnAction(e -> {
            if(menuAndTablesController.checkBoxOpenMenu.isSelected())
                menuAndTablesController.tabPaneMenuAndAreas.getSelectionModel().select(1);
        });

    }


}
