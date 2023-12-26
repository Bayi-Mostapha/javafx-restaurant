package application.Controllers;

import javafx.fxml.FXML;
import java.lang.Double;
import java.util.ArrayList;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UAItemPU {
	@FXML
	private TextField nametxt;
	@FXML
	private TextField pricetxt;
	@FXML
	private ChoiceBox<String> selectCat;
	
	public String getName() {
		return nametxt.getText();
	}
	public void setName(String name) {
		nametxt.setText(name);
	}
	
	public Double getPrice() {
		return Double.parseDouble(pricetxt.getText());
	}
	public void setPrice(Double price) {
		pricetxt.setText(String.valueOf(price));
	}
	
	public String getCategorie() {
		return selectCat.getValue();
	}
	public void setCategorie(String categorie) {
		selectCat.setValue(categorie);
	}
	public void setCategories(ArrayList<String> categories) {
		selectCat.getItems().addAll(categories);
	}
}
