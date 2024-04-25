package org.example.database_btl.Backend.model.menuArea.Area;


import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Backend.model.Restaurant;
import org.example.database_btl.Backend.model.controller.AreaController;

import org.example.database_btl.HelloApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Area {

    ////////////////////////////////
    //                            //
    //      Area's attribute      //
    //                            //
    ////////////////////////////////


    public ArrayList<Table> tables;
    public ArrayList<Viproom> VipRooms;
    public String name;



    ////////////////////////////////
    //                            //
    //        Area's FXML         //
    //                            //
    ////////////////////////////////



    public AreaController areaController = null;

    public Tab areaContainer = null;

    ////////////////////////////////
    //                            //
    //    Area's SQL statement    //
    //                            //
    ////////////////////////////////

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Area.fxml"));
        try {
            areaContainer = loader.load();
            areaController = loader.getController();
            areaController.Area.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //init the vip room
        VipRooms = new ArrayList<>();
        synchronized (Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(getSqlGetTable);) {
                while (rs.next()) {
                    VipRooms.add(new Viproom(rs.getString("Room_code"), -1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        tables= new ArrayList<>();
        VipRooms = new ArrayList<>();



        areaContainer.setOnSelectionChanged(event -> {
            if(areaContainer.isSelected()){
                this.updateArea(event);
            }
        });
    }

    public void updateArea(Event event){
        ArrayList<Table> tempTableList = new ArrayList<>();
        ArrayList<Viproom> tempVipRoomList = new ArrayList<>();
        synchronized (Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(sqlGetTable);) {
                while (rs.next()) {
                    tempTableList.add(new Table(rs.getString("Order_num"), rs.getBoolean("available") ? 1 : -1));
                    tempTableList.getLast().BuildTable();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        synchronized(Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(getSqlGetTable);) {
                while (rs.next()) {
                    tempVipRoomList.add(new Viproom(rs.getString("Room_code"), rs.getBoolean("available") ? 1 : -1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int tempTableIterator = 0;

        for(tempTableIterator = 0 ; tempTableIterator < this.tables.size(); tempTableIterator++){
            //compare
            if(this.tables.get(tempTableIterator).compare(tempTableList.get(tempTableIterator))){
                continue;
            }
            else{
                Table tableOld = this.tables.get(tempTableIterator);
                Table tableNew = tempTableList.get(tempTableIterator);

                if(!tableOld.ID.equals(tableNew.ID)){
                    tableOld.setID(tableNew.ID);
                    tableOld.setStatus(tableNew.Status);
                }
                if (tableOld.Status != tableNew.Status){
                    tableOld.setStatus(tableNew.Status);
                }
            }
        }
        for(;tempTableIterator < tempTableList.size(); tempTableIterator++){
            this.tables.add(tempTableList.get(tempTableIterator));
        }



        for(tempTableIterator = 0 ; tempTableIterator < this.VipRooms.size(); tempTableIterator++){
            //compare
            if(this.VipRooms.get(tempTableIterator).compare(tempVipRoomList.get(tempTableIterator))){
                continue;
            }
            else{
                Viproom vipRoomOld = this.VipRooms.get(tempTableIterator);
                Viproom vipRoomNew = tempVipRoomList.get(tempTableIterator);

                if(!vipRoomOld.name.equals(vipRoomNew.name)){
                    vipRoomOld.name = vipRoomNew.name;
                }
                if (vipRoomOld.isBooked != vipRoomNew.isBooked){
                    vipRoomOld.isBooked = vipRoomNew.isBooked;
                }
            }
        }
        for(;tempTableIterator < tempVipRoomList.size(); tempTableIterator++){
            this.VipRooms.add(tempVipRoomList.get(tempTableIterator));
        }
        showTables();
    }

    public void showTables(){
        //load the area
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Area.fxml"));
        try {
            Tab areaContainer1 = loader.load();
            AreaController areaController1 = loader.getController();
            areaController1.Area.setText(name);
            int count = 0;
            for (Table t: tables){
                if(count % 5 == 0){
                    areaController1.tables.addRow(count / 5);
                }
                areaController1.tables.add(t.tableContainer, count % 5, count / 5);
                count++;
            }
            for(Viproom v : VipRooms){
                if(count % 5 == 0){
                    areaController1.tables.addRow(count / 5);
                }
                areaController1.tables.add(v.vipRoomContainer, count % 5, count / 5);
                count++;
            }
            this.areaContainer.setContent(areaContainer1.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
