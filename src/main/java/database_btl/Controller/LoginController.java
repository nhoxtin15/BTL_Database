package database_btl.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import database_btl.Backend.Sql_connector;
import database_btl.Exception.PopUpMessage;
import database_btl.HelloApplication;

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
        if (username.getText().isEmpty() || password.getText().isEmpty()){
            new PopUpMessage(new Exception("Please fill in all the information"));
            return;
        }

        try {
            Sql_connector.setInstance(username.getText(), password.getText());
        }
        catch (Exception e){
            new PopUpMessage(new Exception("Wrong username or password"));
            return;
        }


        //login succesfully
        FXMLLoader loader;
        if(username.getText().equals("sManager"))
            loader= new  FXMLLoader(HelloApplication.class.getResource("mainManager.fxml"));
        else loader= new  FXMLLoader(HelloApplication.class.getResource("mainEmployee.fxml"));

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
