package org.example.database_btl.Backend.model.menuArea.Menu;

import java.util.ArrayList;

public class FoodAndDrink extends Category {
    public FoodAndDrink(){
        super();
        name = "Food and Drink";
        sqlQuery = "select * from product p, food_and_drinks f where p.product_id = f.product_id";
        initCategory();
        this.CategoryContainer.setText("Food and Drink");
    }





}
