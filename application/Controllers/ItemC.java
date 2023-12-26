package application.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import application.MysqlConnection;
import application.Controllers.classes.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class ItemC {
	private int cid;
	private int iid;
	@FXML
	private Label itemName;
	@FXML
	private Label itemPrice;
	private ItemsC itemsC;
	
	public void setItemData(@SuppressWarnings("exports") Item item, ItemsC itemsC) {
		itemName.setText(item.getName());
		itemPrice.setText(String.valueOf(item.getPrice()));
		cid=item.getCategoryId();
		iid=item.getId();
		
		this.itemsC = itemsC;
	}

	@FXML
    public void update() {
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/application/layout/uaItemPU.fxml"));
			DialogPane popup = fxmlloader.load();
			
			UAItemPU update = fxmlloader.getController();
			
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(popup);
			dialog.setTitle("update" + " " + itemName.getText());
			
			Connection connection = MysqlConnection.getDBConnection();
			String sql = "SELECT `name` FROM `categories`"; 
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet results = ps.executeQuery();
            
            ArrayList<String> names = new ArrayList<>();
            while (results.next()) {
                names.add(results.getString("name"));
            }
            update.setName(itemName.getText());
			update.setPrice(Double.parseDouble(itemPrice.getText()));
			update.setCategories(names);
			
			sql = "SELECT `name` FROM `categories` WHERE id = ?"; 
			ps = connection.prepareStatement(sql);
			ps.setInt(1, cid);
			results = ps.executeQuery();
			results.next();
			update.setCategorie(results.getString("name"));
			
			Optional<ButtonType> clickedBtn = dialog.showAndWait();
			
			if(clickedBtn.get() == ButtonType.OK) {
				try {
					sql = "SELECT `id` FROM `categories` WHERE name = ?"; 
					ps = connection.prepareStatement(sql);
					ps.setString(1, update.getCategorie());
					results = ps.executeQuery();
					results.next();
					int cid=results.getInt("id");
					
					sql = "UPDATE `items` SET name = ?, price = ?, categoryId = ?, picture = '' WHERE id = ?"; 
					ps = connection.prepareStatement(sql);
					ps.setString(1, update.getName());
					ps.setDouble(2, update.getPrice());
					ps.setInt(3, cid);
					ps.setInt(4, iid);
					
					ps.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			itemsC.getItems(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@FXML
	public void delete() {
		Connection connection = MysqlConnection.getDBConnection();
		String sql = "DELETE FROM `items` WHERE id = ?"; 
        PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, iid);
			ps.execute();
			itemsC.getItems(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
