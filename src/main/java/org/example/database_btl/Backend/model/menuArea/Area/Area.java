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

    public AreaController areaController = null;

    public Tab areaContainer = null;

    public String sqlGetTable;
    public String getSqlGetTable;

    public Area(String name){
        this.name = name;
        //sql get table
        StringBuilder sqlTable = new StringBuilder();
        sqlTable.append("SELECT * FROM Table_book WHERE  Area_ID = '").append(name).append("'");
        this.sqlGetTable = sqlTable.toString();

        //sql get all the viproom of the area
        StringBuilder sqlVipRoom = new StringBuilder();
        sqlVipRoom.append("SELECT * FROM Vip_room WHERE Area_ID = '").append(name).append("'");
        this.getSqlGetTable = sqlVipRoom.toString();

        initArea();

    }

    public void initArea(){
        //load the area
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("FxmlModel/Area.fxml"));
        try {
            areaContainer = loader.load();
            areaController = loader.getController();
            areaController.Area.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //init the tables
        tables = new ArrayList<>();
        ResultSet rs = Sql_connector.executeQuery(sqlGetTable);
        try{
            while (rs.next()) {
                tables.add(new Table(rs.getString("Table_ID"), rs.getInt("Status")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //init the viproom
        Viprooms = new ArrayList<>();
        rs = Sql_connector.executeQuery(getSqlGetTable);
        try{
            while (rs.next()) {
                Viprooms.add(new Viproom(rs.getString("Vip_ID"), rs.getInt("Status")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        int count = 1;
        //add the tables to the area
        for (Table table : tables) {
            if(count % 5 == 0){
                areaController.tables.addRow(count / 5);
            }
            areaController.tables.add(table.tableContainer, count % 5, count / 5);
        }

        //add the viproom to the area
        for (Viproom viproom : Viprooms) {
            if(count % 5 == 0){
                areaController.tables.addRow(count / 5);
            }
            areaController.tables.add(viproom.vipRoomContainer, count % 5, count / 5);
        }
    }



}
