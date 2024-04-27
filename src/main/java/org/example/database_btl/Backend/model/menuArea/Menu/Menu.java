package org.example.database_btl.Backend.model.menuArea.Menu;


import org.example.database_btl.Backend.model.Restaurant;

import java.util.ArrayList;

public class Menu {

    public FoodAndDrink foodAndDrink;
    public Combo combo;

    public Soup_base soup_base;


    public Menu(){
        foodAndDrink = new FoodAndDrink();
        combo = new Combo();
        soup_base = new Soup_base();
    }












}
