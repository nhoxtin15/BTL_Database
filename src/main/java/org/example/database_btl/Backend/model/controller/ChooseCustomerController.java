package org.example.database_btl.Backend.model.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.database_btl.Backend.Sql_connector;

import java.sql.ResultSet;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ChooseCustomerController {
    public String customerID ="";
    public String customerName="";
    public String customerPoint="";


    @FXML
    public TextField SSNToFind;
    @FXML
    public TextField PhoneToFind;

    @FXML
    public TextField CustomerFound;
    @FXML
    public TextField PointFound;


    public void findCustomer(){
        synchronized (Sql_connector.lock){
            String sql = "SELECT * FROM CUSTOMER c, Customer_Phone_number cpn WHERE  c.Cus_ID = cpn.Cus_ID and (SSN = '"+SSNToFind.getText()+"' OR Phone_number = '"+PhoneToFind.getText()+"')";
            try(ResultSet rs = Sql_connector.executeQuery(sql)) {
                if (rs.next()) {
                    customerID = rs.getString("Cus_ID");
                    customerName = rs.getString("LName") +" "+ rs.getString("FName");
                    customerPoint = rs.getString("acc_point");
                    CustomerFound.setText(customerName);
                    PointFound.setText(customerPoint);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void chooseCustomer(ActionEvent event){
        //close the tab
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancel(ActionEvent event){
        customerID ="";
        customerName="";
        customerPoint="";
        //close the tab
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }






}
