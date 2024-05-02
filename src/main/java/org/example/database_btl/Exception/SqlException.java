package org.example.database_btl.Exception;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 02/05/2024$
 */
public class SqlException extends Exception{
    public SqlException(String queries) {
        super("Error in executing queries: " + queries);
    }
}
