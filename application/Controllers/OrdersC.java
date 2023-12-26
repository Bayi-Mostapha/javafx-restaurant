package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.MysqlConnection;
import application.Controllers.classes.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class OrdersC implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox;
    
    ObservableList<Order> orders = FXCollections.observableArrayList();
    
    public void getAll() {
    	orders.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql = "SELECT * FROM `orders`"; 
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id=results.getInt("id");
				String type=results.getString("type");
				double total=results.getDouble("total");
				String date =results.getString("order_date");
				String status =results.getString("status");
				orders.add(new Order(id, type, total, date, status));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            contentVBox.setMinWidth(newValue.getWidth());
            contentVBox.setMinHeight(newValue.getHeight());
        });
		
    	getAll();
    	contentVBox.getChildren().clear();
	    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/application/layout/order-row-titles.fxml"));
	    
        Parent orderNode1;
		try {
			orderNode1 = loader1.load();
			contentVBox.getChildren().add(orderNode1);
		} catch (IOException e) {
			e.printStackTrace();
		}
         
        for (Order order : orders) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/order-row.fxml"));
                Parent orderNode = loader.load();

                OrderController OrderController = loader.getController();
                OrderController.setOrderData(order);
                
                Button statusButton = (Button) orderNode.lookup(".status");
	             if (statusButton != null) {
	            	 if("pending".equals(order.getStatus())) {
	            		 statusButton.getStyleClass().add("pending_btn"); 
	            	 }
	            	 else if("ready".equals(order.getStatus())) {
	            		 statusButton.getStyleClass().add("ready_btn"); 
	            	 }
	             }

                contentVBox.getChildren().add(orderNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}