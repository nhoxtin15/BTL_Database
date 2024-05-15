package database_btl.Manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import database_btl.Sql_connector;
import database_btl.Exception.EmptyInfomation;
import database_btl.Exception.PopUpMessage;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class AddPhoneController {
    @FXML
    public TextField employeeSSN;
    @FXML
    public TextField phone;

    public void addPhone(){
        if (employeeSSN.getText().isEmpty() || phone.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;
        }
        String sql = "INSERT INTO PHONE VALUES ('"
                + employeeSSN.getText() + "', '"
                + phone.getText() + "')";
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
