package org.example.database_btl.Backend.model.menuArea;

/**
 * Description: Store Menu and Area (tables and Viprooms)$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.Receipt.AllReceipt;
import org.example.database_btl.Backend.model.Receipt.AreaReceipt;
import org.example.database_btl.Backend.model.Receipt.Receipt;
import org.example.database_btl.Backend.model.Restaurant;
import org.example.database_btl.Backend.model.controller.AreaController;
import org.example.database_btl.Backend.model.controller.MenuAndTablesController;
import org.example.database_btl.Backend.model.menuArea.Area.Area;
import org.example.database_btl.Backend.model.menuArea.Area.AreaVipRoom;
import org.example.database_btl.Backend.model.menuArea.Area.VipRoom;
import org.example.database_btl.Backend.model.menuArea.Menu.Menu;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Exception.PopUpMessage;

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

        this.area = new ArrayList<>();
        initArea();
        this.menu = new Menu();
        initMenus();

        //button at the end of the pane
        menuAndTablesController.buttonCreateReceipt.setOnAction(e -> {
            //loop through and add table
            String areaName = menuAndTablesController.tabPaneAreas.getSelectionModel().getSelectedItem().getText();
            ArrayList<String> tableList = new ArrayList<>();
            if(areaName.equals("Vip Rooms") ){
                tableList = areaVipRoom.getVipRoomName();
            }
            else {
                for (Area a : area) {
                    if (a.name.equals(areaName)) {
                        tableList = a.getBookTableList();
                        break;
                    }
                }
            }

            AreaReceipt areaReceipt = new AreaReceipt(areaName, tableList);


            AllReceipt.addReceipt(areaName, areaReceipt);

            if(menuAndTablesController.checkBoxOpenMenu.isSelected())
                menuAndTablesController.tabPaneMenuAndAreas.getSelectionModel().select(1);

        });


    }

    public void initArea(){
        synchronized (Sql_connector.lock) {
            //get all the area
            try(ResultSet rs = Sql_connector.executeQuery("SELECT * FROM Area");){
                ArrayList<String> areaName = new ArrayList<>();
                while (rs.next()) {
                    areaName.add(rs.getString("Area_ID"));
                    AllReceipt.receiptMap.put(rs.getString("Area_ID"), 1);
                }
                //for each area
                // init the new area
                for (String name : areaName) {
                    area.add(new Area(name));
                }
            }
            catch (Exception e) {
                new PopUpMessage(e);
            }
        }


        for (Area a : area) {
            ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(a.areaContainer);
        }


        //init the vip room
        areaVipRoom = new AreaVipRoom();
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(areaVipRoom.tabAreaVipRoomContainer);


    }

    public void initMenus(){
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getLast().getContent()).getTabs().add(menu.foodAndDrink.CategoryContainer);
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getLast().getContent()).getTabs().add(menu.combo.CategoryContainer);
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getLast().getContent()).getTabs().add(menu.soup_base.CategoryContainer);
    }




}
