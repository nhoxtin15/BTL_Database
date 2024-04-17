package org.example.database_btl.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    protected void onLoginButtonClick() {
        username.setText("concac");
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
