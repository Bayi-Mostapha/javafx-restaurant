package application.Controllers;

import application.Controllers.classes.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OrderController {
	int id;
	
    @FXML
    private Label orderid;
    
    @FXML
    private Label ordertype;
    
    @FXML
    private Label orderprice;
    
    @FXML
    private Label orderdate;
    
    @FXML
    private Button status;

    public void setOrderData(@SuppressWarnings("exports") Order order) {
    	id =  order.getId();
    	orderid.setText(String.format("%d",id));
    	ordertype.setText( order.getType());
        orderprice.setText(String.format("$%.2f",  order.getPrice()));
        orderdate.setText(order.getDate());
        status.setText(order.getStatus());
    }
    
    
}