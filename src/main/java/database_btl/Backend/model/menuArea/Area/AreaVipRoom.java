package database_btl.Backend.model.menuArea.Area;
/**
 * Description: Special Area for all the vip rooms$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import database_btl.Backend.Sql_connector;
import database_btl.Backend.model.controller.AreaVipRoomController;
import database_btl.Exception.PopUpMessage;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AreaVipRoom extends Area{
    public AreaVipRoom() {
        super("VipRooms");
        this.sqlGetVipRoom = "SELECT * FROM Vip_room";
    }

}
