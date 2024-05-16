package database_btl.Exception;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import database_btl.Exception.Controller.PopUpMessageController;
import org.w3c.dom.Text;

/**
 * Description:
 * Author: nhoxtin15$
 * Date: 28/04/2024$
 */
public class PopUpMessage{


    public String message;
    public String errorName;
    public PopUpMessage(String errorName,String message){
        this.message = message;
        this.errorName = errorName;
        this.display();
    }

    public PopUpMessage(Exception e){
        this.message = e.getMessage();
        this.errorName =   e.getClass().getSimpleName();

//        e.printStackTrace();
//        System.out.println("-----------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n-----------------------------------------");
        this.display();
    }

    public void display(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUpMessage.fxml"));
        try{
            Parent root = loader.load();
            PopUpMessageController controller = loader.getController();
            controller.Error_name.setText(errorName);
            controller.Error_description.setText(message);

            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
