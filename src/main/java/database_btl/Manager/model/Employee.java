package database_btl.Manager.model;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class Employee {
    public String employeeSSN;
    public String employeeSex;

    public String employeeWork_start_date;

    public String employeeFName;

    public String employeeLName;

    public String employeeSalary;

    public String employeeBirthdate;

    public Employee(String employeeSSN, String employeeSex, String employeeWork_start_date, String employeeFName, String employeeLName, String employeeSalary, String employeeBirthdate) {
        this.employeeSSN = employeeSSN;
        this.employeeSex = employeeSex;
        this.employeeWork_start_date = employeeWork_start_date;
        this.employeeFName = employeeFName;
        this.employeeLName = employeeLName;
        this.employeeSalary = employeeSalary;
        this.employeeBirthdate = employeeBirthdate;
    }

    public String getEmployeeSSN() {
        return employeeSSN;
    }
    public String getEmployeeSex() {
        return employeeSex;
    }
    public String getEmployeeWork_start_date() {
        return employeeWork_start_date;
    }
    public String getEmployeeFName() {
        return employeeFName;
    }
    public String getEmployeeLName() {
        return employeeLName;
    }
    public String getEmployeeSalary() {
        return employeeSalary;
    }
    public String getEmployeeBirthdate() {
        return employeeBirthdate;
    }



}
