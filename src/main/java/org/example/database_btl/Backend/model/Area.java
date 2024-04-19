package org.example.database_btl.Backend.model;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.example.database_btl.Backend.model.Controller.AreaController;
import org.example.database_btl.HelloApplication;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Area {
    public ArrayList<Table> tables;
    public ArrayList<Viproom> Viprooms;
    public String name;

    public AreaController controller;



    public Area(String name, AreaController controller, ResultSet resultSet){
        this.name = name;
        this.controller = controller;

    }

}
