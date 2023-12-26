package application.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.MysqlConnection;
import application.Controllers.classes.OrderItem;
import application.Controllers.classes.OrderKitchen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class KOrderController {
	int id;
	
    @FXML
    private Label orderid;
    
    @FXML
    private Label note;
    
    @FXML
    private VBox vboxitem;
    
    @FXML
    private Pane readyBtn;
    
    private KOrdersController kordersC;
    
    ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    
   
    public void setOrderData(@SuppressWarnings("exports") OrderKitchen order, KOrdersController kordersC) {
    	id =  order.getId();
    	String p = "Order #" + String.format("%d",id);
    	orderid.setText(p);
    	note.setText(order.getNote());
    	this.kordersC = kordersC;
    	readyBtn.setOnMouseClicked(this::ready);
    	
    	getAll();
    	for (OrderItem item : orderItems) {
       	 FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/application/layout/kitchen_card_item_row.fxml"));
            Parent orderNode1;
			try {
				orderNode1 = loader1.load();
				ItemRow  itemRow = loader1.getController();
	        	itemRow.setItemsData(item);
	        	 
	        	vboxitem.getChildren().add(orderNode1);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

	
	public void getAll() {
		try {
			Connection connection = MysqlConnection.getDBConnection();
			String sql2 = "SELECT * FROM `orderitems` WHERE orderId = ?"; 
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			ps2.setInt(1, id);
			ps2.execute();
			ResultSet results2 = ps2.executeQuery();
			while (results2.next()) {
				int itemid =results2.getInt("itemId");
				int quantity=results2.getInt("quantity");
				
				String sql3 = "SELECT `name` FROM `items` WHERE id = ?"; 
				PreparedStatement ps3 = connection.prepareStatement(sql3);
				ps3.setInt(1, itemid);
				ps3.execute();
				ResultSet results3 = ps3.executeQuery();
				results3.next();
				
				String itemName = results3.getString("name");
				orderItems.add(new OrderItem(itemid,itemName,quantity));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ready(@SuppressWarnings("exports") MouseEvent event) {
		kordersC.updateToReady(id);
	}
}
