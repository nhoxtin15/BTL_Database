package org.example.database_btl.Backend.model.menuArea.Area;
/**
 * Description: Special Area for all the vip rooms$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Backend.model.controller.AreaVipRoomController;
import org.example.database_btl.Exception.PopUpMessage;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AreaVipRoom{

    public ArrayList<VipRoom> VipRooms = new ArrayList<>();


    public static final String sqlVipRoom = "SELECT * FROM vip_room";


    public AreaVipRoomController areaVipRoomController;
    public Tab tabAreaVipRoomContainer;

    public AreaVipRoom() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("VipRoomArea.fxml"));
        try{
            tabAreaVipRoomContainer = loader.load();
            areaVipRoomController = loader.getController();
            this.tabAreaVipRoomContainer.setText("Vip Rooms");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabAreaVipRoomContainer.setOnSelectionChanged(event -> {
            if(tabAreaVipRoomContainer.isSelected()){
                updateVipRooms();
            }

        });
    }



    public void updateVipRooms(){
        ArrayList<VipRoom> tempVipRoomsList = new ArrayList<>();
        synchronized (Sql_connector.lock){
            try(ResultSet resultSet = Sql_connector.executeQuery(sqlVipRoom)){
                while (resultSet.next()){
                    String name = resultSet.getString("Room_code");
                    int status = resultSet.getBoolean("available")?1:-1;
                    VipRoom vipRoom = new VipRoom(name, status);
                    tempVipRoomsList.add(vipRoom);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < VipRooms.size(); i++){
            if(i < tempVipRoomsList.size()){
                if(!VipRooms.get(i).compare(tempVipRoomsList.get(i))){
                    VipRooms.get(i).update(tempVipRoomsList.get(i));
                }
            }
        }
        for (int i = VipRooms.size() ; i< tempVipRoomsList.size();i++){
            VipRooms.add(tempVipRoomsList.get(i));
        }

        showVipRooms();

    }
    public void showVipRooms(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("VipRoomArea.fxml"));
        try{
            this.tabAreaVipRoomContainer.setText("Vip Rooms");
            Tab tempAreaVipRoomContainer = loader.load();
            AreaVipRoomController tempAreaVipRoomController = loader.getController();

            for(int i = 0; i < VipRooms.size(); i++){
                if(i%5==0) tempAreaVipRoomController.vipRooms.addRow(i/5);
                tempAreaVipRoomController.vipRooms.add(VipRooms.get(i).vipRoomContainer, i%5,i/5);
            }
            this.areaVipRoomController.AreaVipRoom.setContent(tempAreaVipRoomContainer.getContent());
        }
        catch (Exception e) {
            new PopUpMessage(e);
        }

    }


}
