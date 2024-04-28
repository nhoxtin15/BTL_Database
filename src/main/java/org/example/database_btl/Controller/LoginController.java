package org.example.database_btl.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.database_btl.Exception.PopUpMessage;
import org.example.database_btl.HelloApplication;

import java.io.IOException;

public class LoginController{
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    @FXML
    private Pane leftPane;

    @FXML
    private Pane rightPane;

    @FXML
    private Pane mainPane;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws Exception {



        //login succesfully
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("mainEmployee.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            new PopUpMessage(e);
        }
    }

    @FXML
    private Text label1;
    @FXML
    private Text label2;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;

    public void initialize() {
        rightPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newFontSize = newValue.doubleValue()/16;
            label1.setFont(new javafx.scene.text.Font("Helvetica Bold",newFontSize));
            label2.setFont(new javafx.scene.text.Font("Helvetica Bold",newFontSize));
            newFontSize = newValue.doubleValue()/25;
            labelUsername.setFont(new javafx.scene.text.Font("Helvetica Bold",newFontSize));
            labelPassword.setFont(new javafx.scene.text.Font("Helvetica Bold",newFontSize));
        });
    }

}
