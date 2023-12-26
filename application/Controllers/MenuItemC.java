package application.Controllers;


import application.Controllers.classes.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MenuItemC {
	private int id;
	@FXML
	private Label itemName;
	@FXML
	private Label itemPrice;
	@FXML 
	private HBox addBtn;
	//@FXML
	private MenuC menuController;
	
	public void setItemData(@SuppressWarnings("exports") Item item, MenuC menuController) {
		itemName.setText(item.getName());
		itemPrice.setText(String.valueOf(item.getPrice()));
		id=item.getId();
		this.menuController = menuController;
		
		addBtn.setOnMouseClicked(this::handleCategoryClick);
	}
	
	
	private void handleCategoryClick(MouseEvent event) {
		menuController.addToSelected(id);
    }
}
