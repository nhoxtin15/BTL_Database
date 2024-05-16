package database_btl.Exception;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 16/05/2024$
 */
public class NoTableSelected extends Exception {
    public NoTableSelected(String ProductName) {
        super("The product " + ProductName + " is not select a table");
    }
}
