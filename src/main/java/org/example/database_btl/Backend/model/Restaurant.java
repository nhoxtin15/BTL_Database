package org.example.database_btl.Backend.model;

import org.example.database_btl.Backend.Sql_connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Restaurant {
    ArrayList<Area> areas;
    public Restaurant(){
        areas = new ArrayList<>();
        try {
            ResultSet rs = Sql_connector.executeQuery("SELECT * FROM area");
            while (rs.next()){
                areas.add(new Area(rs.getString("Area_ID")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
    }
}
