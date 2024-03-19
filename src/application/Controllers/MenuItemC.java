package application.Controllers;


import java.io.File;
import java.net.MalformedURLException;

import application.Controllers.classes.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MenuItemC {
	private int id;
	@FXML
	private ImageView picture;
	@FXML
	private Label itemName;
	@FXML
	private Label itemPrice;
	@FXML 
	private HBox addBtn;
	
	private MenuC menuController;
	
	
	public void setItemData(@SuppressWarnings("exports") Item item, MenuC menuController) {
		itemName.setText(item.getName());
		itemPrice.setText(String.valueOf(item.getPrice()));
		id=item.getId();
		this.menuController = menuController;
		
		Image image;
		try {
			image = new Image(new File(item.getPicture()).toURI().toURL().toExternalForm());
			picture.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		addBtn.setOnMouseClicked(this::handleCategoryClick);
	}
	
	
	private void handleCategoryClick(MouseEvent event) {
		menuController.addToSelected(id);
    }
}
