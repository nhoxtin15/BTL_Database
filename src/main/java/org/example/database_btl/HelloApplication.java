package org.example.database_btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.example.database_btl.Backend.Sql_connector;
import  org.example.database_btl.Controller.LoginController;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

import java.io.IOException;
import java.net.URL;



public class HelloApplication extends Application {
//    private static byte[] readImage(String imagePath) {
//        try {
//            // Read the image file into a byte array
//            File file = new File(imagePath);
//            byte[] imageData = new byte[(int) file.length()];
//            FileInputStream fis = new FileInputStream(file);
//            fis.read(imageData);
//            fis.close();
//            return imageData;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login!");
        stage.setScene(scene);


        // Get screens
        Screen primaryScreen = Screen.getPrimary();
        Screen secondaryScreen = null;
        for (Screen screen : Screen.getScreens()) {
            if (!screen.equals(primaryScreen)) {
                secondaryScreen = screen;
                break;
            }
        }
        // Set the stage to the secondary screen if it exists
        if (secondaryScreen != null) {
            Rectangle2D bounds = secondaryScreen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
        }

        System.out.println(getClass().getResource("Image/login_logo.png").toString());
        stage.getIcons().add(new Image(getClass().getResource("Image/login_logo.png").toString()));


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}