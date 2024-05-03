package org.example.database_btl.Manager.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database_btl.Manager.model.Report;
import org.example.database_btl.Manager.model.ReportMonth;

import java.time.Month;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ReportMonthController extends ReportController {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Report, String> yearColumn = new TableColumn<>("year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableReport.getColumns().add(yearColumn);

        TableColumn<Report, Month> monthColumn = new TableColumn<>("month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        tableReport.getColumns().add(monthColumn);

        TableColumn<Report, String> revenueColumn = new TableColumn<>("revenue");
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        tableReport.getColumns().add(revenueColumn);

    }
}
