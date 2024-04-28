package org.example.database_btl.Backend.model.Receipt;

import java.util.ArrayList;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 28/04/2024$
 */
public class AreaReceipt {
    public ArrayList<String> tables;

    public String name;

    public AreaReceipt(String name, ArrayList<String> tables){
        this.name = name;
        this.tables = new ArrayList<>(tables);
    }

    public void addTable(String table){
        tables.add(table);
    }

}
