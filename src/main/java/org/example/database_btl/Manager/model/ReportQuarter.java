package org.example.database_btl.Manager.model;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class ReportQuarter extends Report{
    public ReportQuarter(String year, String quarter, String revenue) {
        super(year, quarter, "0", "0", revenue);
    }
}
