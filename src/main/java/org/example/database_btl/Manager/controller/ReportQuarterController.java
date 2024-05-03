package org.example.database_btl.Manager.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database_btl.Manager.model.Report;

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
    }
}
