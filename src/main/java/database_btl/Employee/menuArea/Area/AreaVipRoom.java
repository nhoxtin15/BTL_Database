package database_btl.Employee.menuArea.Area;
/**
 * Description: Special Area for all the vip rooms$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

public class AreaVipRoom extends Area{
    public AreaVipRoom() {
        super("VipRooms");
        this.sqlGetVipRoom = "SELECT * FROM Vip_room";
    }

}
