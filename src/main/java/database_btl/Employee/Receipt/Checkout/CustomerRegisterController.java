package database_btl.Employee.Receipt.Checkout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import database_btl.Sql_connector;
import database_btl.Exception.EmptyInfomation;
import database_btl.Exception.PopUpMessage;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class CustomerRegisterController {

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
        //customerSex = new ChoiceBox<>();
        customerSex.getItems().add("Male");
        customerSex.getItems().add("Female");


    }


    public void registerCustomer(ActionEvent event){
        if(customerFName.getText().isEmpty() || customerLName.getText().isEmpty() || customerSex.getValue() == null || customerSSN.getText().isEmpty() || customerDOB.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;
        }

        String sqlInsertCustomer = "INSERT INTO Customer (SSN,Sex,FName,LName,DOB) Values (";
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
                return;
            }
        }


        if(!this.customerPhones.getText().isEmpty()){
            String[] phones = customerPhones.getText().split("\n");
            String Cus_ID ;
            String sqlGetCus_ID = "SELECT * FROM Customer WHERE SSN = '"+customerSSN.getText()+"'";

            synchronized (Sql_connector.lock){
                try(ResultSet rs = Sql_connector.executeQuery(sqlGetCus_ID))
                {
                    rs.next();
                    Cus_ID = rs.getString("Cus_ID");
                }
                catch (Exception e){
                    new PopUpMessage(e);
                    return;
                }
            }
            for (String phone : phones){
                String sqlInsertPhone = "INSERT INTO Customer_Phone_number (Cus_ID,Phone_number) Values (";
                sqlInsertPhone += "'"+Cus_ID+"',";
                sqlInsertPhone += "'"+phone+"')";
                synchronized (Sql_connector.lock){
                    try{
                        Sql_connector.executeUpdate(sqlInsertPhone);
                    }
                    catch (Exception e){
                        new PopUpMessage(e);
                        return;
                    }
                }
            }
        }




        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void addPhone(){
        if(customerPhone.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;

        }
        customerPhones.appendText(customerPhone.getText()+"\n");
        customerPhone.clear();
    }



}
