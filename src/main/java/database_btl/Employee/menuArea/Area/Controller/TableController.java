package database_btl.Employee.menuArea.Area.Controller;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import database_btl.HelloApplication;
import javafx.scene.layout.Pane;
public class TableController {

    public static Image DEFAULT_IMAGE = new Image(HelloApplication.class.getResource("Image/dinning-table.png").toString());
    @FXML
    public ImageView imageTable;

    @FXML
    public Rectangle rectangle;

    @FXML
    public Text tableName;

    @FXML
    public Pane paneContainer;

    public Image getDefaultImage(){
        return DEFAULT_IMAGE;
    }

    public void initialize() {
        imageTable.setImage(getDefaultImage());

        rectangle.setFill(Color.GREEN);
        paneContainer.setOnMouseEntered(e->{
            paneContainer.setEffect(new DropShadow());

        });
        paneContainer.setOnMouseExited(e->{
            paneContainer.setEffect(null);

        });
    }

}
