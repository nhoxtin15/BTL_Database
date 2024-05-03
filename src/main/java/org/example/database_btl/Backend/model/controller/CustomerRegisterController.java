package org.example.database_btl.Backend.model.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Exception.EmptyInfomation;
import org.example.database_btl.Exception.PopUpMessage;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class CustomerRegisterController {
    @FXML
    public TextField customerID;
    @FXML
    public TextField customerFName;
    @FXML
    public TextField customerLName;

    @FXML
    public ChoiceBox<String> customerSex;
    @FXML
    public TextField customerSSN;

    @FXML
    public TextField customerDOB;

    @FXML
    public TextField customerPhone;
    @FXML
    public TextArea customerPhones;







    public void initialize() {
        customerSex.getItems().add("Male");
        customerSex.getItems().add("Female");
    }


    public void registerCustomer(){
        if(customerFName.getText().isEmpty() || customerLName.getText().isEmpty() || customerSex.getValue() == null || customerSSN.getText().isEmpty() || customerDOB.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;
        }
        String sqlInsertCustomer = "INSERT INTO Customer (Cus_ID,SSN,Sex,FName,LName,DOB) Values (";
        sqlInsertCustomer += "'"+1234+"',";
        sqlInsertCustomer += "'"+customerSSN.getText()+"',";
        sqlInsertCustomer += "'"+customerSex.getValue().charAt(0)+"',";
        sqlInsertCustomer += "'"+customerFName.getText()+"',";
        sqlInsertCustomer += "'"+customerLName.getText()+"',";
        sqlInsertCustomer += "'"+customerDOB.getText()+"')";

        synchronized (Sql_connector.lock){
            try{
                Sql_connector.executeUpdate(sqlInsertCustomer);
            }
            catch (Exception e){
                new PopUpMessage(e);
            }

        }


    }





}
