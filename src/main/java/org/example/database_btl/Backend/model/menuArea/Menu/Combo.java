package org.example.database_btl.Backend.model.menuArea.Menu;

public class Combo extends Category{
    public Combo(){
        super();
        name = "Combo";
        sqlQuery = "select * from product p, combo c where p.product_id = c.product_id";
        initCategory();
        this.CategoryContainer.setText("Combo");
    }

}
