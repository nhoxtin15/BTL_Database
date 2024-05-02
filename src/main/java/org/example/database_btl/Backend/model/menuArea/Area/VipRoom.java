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

public class VipRoom extends Table{


    @Override
    public String getResource(){
        return "VipRoom.fxml";
    }

    public VipRoom(String name, int Status){
        super(name, Status);
    }


}
