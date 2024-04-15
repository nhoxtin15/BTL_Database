package org.example.database_btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    private static byte[] readImage(String imagePath) {
        try {
            // Read the image file into a byte array
            File file = new File(imagePath);
            byte[] imageData = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(imageData);
            fis.close();
            return imageData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();



        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "nhoxtin1");
            statement = connection.createStatement();




            //PreparedStatement statement1 = connection.prepareStatement("INSERT INTO image VALUES (?)");
            //statement1.setBlob(1, HelloApplication.class.getResource("Image/abc.jpg").openStream());
            //statement1.executeUpdate();

            resultSet = statement.executeQuery("SELECT * from abc");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("Image.fxml"));
            stage.setScene(new Scene(fxmlLoader1.load(), 500, 700));
            Image_controller controller = fxmlLoader1.getController();


            try(
                PreparedStatement statement1 = connection.prepareStatement("SELECT image FROM image");
            ){
                //statement1.setInt(1, 1);
                try(ResultSet resultSet1 = statement1.executeQuery()){
                    if(resultSet1.next()){
                        byte[] imageData = resultSet1.getBytes("image");
                        controller.setImage(new Image(new ByteArrayInputStream(imageData)));
                    }
                }
            }

            stage.show();







        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {
        launch();
    }
}