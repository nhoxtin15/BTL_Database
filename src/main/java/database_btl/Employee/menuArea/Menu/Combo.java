package database_btl.Employee.menuArea.Menu;
/**
 * Description: Combo category$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class Combo extends Category{
    public Combo(){
        super();
        name = "Combo";
        sqlQuery = "select * from product p, combo c where p.product_id = c.product_id";
        initCategory();
        this.CategoryContainer.setText("Combo");
    }

}
