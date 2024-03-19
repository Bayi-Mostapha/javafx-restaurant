package application.Controllers;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.MysqlConnection;
import application.Controllers.classes.OrderKitchen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;

public class KOrdersController {
    @FXML
    private TilePane contentbox;
 
    ObservableList<OrderKitchen> orders = FXCollections.observableArrayList();
    
    public void initialize() {
    	getAll();
    	displayCards();
    }
    
    public void getAll() {
    	orders.clear();
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql = "SELECT * FROM `orders` WHERE status = 'pending'"; 
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id =results.getInt("id");
				String note =results.getString("note");
				
				orders.add(new OrderKitchen(id, note));
			}
		} catch (Exception e) {
			
		}	
    }
    
    public void displayCards() {
    	contentbox.getChildren().clear();
        for (OrderKitchen order : orders) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/kitchen_card.fxml"));
                Parent orderNode = loader.load();

                KOrderController KOrderController = loader.getController();
                KOrderController.setOrderData(order, this);
                
                contentbox.getChildren().add(orderNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateToReady(int id) {
    	Connection connection = MysqlConnection.getDBConnection();
		
		String sql = "UPDATE `ORDERS` SET status = 'ready' WHERE id = ?"; 
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getAll();
		displayCards();
    }
}
