package application.Controllers;

import java.io.IOException;

import application.Controllers.classes.SelectedItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuSelectedItemC {
	private int id;
	@FXML
	private Label SIName;
	@FXML
	private Label SIPrice;
	@FXML
	private Label SIQtt;
	private int qtt;
	
	private MenuC menuC;
	
	public void setItemData(@SuppressWarnings("exports") SelectedItem item, MenuC menuC) {
		id = item.getItemId();
		SIName.setText(item.getItemName());
		SIPrice.setText(String.valueOf(item.getItemPrice()));
		qtt = item.getQuantity();
		SIQtt.setText(String.valueOf(qtt));
		this.menuC = menuC;
	}
	
	@FXML 
	public void plusQtt() throws IOException {
		qtt++;
		SIQtt.setText(String.valueOf(qtt));
		menuC.setQtt(id, qtt);
	}
	
	@FXML 
	public void minusQtt() throws IOException {
		if(qtt > 1) {
			qtt--;
			SIQtt.setText(String.valueOf(qtt));
			menuC.setQtt(id, qtt);
		}
	}
	
	@FXML
	public void removeItem() {
		menuC.removeItem(id);
	}
}
