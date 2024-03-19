package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.MysqlConnection;
import application.Controllers.classes.Categorie;
import application.Controllers.classes.Item;
import application.Controllers.classes.SelectedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MenuC implements Initializable {
	//private int currCat;
	@FXML
	private TilePane categoriesContainer;
	@FXML
	private TilePane itemsContainer;
	@FXML
	private VBox selectedItemsContainer;
	
	@FXML
	private Label nItemsLabel;
	@FXML
	private Label sTotalLabel;
	@FXML
	private Label totalLabel;
	
	@FXML
	private RadioButton radioDineIn;
	@FXML
	private RadioButton radioToGo;
	@FXML
	private RadioButton radioDelivery;
	@FXML
	private TextArea notetxt;
	
	ObservableList<Categorie> categories = FXCollections.observableArrayList();
	ObservableList<Item> items = FXCollections.observableArrayList();
	ObservableList<SelectedItem> selectedItems = FXCollections.observableArrayList();

	public void setQtt(int id, int qtt) throws IOException {
		for (SelectedItem selectedItem : selectedItems) {
		    if(selectedItem.getItemId()==id) {
		    	selectedItem.setQuantity(qtt);
		    }
		    displaySelected();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getAllCats();
	    for (Categorie categorie : categories) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/categorie2.fxml"));
	            Parent categorieNode = loader.load();
	
	            Categorie2C categorieController = loader.getController();
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
	                String picture = results.getString("picture");
	                categories.add(new Categorie(id, name, picture));
	            }
	            if(categories.get(0) != null) {
	            	//this.currCat = categories.get(0).getId();
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
		//this.currCat = id;
	    items.clear();
	    itemsContainer.getChildren().clear();
	    
	    FXMLLoader loader;
	    Parent itemNode;

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
	                String picture = results.getString("picture");
	                items.add(new Item(iid, name, price, cid, picture));
	            }
	            
	            for (Item item : items) {
	                loader = new FXMLLoader(getClass().getResource("/application/layout/menu-item.fxml"));
	                itemNode = loader.load();

	                MenuItemC itemController = loader.getController();
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
	
	void addToSelected(int id) {
		for (SelectedItem selectedItem : selectedItems) {
		    if(selectedItem.getItemId()==id) {
		    	return;
		    }
		}

	    try {
	        Connection connection = MysqlConnection.getDBConnection();
	        
	        if (connection != null) {
	            String sql = "SELECT * FROM `items` WHERE id = ?"; 
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setInt(1, id);
	            ResultSet results = ps.executeQuery();
	            
	            if (results.next()) {
	                int iid = results.getInt("id");
	                String name = results.getString("name");
	                double price = results.getDouble("price");
	                String picture = results.getString("picture");
	                selectedItems.add(new SelectedItem(iid, name, price, picture, 1));
	            }
	            displaySelected();
	        } else {
	            System.err.println("Failed to establish a database connection!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void displaySelected() throws IOException {
		selectedItemsContainer.getChildren().clear();
		FXMLLoader loader;
	    Parent itemNode;

		for (SelectedItem item : selectedItems) {
            loader = new FXMLLoader(getClass().getResource("/application/layout/menu-selected-item.fxml"));
            itemNode = loader.load();

            MenuSelectedItemC itemController = loader.getController();
            itemController.setItemData(item, this);

            selectedItemsContainer.getChildren().add(itemNode);
        }
		changeInfo();
	}
	
	public void changeInfo() {
		nItemsLabel.setText(String.valueOf(selectedItems.size()) + " (items)");
		
		double total = 0;
		for (SelectedItem selectedItem : selectedItems) {
		    double itemTotal = selectedItem.getQuantity() * selectedItem.getItemPrice();
		    total += itemTotal;
		}
		String formattedTotal = String.format("%.2f", total);

		sTotalLabel.setText("$" + formattedTotal);
		totalLabel.setText("$" + formattedTotal);
	}
	
	public void removeItem(int id) {
		Iterator<SelectedItem> iterator = selectedItems.iterator();
		while (iterator.hasNext()) {
		    SelectedItem selectedItem = iterator.next();
		    if (selectedItem.getItemId() == id) {
		        iterator.remove();
		        break;
		    }
		}
		try {
			displaySelected();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void insertOrder() {
		if(selectedItems.size() == 0) {
			return;
		}
		
		int id = 0;
		
		String type = "";
		if(radioDineIn.isSelected()) {
			type = "dine in";
		} else if(radioToGo.isSelected()) {
			type = "to go";
		} else if(radioDelivery.isSelected()) {
			type = "delivery";
		} 
		
		double total = 0;
		for (SelectedItem selectedItem : selectedItems) {
		    double itemTotal = selectedItem.getQuantity() * selectedItem.getItemPrice();
		    total += itemTotal;
		}
		
		String note = notetxt.getText();
		if(note == "") {
			note = "no note";
		}
		
		try {
		    Connection connection = MysqlConnection.getDBConnection();
		    String sql = "INSERT INTO `orders`(`type`, `total`, `status`, `note`, `order_date`) VALUES(?, ?, 'pending', ?, CURDATE())";
		    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		    ps.setString(1, type);
		    ps.setDouble(2, total);
		    ps.setString(3, note);

		    ps.executeUpdate();

		    ResultSet generatedKeys = ps.getGeneratedKeys();
		    if (generatedKeys.next()) {
		        id = generatedKeys.getInt(1);
		    }
		    if(id != 0) {
		    	for (SelectedItem selectedItem : selectedItems) {
			    	sql = "INSERT INTO `orderItems`(orderId, itemId, quantity) VALUES(?, ?, ?)"; 
					PreparedStatement ps2 = connection.prepareStatement(sql);
					
					ps2.setInt(1, id);
					ps2.setInt(2, selectedItem.getItemId());
					ps2.setInt(3, selectedItem.getQuantity());
					
					ps2.execute();
				}
		    }
		    selectedItems.clear();
		    selectedItemsContainer.getChildren().clear();
		    totalLabel.setText("0");
		    sTotalLabel.setText("0");
		    nItemsLabel.setText("0 (items)");
		    radioDineIn.setSelected(true);
		    notetxt.setText("");
		} catch (Exception e) {
		    e.printStackTrace();
		}

	}
}
