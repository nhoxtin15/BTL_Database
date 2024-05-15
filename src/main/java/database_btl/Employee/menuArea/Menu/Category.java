package database_btl.Employee.menuArea.Menu;

/**
 * Description: General class for categories$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import database_btl.Sql_connector;
import database_btl.Employee.menuArea.Menu.Controller.CategoryController;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Category {
    public String name;
    public ArrayList<Product> products;
    public Tab CategoryContainer;
    public CategoryController CategoryController;

    public String sqlQuery;

    public Category(){
        products = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Category.fxml"));
        try{
            CategoryContainer = loader.load();
            CategoryController = loader.getController();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void initCategory(){
        CategoryContainer.setOnSelectionChanged(event -> {
            if(CategoryContainer.isSelected()){
                updateCategory();
            }
        });
    }

    public void updateCategory(){
        ArrayList<Product> tempProductList = new ArrayList<>();

        try(ResultSet rs = Sql_connector.executeQuery(sqlQuery)){
            while(rs.next()){
                String productName = rs.getString("product_name");

                int price = rs.getInt("price");
                String imagePath = rs.getString("image");

                tempProductList.add( new Product(productName, price, imagePath));

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        for (int i = 0; i< products.size() && i < tempProductList.size(); i++){
            if(! products.get(i).compare(tempProductList.get(i)))
                products.get(i).update(tempProductList.get(i));
        }
        for (int i = products.size(); i < tempProductList.size(); i++){
            products.add(tempProductList.get(i));
            products.getLast().initProduct();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Category.fxml"));
        try{
            Tab tempCategoryContainer = loader.load();
            CategoryController tempCategoryController = loader.getController();
            int count = 0;
            for (Product p : products){
                if(count % 5 == 0){
                    tempCategoryController.products.addRow(count/5);
                }
                tempCategoryController.products.add(p.productContainer, count % 5, count/5);
                count++;
            }

            this.CategoryContainer.setContent(tempCategoryContainer.getContent());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }














}
