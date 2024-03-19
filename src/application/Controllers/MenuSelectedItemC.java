package application.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import application.Controllers.classes.SelectedItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuSelectedItemC {
	private int id;
	@FXML
	private ImageView picture;
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
		
		Image image;
		try {
			image = new Image(new File(item.getPicture()).toURI().toURL().toExternalForm());
			picture.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
