package org.example.database_btl.Backend.model.controller;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.database_btl.HelloApplication;
import javafx.scene.layout.Pane;
public class TableController {

    public static final Image DEFAULT_IMAGE = new Image(HelloApplication.class.getResource("Image/dinning-table.png").toString());
    @FXML
    public ImageView tableImage;

    @FXML
    public Rectangle rectangle;

    @FXML
    public Text tableNumber;

    @FXML
    public Pane pane;

    public void initialize() {
        tableImage.setImage(DEFAULT_IMAGE);

        rectangle.setFill(Color.GREEN);
        pane.setOnMouseEntered(e->{
            pane.setEffect(new DropShadow());

        });
        pane.setOnMouseExited(e->{
            pane.setEffect(null);

        });
        pane.setOnMouseClicked(e->{
            rectangle.setFill(Color.RED);

            System.out.println("Clicked");
        });

    }

}
