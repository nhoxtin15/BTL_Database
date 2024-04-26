package org.example.database_btl.Backend.model.menuArea.Menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import org.example.database_btl.Backend.Sql_connector;
import org.example.database_btl.Backend.model.controller.CategoryController;

import java.io.ByteArrayInputStream;
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
        System.out.println("cac");
        try(ResultSet rs = Sql_connector.executeQuery(sqlQuery)){
            while(rs.next()){
                System.out.println("cac");
                String productName = rs.getString("product_name");

                int price = rs.getInt("price");
                byte[] imageBytes =  rs.getBytes("image");
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                // Read image from ByteArrayInputStream
                Image image = new Image(bis);
                tempProductList.add( new Product(productName, price, image));

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
