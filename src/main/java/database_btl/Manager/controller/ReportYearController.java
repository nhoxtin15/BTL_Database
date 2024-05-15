package database_btl.Manager.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import database_btl.Manager.model.Report;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ReportYearController extends ReportController{
    public void initialize() {
        TableColumn<Report, String> yearColumn = new TableColumn<>("year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableReport.getColumns().add(yearColumn);
        TableColumn<Report, String> revenueColumn = new TableColumn<>("revenue");
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        tableReport.getColumns().add(revenueColumn);

    }
}
