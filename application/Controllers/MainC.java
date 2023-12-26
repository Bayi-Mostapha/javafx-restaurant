package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainC implements Initializable {

	@FXML
	private AnchorPane contentContainer;
	
	@FXML
	private AnchorPane mainContainer;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
			Stage stage = (Stage)mainContainer.getScene().getWindow();
			stage.close();
		} 
	}
}
