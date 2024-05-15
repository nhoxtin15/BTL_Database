package database_btl.Manager.controller;

import database_btl.Backend.Sql_connector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import database_btl.Exception.EmptyInfomation;
import database_btl.Exception.PopUpMessage;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class RemoveEmployeeController {
    @FXML
    public TextField employeeSSN;

    public void removeEmployee(){
        if (employeeSSN.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;
        }
        String sql = "DELETE FROM EMPLOYEE WHERE SSN = '" + employeeSSN.getText() + "'";
        try{
            synchronized (Sql_connector.lock){
                Sql_connector.executeUpdate(sql);
            }
        }
        catch (Exception e){
            new PopUpMessage(e);
        }
    }
}
