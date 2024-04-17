package org.example.database_btl.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController{
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    @FXML
    protected void onLoginButtonClick() {
        username.setText("concac");
    }

}
