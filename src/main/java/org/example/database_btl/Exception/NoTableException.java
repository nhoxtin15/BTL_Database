package org.example.database_btl.Exception;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 02/05/2024$
 */
public class NoTableException extends  Exception{
    public NoTableException() {
        super("No table found");
    }
}
