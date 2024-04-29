package org.example.database_btl.Backend.model.controller;


import javafx.scene.image.Image;
import org.example.database_btl.HelloApplication;

public class VipRoomController extends  TableController{

    public static Image DEFAULT_IMAGE = new Image(HelloApplication.class.getResource("Image/viproom.png").toString());


    public Image getDefaultImage(){
        return DEFAULT_IMAGE;
    }

//    @Override
//    public void initialize() {
//        super.initialize();
//    }
}
