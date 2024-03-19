package application.Controllers;

import application.Controllers.classes.OrderItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemRow {
	int id;
	
    @FXML
    private Label itemname;
    
    @FXML
    private Label quantity;
    
   
    public void setItemsData(@SuppressWarnings("exports") OrderItem order) {
    	id =  order.getItemId();
    	itemname.setText(order.getName());
    	String q = "x" + order.getQuantity();
    	quantity.setText(q);
    }
    
    
}
