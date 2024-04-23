package org.example.database_btl.Backend.model.controller;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.database_btl.HelloApplication;

public class VipRoomController {
    public static final Image DefaultImage = new Image(HelloApplication.class.getResource("Image/viproom.png").toString());

    @FXML
    public Rectangle rectangleVipRoom;

    @FXML
    public Text textVipRoom;

    @FXML
    public Pane paneVipRoom;

    @FXML
    public ImageView imagevipRoom;

    @FXML
    public void initialize(){
        imagevipRoom.setImage(DefaultImage);
        rectangleVipRoom.setFill(Color.GREEN);
        paneVipRoom.setOnMouseEntered(e->{
            paneVipRoom.setEffect(new DropShadow());

        });
        paneVipRoom.setOnMouseExited(e->{
            paneVipRoom.setEffect(null);

        });
    }
}
