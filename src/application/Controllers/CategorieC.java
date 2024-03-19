package application.Controllers;

import java.io.File;
import java.net.MalformedURLException;

import application.Controllers.classes.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CategorieC {
	private int id;
	@FXML
	private ImageView picture;
	@FXML
	private Label catName;
	@FXML
	private ItemsC itemsController;
	
	public void setcategorieData(@SuppressWarnings("exports") Categorie categorie, ItemsC itemsController) {
		this.id = categorie.getId();
		catName.setText(categorie.getName());
		
		Image image;
		try {
			image = new Image(new File(categorie.getPicture()).toURI().toURL().toExternalForm());
			picture.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		this.itemsController = itemsController;
		
		catName.setOnMouseClicked(this::handleCategoryClick);
	}
	
	
	private void handleCategoryClick(MouseEvent event) {
        itemsController.getItems(id);
    }
    
}
