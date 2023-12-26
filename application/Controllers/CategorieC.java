package application.Controllers;

import application.Controllers.classes.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CategorieC {
	private int id;
	@FXML
	private Label catName;
	@FXML
	private ItemsC itemsController;
	
	public void setcategorieData(@SuppressWarnings("exports") Categorie categorie, ItemsC itemsController) {
		this.id = categorie.getId();
		catName.setText(categorie.getName());
		this.itemsController = itemsController;
		
		catName.setOnMouseClicked(this::handleCategoryClick);
	}
	
	
	private void handleCategoryClick(MouseEvent event) {
        itemsController.getItems(id);
    }
    
}
