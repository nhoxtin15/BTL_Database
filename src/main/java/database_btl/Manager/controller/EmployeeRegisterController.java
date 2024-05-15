package database_btl.Manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import database_btl.Sql_connector;
import database_btl.Exception.EmptyInfomation;
import database_btl.Exception.PopUpMessage;


/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class EmployeeRegisterController {
    @FXML
    public TextField employeeSSN;
    @FXML
    public ChoiceBox<String> employeeSex;
    @FXML
    public TextField employeeWork_start_date;
    @FXML
    public TextField employeeFName;
    @FXML
    public TextField employeeLName;
    @FXML
    public TextField employeeSalary;
    @FXML
    public TextField employeeBirthdate;

    public void registerEmployee(){
        if (employeeSSN.getText().isEmpty() || employeeSex.getValue().isEmpty() || employeeWork_start_date.getText().isEmpty() || employeeFName.getText().isEmpty() || employeeLName.getText().isEmpty() || employeeSalary.getText().isEmpty() || employeeBirthdate.getText().isEmpty()){
            new PopUpMessage(new EmptyInfomation());
            return;
        }

        String sql = "INSERT INTO EMPLOYEE VALUES ('"
                + employeeSSN.getText() + "', '"
                + employeeSex.getValue() + "', '"
                + employeeWork_start_date.getText() + "', '"
                + employeeFName.getText() + "', '"
                + employeeLName.getText() + "', "
                + employeeSalary.getText() + ", '"
                + employeeBirthdate.getText() + "')";

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
