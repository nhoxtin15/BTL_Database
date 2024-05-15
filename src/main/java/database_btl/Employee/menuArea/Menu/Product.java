package database_btl.Employee.menuArea.Menu;

/**
 * Description: Store product$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import database_btl.Employee.Receipt.AllReceipt;
import database_btl.Employee.menuArea.Menu.Controller.ProductController;
import database_btl.Exception.*;
import database_btl.HelloApplication;

public class Product {

    public String name;
    public int price;

    public Image image;

    public String table;

    public Pane productContainer;
    public ProductController productController;

    public boolean isInit = false;

    public Product(String name, int price, String imagePath) {
        this.name = name;
        this.price = price;
        this.image = new Image(HelloApplication.class.getResource(imagePath).toString());
    }

    public void initProduct(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Product.fxml"));
        try {
            productContainer = loader.load();
            productController = loader.getController();
            productController.textName.setText(name);
            productController.labelPrice.setText(price+" VND");
            productController.imageProduct.setImage(image);

            this.productContainer.setOnMouseClicked(event -> {
                try {
                    AllReceipt.addProduct(this.name,this.price);
                } catch (Exception e) {
                    new PopUpMessage(e);
                }
            });
            isInit = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setImage(Image image){
        this.image = image;
        productController.imageProduct.setImage(image);
    }
    public void setName(String name){
        this.name = name;
        productController.textName.setText(name);
    }
    public void setPrice(int price){
        this.price = price;
        productController.labelPrice.setText(price+" VND");
    }
    public void update(Product p){
        this.setName(p.name);
        this.setPrice(p.price);
        this.setImage(p.image);
    }
    public boolean compare(Product p){
        return this.name.equals(p.name) && this.price == p.price && this.image.equals(p.image);
    }

}
