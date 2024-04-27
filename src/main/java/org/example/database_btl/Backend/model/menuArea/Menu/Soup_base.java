package org.example.database_btl.Backend.model.menuArea.Menu;

public class Soup_base extends  Category{
    public Soup_base(){
        super();
        name = "Soup base";
        sqlQuery = "select * from product p, soup_base s where p.product_id = s.product_id";
        initCategory();
        this.CategoryContainer.setText("Soup base");
    }
}
