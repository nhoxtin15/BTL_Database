package org.example.database_btl.Backend.model.menuArea.Area;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Backend.model.controller.AreaController;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Area {

    ////////////////////////////////
    //                            //
    //      Area's attribute      //
    //                            //
    ////////////////////////////////


    public ArrayList<Table> tables;
    public ArrayList<VipRoom> vipRooms;
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
        vipRooms = new ArrayList<>();
        synchronized (Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(getSqlGetTable);) {
                while (rs.next()) {
                    vipRooms.add(new VipRoom(rs.getString("Room_code"), -1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        tables= new ArrayList<>();
        vipRooms = new ArrayList<>();



        areaContainer.setOnSelectionChanged(event -> {
            if(areaContainer.isSelected()){
                this.updateArea();
            }
        });
    }

    public void updateArea(){
        ArrayList<Table> tempTableList = new ArrayList<>();
        ArrayList<VipRoom> tempVipRoomList = new ArrayList<>();
        synchronized (Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(sqlGetTable);) {
                while (rs.next()) {
                    tempTableList.add(new Table(rs.getString("Order_num"), rs.getBoolean("available") ? 1 : -1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        synchronized(Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(getSqlGetTable);) {
                while (rs.next()) {
                    tempVipRoomList.add(new VipRoom(rs.getString("Room_code"), rs.getBoolean("available") ? 1 : -1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int tempTableIterator = 0;

        for(tempTableIterator = 0 ; tempTableIterator < this.tables.size(); tempTableIterator++){
            //compare
            if(tempTableIterator >= tempTableList.size()){
                break;
            }
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
            tables.getLast().BuildTable();
        }



        for(tempTableIterator = 0 ; tempTableIterator < this.vipRooms.size(); tempTableIterator++){
            //compare
            if(this.vipRooms.get(tempTableIterator).compare(tempVipRoomList.get(tempTableIterator))){
                continue;
            }
            else{
                VipRoom vipRoomOld = this.vipRooms.get(tempTableIterator);
                VipRoom vipRoomNew = tempVipRoomList.get(tempTableIterator);

                if(!vipRoomOld.getName().equals(vipRoomNew.getName())){
                    vipRoomOld.setName(vipRoomNew.getName());
                }
                if (vipRoomOld.getStatus() != vipRoomNew.getStatus()){
                    vipRoomOld.setStatus(vipRoomNew.getStatus());
                }
            }
        }
        for(;tempTableIterator < tempVipRoomList.size(); tempTableIterator++){
            this.vipRooms.add(tempVipRoomList.get(tempTableIterator));
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
            for(VipRoom v : vipRooms){
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
