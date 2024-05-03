package org.example.database_btl.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Exception.PopUpMessage;
import org.example.database_btl.HelloApplication;
import org.example.database_btl.Manager.model.Employee;

import java.sql.ResultSet;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class MainManagerController {
    @FXML
    public TableView<Employee> employeeTable;

    @FXML
    public TableColumn<Employee,String> employeeSSN;
    @FXML
    public TableColumn<Employee,String> employeeSex;
    @FXML
    public TableColumn<Employee,String> employeeWork_start_date;
    @FXML
    public TableColumn<Employee,String> employeeFName;
    @FXML
    public TableColumn<Employee,String> employeeLName;
    @FXML
    public TableColumn<Employee,String> employeeSalary;
    @FXML
    public TableColumn<Employee,String> employeeBirthdate;



    public void addEmployee(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Manager/AddEmployee.fxml"));
        try{
            Scene scene = new Scene(loader.load()) ;

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (Exception e) {
            new PopUpMessage(e);
        }
    }
    public void removeEmployee(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Manager/RemoveEmployee.fxml"));
        try{
            Scene scene = new Scene(loader.load()) ;

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (Exception e) {
            new PopUpMessage(e);
        }
    }
    public void addAddress(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Manager/AddAddress.fxml"));
        try{
            Scene scene = new Scene(loader.load()) ;

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (Exception e) {
            new PopUpMessage(e);
        }
    }
    public void addPhone(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Manager/AddPhone.fxml"));
        try{
            Scene scene = new Scene(loader.load()) ;

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (Exception e) {
            new PopUpMessage(e);
        }
    }

    public void initialize(){
        employeeSSN.setCellValueFactory(new PropertyValueFactory<>("employeeSSN"));
        employeeSex.setCellValueFactory(new PropertyValueFactory<>("employeeSex"));
        employeeWork_start_date.setCellValueFactory(new PropertyValueFactory<>("employeeWork_start_date"));
        employeeFName.setCellValueFactory(new PropertyValueFactory<>("employeeFName"));
        employeeLName.setCellValueFactory(new PropertyValueFactory<>("employeeLName"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        employeeBirthdate.setCellValueFactory(new PropertyValueFactory<>("employeeBirthdate"));

        String sql = "SELECT * FROM EMPLOYEE";

        synchronized (Sql_connector.lock){
            ObservableList<Employee> employees = FXCollections.observableArrayList();
            try(ResultSet rs = Sql_connector.executeQuery(sql)) {
                while (rs.next()) {
                    String employeeSSN = rs.getString("SSN");
                    String employeeSex = (rs.getString("Sex").equals("M") )?"Male"  :"Female";
                    String employeeWork_start_date = rs.getString("Work_start_date");
                    String employeeFName = rs.getString("FName");
                    String employeeLName = rs.getString("LName");
                    String employeeSalary = rs.getString("Salary");
                    String employeeBirthdate = rs.getString("Birthdate");
                    employees.add(new Employee(employeeSSN,employeeSex,employeeWork_start_date,employeeFName,employeeLName,employeeSalary,employeeBirthdate));
                }
            } catch (Exception e) {
                new PopUpMessage(e);
            }
            employeeTable.setItems(employees);
        }
    }

}
