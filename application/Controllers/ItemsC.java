package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.MysqlConnection;
import application.Controllers.classes.Categorie;
import application.Controllers.classes.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.TilePane;

public class ItemsC implements Initializable {
	private int currCat;
	@FXML
	private TilePane categoriesContainer;
	
	@FXML
	private TilePane itemsContainer;
	
	ObservableList<Categorie> categories = FXCollections.observableArrayList();
	ObservableList<Item> items = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getAllCats();
	    for (Categorie categorie : categories) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/categorie.fxml"));
	            Parent categorieNode = loader.load();
	
	            CategorieC categorieController = loader.getController();
	            categorieController.setcategorieData(categorie, this);
	
	            categoriesContainer.getChildren().add(categorieNode);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public void getAllCats() {
	    categories.clear();
	    try {
	        Connection connection = MysqlConnection.getDBConnection();
	        
	        if (connection != null) {
	            String sql = "SELECT * FROM `categories`"; 
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ResultSet results = ps.executeQuery();
	            
	            while (results.next()) {
	            	int id = results.getInt("id");
	                String name = results.getString("name");
	                categories.add(new Categorie(id, name));
	            }
	            if(categories.get(0) != null) {
	            	this.currCat = categories.get(0).getId();
	            	getItems(categories.get(0).getId());
	            }
	        } else {
	            System.err.println("Failed to establish a database connection!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	void getItems(int id) {
		this.currCat = id;
	    items.clear();
	    itemsContainer.getChildren().clear();
	    
	    FXMLLoader loader;
	    Parent itemNode;
	    try {
	    	loader = new FXMLLoader(getClass().getResource("/application/layout/add-item.fxml"));
	        itemNode = loader.load();
	        itemNode.setOnMouseClicked(event -> {
	            addItem();
	        });
	        itemsContainer.getChildren().add(itemNode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	    try {
	        Connection connection = MysqlConnection.getDBConnection();
	        
	        if (connection != null) {
	            String sql = "SELECT * FROM `items` WHERE categoryId = ?"; 
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, id);
	            ResultSet results = ps.executeQuery();
	            
	            while (results.next()) {
	                int iid = results.getInt("id");
	                String name = results.getString("name");
	                double price = results.getDouble("price");
	                int cid = results.getInt("categoryId");
	                items.add(new Item(iid, name, price, cid));
	            }
	            
	            for (Item item : items) {
	                loader = new FXMLLoader(getClass().getResource("/application/layout/item.fxml"));
	                itemNode = loader.load();

	                ItemC itemController = loader.getController();
	                itemController.setItemData(item, this);

	                itemsContainer.getChildren().add(itemNode);
	            }
	        } else {
	            System.err.println("Failed to establish a database connection!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
    public void addItem() {
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/application/layout/uaItemPU.fxml"));
			DialogPane popup = fxmlloader.load();
			
			UAItemPU ua = fxmlloader.getController();
			
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(popup);
			dialog.setTitle("create new item");
			
			Connection connection = MysqlConnection.getDBConnection();
			String sql = "SELECT `name` FROM `categories` WHERE id = ?"; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, currCat);
			ResultSet results = ps.executeQuery();
			results.next();
            
            ArrayList<String> names = new ArrayList<>();
            names.add(results.getString("name"));
            
			ua.setCategories(names);
			ua.setCategorie(results.getString("name"));
			
			Optional<ButtonType> clickedBtn = dialog.showAndWait();
			
			if(clickedBtn.get() == ButtonType.OK) {
				try {
					connection = MysqlConnection.getDBConnection();
					
					sql = "INSERT INTO `items` (`name`, `price`, `categoryId`, `picture`) VALUES(?, ?, ?, '')"; 
					ps = connection.prepareStatement(sql);
					
					ps.setString(1, ua.getName());
			        ps.setDouble(2, ua.getPrice());
					ps.setInt(3, currCat);
					
					ps.execute();
					getItems(currCat);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
