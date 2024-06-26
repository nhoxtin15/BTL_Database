package database_btl.Employee.menuArea;

/**
 * Description: Store Menu and Area (tables and Viprooms)$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import database_btl.Employee.Receipt.AllReceipt;
import database_btl.Employee.menuArea.Area.Area;
import database_btl.Employee.menuArea.Area.AreaVipRoom;
import database_btl.Employee.menuArea.Menu.Menu;
import database_btl.Sql_connector;
import database_btl.Exception.PopUpMessage;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MenuAndTables {

    public ArrayList<Area> area;



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

            for (Area a: area){
                if(a.name.equals(areaName)){
                    try {
                        AllReceipt.addReceipt(a.getAreaReceipt());
                    } catch (Exception exception) {
                        new PopUpMessage(exception);
                    }
                    break;
                }
            }

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
        //init vip room
        area.add(new AreaVipRoom());

        for (Area a : area) {
            ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getFirst().getContent()).getTabs().add(a.areaContainer);
        }

    }

    public void initMenus(){
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getLast().getContent()).getTabs().add(menu.foodAndDrink.CategoryContainer);
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getLast().getContent()).getTabs().add(menu.combo.CategoryContainer);
        ((TabPane)menuAndTablesController.tabPaneMenuAndAreas.getTabs().getLast().getContent()).getTabs().add(menu.soup_base.CategoryContainer);
    }




}
