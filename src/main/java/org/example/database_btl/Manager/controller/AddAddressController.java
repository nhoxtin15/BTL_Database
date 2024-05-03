package org.example.database_btl.Manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Exception.EmptyInfomation;
import org.example.database_btl.Exception.PopUpMessage;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class AddAddressController {
    @FXML
    public TextField employeeSSN;
    @FXML
    public TextField street_address;
    @FXML
    public TextField district;
    @FXML
    public TextField province;


    @FXML
    public void addAddress(){
        if (employeeSSN.getText().isEmpty() || street_address.getText().isEmpty() || district.getText().isEmpty() || province.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;
        }

        String sql = "INSERT INTO ADDRESS VALUES ('"
                + employeeSSN.getText() + "', '"
                + street_address.getText() + "', '"
                + district.getText() + "', '"
                + province.getText() + "')";
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
