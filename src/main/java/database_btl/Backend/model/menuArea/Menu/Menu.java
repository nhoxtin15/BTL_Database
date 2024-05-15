package database_btl.Backend.model.menuArea.Menu;

/**
 * Description: Store all the category$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */


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
