package application.Controllers;

import application.Controllers.classes.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Categorie2C {
	private int id;
	@FXML
	private Label catName;
	//@FXML
	private MenuC menuController;
	
	public void setcategorieData(@SuppressWarnings("exports") Categorie categorie, MenuC menuController) {
		this.id = categorie.getId();
		catName.setText(categorie.getName());
		this.menuController = menuController;
		
		catName.setOnMouseClicked(this::handleCategoryClick);
	}
	
	
	private void handleCategoryClick(MouseEvent event) {
		menuController.getItems(id);
    }
    
}
