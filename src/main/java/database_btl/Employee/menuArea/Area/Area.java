package database_btl.Employee.menuArea.Area;

/**
 * Description: Area $
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import database_btl.Sql_connector;
import database_btl.Employee.Receipt.AreaReceipt;
import database_btl.Employee.menuArea.Area.Controller.AreaController;
import database_btl.Exception.NoTableException;
import database_btl.Exception.PopUpMessage;

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
    public String sqlGetVipRoom;


    public Area(String name){
        this.name = name;
        //sql get table
        StringBuilder sqlTable = new StringBuilder();
        sqlTable.append("SELECT * FROM Table_book WHERE  Area_ID = '").append(name).append("'");
        this.sqlGetTable = sqlTable.toString();

        //sql get all the viproom of the area
        StringBuilder sqlVipRoom = new StringBuilder();
        sqlVipRoom.append("SELECT * FROM Vip_room WHERE Area_ID = '").append(name).append("'");
        this.sqlGetVipRoom = sqlVipRoom.toString();
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
        tables= new ArrayList<>();
        vipRooms = new ArrayList<>();

        //Auto update
        areaContainer.setOnSelectionChanged(event -> {
            if(areaContainer.isSelected()){
                this.updateArea();
            }
        });
    }


    public void updateTable(){
        ArrayList<Table> tempTableList = new ArrayList<>();
        synchronized (Sql_connector.lock) {
            try (ResultSet rs = Sql_connector.executeQuery(sqlGetTable);) {
                while (rs.next()) {
                    tempTableList.add(new Table(rs.getString("Order_num"), rs.getBoolean("available") ? -1 : 1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        for (int i = 0 ; i < this.tables.size() && i < tempTableList.size(); i++){
            if(!this.tables.get(i).compare(tempTableList.get(i))){
                Table tableNew = tempTableList.get(i);
                this.tables.get(i).update(tableNew);
            }
        }
        for(int i = this.tables.size(); i < tempTableList.size(); i++){
            this.tables.add(tempTableList.get(i));
            tables.get(i).BuildTable();
        }
    }

    public void  updateVipRoom(){
        ArrayList<VipRoom> tempVipRoomList = new ArrayList<>();
        synchronized (Sql_connector.lock){
            try(ResultSet rs = Sql_connector.executeQuery(sqlGetVipRoom)){
                while (rs.next()){
                    tempVipRoomList.add(new VipRoom(rs.getString("Room_code"), rs.getBoolean("available")?-1:1));
                }
            } catch (Exception e) {
                new PopUpMessage(e);
            }
        }

        for(int i = 0; i < vipRooms.size() && i < tempVipRoomList.size(); i++){
            if(!vipRooms.get(i).compare(tempVipRoomList.get(i))){
                vipRooms.get(i).update(tempVipRoomList.get(i));
            }
        }
        for(int i = vipRooms.size(); i < tempVipRoomList.size(); i++){
            vipRooms.add(tempVipRoomList.get(i));
            vipRooms.get(i).BuildTable();
        }

    }

    public void updateArea(){
        updateTable();
        updateVipRoom();

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
                areaController1.tables.add(v.tableContainer, count % 5, count / 5);
                count++;
            }

            this.areaContainer.setContent(areaContainer1.getContent());
        } catch (Exception e) {
            new PopUpMessage(e);
        }

    }


    public AreaReceipt getAreaReceipt() throws Exception{
        ArrayList<String> tableList = new ArrayList<>();
        for(Table t : tables){
            if(t.getStatus() == Table.EnumStatus.SELECTED.getValue()){
                tableList.add(t.getID());
                t.setStatus(Table.EnumStatus.OCCUPIED.getValue());
                String sqlUpdateTable = "UPDATE Table_book SET available = 0 WHERE Order_num = '" + t.getID() + "'" +"and Area_ID = '" + name + "'";
                synchronized (Sql_connector.lock){
                    Sql_connector.executeUpdate(sqlUpdateTable);
                }
            }
        }
        for(VipRoom v : vipRooms){
            if(v.getStatus() == VipRoom.EnumStatus.SELECTED.getValue()){
                tableList.add(v.getID());
                v.setStatus(VipRoom.EnumStatus.OCCUPIED.getValue());
                String sqlUpdateVipRoom = "UPDATE Vip_room SET available = 0 WHERE Room_code = '" + v.getID();
                synchronized (Sql_connector.lock){
                    Sql_connector.executeUpdate(sqlUpdateVipRoom);
                }
            }
        }

        if(tableList.isEmpty()){
            throw new NoTableException();
        }
        return new AreaReceipt(name,tableList);
    }





}
