package database_btl.Manager.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import database_btl.Manager.model.Report;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ReportWeekController extends ReportController{
    public void initialize() {
        super.initialize();
        TableColumn<Report, String> yearColumn = new TableColumn<>("year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableReport.getColumns().add(yearColumn);
        TableColumn<Report, String> weekColumn = new TableColumn<>("week");
        weekColumn.setCellValueFactory(new PropertyValueFactory<>("week"));
        tableReport.getColumns().add(weekColumn);
        TableColumn<Report, String> revenueColumn = new TableColumn<>("revenue");
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        tableReport.getColumns().add(revenueColumn);
    }
}
