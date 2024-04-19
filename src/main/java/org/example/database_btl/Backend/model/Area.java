package org.example.database_btl.Backend.model;


import org.example.database_btl.Backend.model.Controller.AreaController;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Area {
    public ArrayList<Table> tables;
    public ArrayList<Viproom> Viprooms;
    public String name;

    public AreaController controller;



    public Area(String name, AreaController controller, ResultSet resultSet){
        this.name = name;
        //retrieve area an create area name

    }

}
