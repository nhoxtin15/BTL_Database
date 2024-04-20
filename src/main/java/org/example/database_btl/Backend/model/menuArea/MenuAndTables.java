package org.example.database_btl.Backend.model.menuArea;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.controller.MenuAndTablesController;
import org.example.database_btl.Backend.model.menuArea.Area.Area;
import org.example.database_btl.Backend.model.menuArea.Menu.Menu;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.HelloApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MenuAndTables {

    public ArrayList<Area> area;

    public Menu menu;

    public MenuAndTablesController menuAndTablesController;
    public VBox menuAndTables;


    public MenuAndTables() {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/MenuAndTables.fxml"));
        try {
            menuAndTables = loader.load();
            menuAndTablesController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.menu = new Menu();
        this.area = new ArrayList<>();

        ResultSet rs = Sql_connector.executeQuery("SELECT * FROM Area");
        try{
            ArrayList<String> areaName = new ArrayList<>();
            while (rs.next()) {
                areaName.add(rs.getString("Area_ID"));
            }
            for (String name : areaName) {
                area.add(new Area(name));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (Area a : area) {
            menuAndTablesController.areas.getTabs().add(a.areaContainer);
        }

        menuAndTablesController.buttonCreateReceipt.setOnAction(e -> {
            if(menuAndTablesController.checkBoxOpenMenu.isSelected())
                menuAndTablesController.tabPaneMenuAndAreas.getSelectionModel().select(1);
        });

    }


}
