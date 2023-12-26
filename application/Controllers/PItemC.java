package application.Controllers;

import application.Controllers.classes.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PItemC {
	@FXML
    private Label itemname;
    
    @FXML
    private Label itemprice;
    
   
    public void setData(@SuppressWarnings("exports") Item item) {
    	itemname.setText(item.getName());
    	itemprice.setText(String.valueOf(item.getPrice()));
    }
}
