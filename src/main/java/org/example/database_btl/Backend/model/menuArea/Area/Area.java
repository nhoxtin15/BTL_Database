package org.example.database_btl.Backend.model.menuArea.Area;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Backend.model.controller.AreaController;

import org.example.database_btl.HelloApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Area {
    public ArrayList<Table> tables;
    public ArrayList<Viproom> Viprooms;
    public String name;

    public AreaController areaController;

    public Tab areaContainer;


    public Area(String name){
        this.name = name;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Table_book WHERE  Area_ID = '").append(name).append("'");


        tables = new ArrayList<>();

        int count = 1;
        // get table and build the structure
        try(ResultSet rs = Sql_connector.executeQuery(sql.toString());) {
            while (rs.next()){
                tables.add(new Table(""+(count++),-1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder sqlVipRoom = new StringBuilder();
        sqlVipRoom.append("SELECT * FROM Vip_room WHERE Area_ID = '").append(name).append("'");

        try( ResultSet rsVipRoom = Sql_connector.executeQuery(sqlVipRoom.toString());){

            Viprooms = new ArrayList<>();

            while (rsVipRoom.next()){
                System.out.println(rsVipRoom.getString("Room_code"));
                Viprooms.add(new Viproom(rsVipRoom.getString("Room_code")));
            }

        }
        catch (Exception e){

        }


        //display the area
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Area.fxml"));

            //get fxml
            areaContainer = loader.load();
            areaController = loader.getController();

            areaController.Area.setText(name);
            count = 0;
            for (Table table : tables){
                if (count % 5 == 0){
                    areaController.tables.addRow(count / 5);
                }
                areaController.tables.add(table.tableContainer, count % 5, count / 5);
                count++;
            }

            for (Viproom viproom : Viprooms){
                if (count % 5 == 0){
                    areaController.tables.addRow(count / 5);
                }
                areaController.tables.add(viproom.viproomContainer, count % 5, count / 5);
                count++;
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }







    }

}
