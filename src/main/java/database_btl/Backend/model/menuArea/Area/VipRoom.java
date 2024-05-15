package database_btl.Backend.model.menuArea.Area;

/**
 * Description: Store Vip room $
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

public class VipRoom extends Table{


    @Override
    public String getResource(){
        return "VipRoom.fxml";
    }

    public VipRoom(String name, int Status){
        super(name, Status);
    }


}
