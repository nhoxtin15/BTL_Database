package org.example.database_btl.Backend.model.menuArea.Menu;


/**
 * Description: Food and Drink category$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class FoodAndDrink extends Category {
    public FoodAndDrink(){
        super();
        name = "Food and Drink";
        sqlQuery = "select * from product p, food_and_drinks f where p.product_id = f.product_id";
        initCategory();
        this.CategoryContainer.setText("Food and Drink");
    }

}
