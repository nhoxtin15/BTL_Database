package database_btl.Employee.menuArea.Area.Controller;


import javafx.scene.image.Image;
import database_btl.HelloApplication;

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
