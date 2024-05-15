package org.example.database_btl.Manager.model;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 03/05/2024$
 */
public class Report {
    public String year;
    public String quarter;
    public String month;
    public String week;
    public String revenue;

    public Report(String year, String quarter, String month, String week, String revenue) {
        this.year = year;
        this.quarter = quarter;
        this.month = month;
        this.week = week;
        this.revenue = revenue;
    }
    public String getYear() {
        return year;
    }
    public String getQuarter() {
        return quarter;
    }
    public String getMonth() {
        return month;
    }
    public String getWeek() {
        return week;
    }
    public String getRevenue() {
        return revenue;
    }
}
