package org.example.database_btl.Backend.model;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Backend.model.Controller.AreaController;
import org.example.database_btl.HelloApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Area {
    public ArrayList<Table> tables;
    public ArrayList<Viproom> Viprooms;
    public String name;

    public AreaController areaController;

    public Tab AreaCointainer;


    public Area(String name){
        this.name = name;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Table_book WHERE  Area_ID = '").append(name).append("'");
        ResultSet rs = Sql_connector.executeQuery(sql.toString());

        tables = new ArrayList<>();


        // get table and build the structure
        try {
            while (rs.next()){
                tables.add(new Table(rs.getString("Table_ID"),false));
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        //display the area
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Area.fxml"));

            AreaCointainer = loader.load();
            areaController = loader.getController();

            areaController.Area.setText(name);
            int count = 0;
            for (Table table : tables){
                if (count % 5 == 0){
                    areaController.tables.addRow(count / 5);
                }
                areaController.tables.add(table.tableContainer, count % 5, count / 5);
                count++;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }







    }

}
