package application.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class OrdersC implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            contentVBox.setMinWidth(newValue.getWidth());
            contentVBox.setMinHeight(newValue.getHeight());
        });
    }
}
