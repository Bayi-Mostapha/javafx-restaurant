package application.Controllers;

import java.io.File;
import java.net.MalformedURLException;

import application.Controllers.classes.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Categorie2C {
	private int id;
	@FXML
	private Label catName;
	@FXML
	private ImageView picture;
	private MenuC menuController;
	
	public void setcategorieData(@SuppressWarnings("exports") Categorie categorie, MenuC menuController) {
		this.id = categorie.getId();
		catName.setText(categorie.getName());
		this.menuController = menuController;
		
		Image image;
		try {
			image = new Image(new File(categorie.getPicture()).toURI().toURL().toExternalForm());
			picture.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		catName.setOnMouseClicked(this::handleCategoryClick);
	}
	
	
	private void handleCategoryClick(MouseEvent event) {
		menuController.getItems(id);
    }
    
}
