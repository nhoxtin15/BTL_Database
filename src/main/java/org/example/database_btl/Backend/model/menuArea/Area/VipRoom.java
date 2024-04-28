package org.example.database_btl.Backend.model.menuArea.Area;

/**
 * Description: Store Vip room $
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.model.controller.VipRoomController;

public class VipRoom {

    private String name;


    public Pane vipRoomContainer;

    public VipRoomController vipRoomController;


    private int status;



    public VipRoom(String name, int Status){
        this.status = Status;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VipRoom.fxml"));
        try {
            vipRoomContainer = loader.load();
            vipRoomController = loader.getController();
            vipRoomController.textVipRoom.setText(name);
            vipRoomController.paneVipRoom.setOnMouseClicked(e->{
                if(this.status == 0){
                    vipRoomController.rectangleVipRoom.setFill(Color.GREEN);
                    this.status = -1;
                }
                else if(this.status == -1){
                    vipRoomController.rectangleVipRoom.setFill(Color.BLUE);
                    this.status = 0;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.name = name;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        this.vipRoomController.textVipRoom.setText(name);
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        if(status == Table.EnumStatus.FREE.getValue()){
            vipRoomController.rectangleVipRoom.setFill(Color.GREEN);
        }
        else if(status == Table.EnumStatus.OCCUPIED.getValue()){
            vipRoomController.rectangleVipRoom.setFill(Color.RED);
        }
    }

    public boolean compare(VipRoom viproom) {
        return this.name.equals(viproom.name) && this.status == viproom.status;
    }

    public void update(VipRoom viproom){
        if(!this.name.equals(viproom.name) )
            this.setName(viproom.getName());
        if(this.status != viproom.getStatus())
            this.setStatus(viproom.getStatus());
    }

}
