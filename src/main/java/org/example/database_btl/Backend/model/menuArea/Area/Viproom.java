package org.example.database_btl.Backend.model.menuArea.Area;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.database_btl.Backend.model.controller.VipRoomController;
import org.example.database_btl.HelloApplication;

public class VipRoom{

    public String name;
    public Pane vipRoomContainer;

    public VipRoomController vipRoomController;

    public int isBooked;



    public VipRoom(String name, int Status){
        isBooked = -1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VipRoom.fxml"));
        try {
            vipRoomContainer = loader.load();
            vipRoomController = loader.getController();
            vipRoomController.textVipRoom.setText(name);
            vipRoomController.paneVipRoom.setOnMouseClicked(e->{
                if(this.isBooked == 0){
                    vipRoomController.rectangleVipRoom.setFill(Color.GREEN);
                    this.isBooked = -1;
                }
                else if(this.isBooked == -1){
                    vipRoomController.rectangleVipRoom.setFill(Color.BLUE);
                    this.isBooked = 0;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.name = name;

    }

    public boolean compare(VipRoom viproom) {
        return this.name.equals(viproom.name) && this.isBooked == viproom.isBooked;
    }
}
