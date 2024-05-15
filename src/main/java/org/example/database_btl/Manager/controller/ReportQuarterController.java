package org.example.database_btl.Manager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Manager.model.Employee;
import org.example.database_btl.Manager.model.Report;

import java.sql.ResultSet;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ReportQuarterController extends ReportController{
    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Report, String> yearColumn = new TableColumn<>("year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableReport.getColumns().add(yearColumn);
        TableColumn<Report, String> quarterColumn = new TableColumn<>("quarter");
        quarterColumn.setCellValueFactory(new PropertyValueFactory<>("quarter"));
        tableReport.getColumns().add(quarterColumn);
        TableColumn<Report, String> revenueColumn = new TableColumn<>("revenue");
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        tableReport.getColumns().add(revenueColumn);

        String sql = "{CALL revenue_by_quarter()}";
        ObservableList<Report> reports = FXCollections.observableArrayList();

        try(ResultSet rs = Sql_connector.executeQuery(sql)){
            while(rs.next()){

                Report report = new Report(rs.getString("year"), rs.getString("quarter"), null, null, rs.getString("revenue"));
                reports.add(report);
            }
            tableReport.setItems(reports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
