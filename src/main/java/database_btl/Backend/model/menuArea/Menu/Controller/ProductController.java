package database_btl.Backend.model.menuArea.Menu.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ProductController {
    @FXML
    public Label labelPrice;

    @FXML
    public Text textName;

    @FXML
    public ImageView imageProduct;

    @FXML
    public Pane pane;

    @FXML
    private void initialize() {
        pane.setOnMouseEntered(e->{
            pane.setEffect(new DropShadow());

        });
        pane.setOnMouseExited(e->{
            pane.setEffect(null);
        });
    }


}
