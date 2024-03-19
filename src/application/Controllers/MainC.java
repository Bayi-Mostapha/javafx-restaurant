package application.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.Main;
import application.MysqlConnection;
import application.Controllers.classes.Categorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainC implements Initializable {
	@FXML
	private AnchorPane contentContainer;
	
	@FXML
	private AnchorPane mainContainer;
	
	@FXML
	private Label username;
	
	@FXML
	private ImageView userimage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
	        Connection connection = MysqlConnection.getDBConnection();
	        
	        if (connection != null) {
	            String sql = "SELECT * FROM `users` WHERE id = 1"; 
	            PreparedStatement ps = connection.prepareStatement(sql);
	            ResultSet results = ps.executeQuery();
	            
	            if(results.next()) {
	                String fname = results.getString("first_name");
	                String lname = results.getString("last_name");
	                username.setText(fname + " " + lname);
	                
	                Image image;
	        		try {
	        			image = new Image(new File(results.getString("picture")).toURI().toURL().toExternalForm());
	        			userimage.setImage(image);
	        		} catch (MalformedURLException e) {
	        			e.printStackTrace();
	        		}
	            }
	        } else {
	            System.err.println("Failed to establish a database connection!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/dashboard.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void goToDashboard(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/dashboard.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	public void goToMenu(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/menu.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToOrders(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/orders.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToStaff(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/staff.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToItems(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/items.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToTables(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/tables.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToSettings(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/application/layout/SettingsScene.fxml"));
			contentContainer.getChildren().removeAll();
			contentContainer.getChildren().setAll(fxml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void logOut(@SuppressWarnings("exports") javafx.event.ActionEvent actionEvent) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("logout");
		alert.setContentText("are you sure you want to log out?");
		
		if (alert.showAndWait().get() == ButtonType.OK){
			Main.changeScene("LoginScene.fxml", false);
		} 
	}
}
