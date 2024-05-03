package org.example.database_btl.Manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.database_btl.Exception.EmptyInfomation;
import org.example.database_btl.Exception.PopUpMessage;

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
            synchronized (org.example.database_btl.Backend.Sql_connector.lock){
                org.example.database_btl.Backend.Sql_connector.executeUpdate(sql);
            }
        }
        catch (Exception e){
            new PopUpMessage(e);
        }
    }
}
