package org.example.database_btl.Backend.model.menuArea.Menu;

/**
 * Description: Soup base category$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class Soup_base extends  Category{
    public Soup_base(){
        super();
        name = "Soup base";
        sqlQuery = "select * from product p, soup_base s where p.product_id = s.product_id";
        initCategory();
        this.CategoryContainer.setText("Soup base");
    }
}
