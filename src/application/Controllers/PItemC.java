package application.Controllers;

import java.io.File;
import java.net.MalformedURLException;

import application.Controllers.classes.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PItemC {
	@FXML
    private Label itemname;
    
    @FXML
    private Label itemprice;
    
    @FXML
	private ImageView picture;
    
   
    public void setData(@SuppressWarnings("exports") Item item) {
    	itemname.setText(item.getName());
    	itemprice.setText(String.valueOf(item.getPrice()));
    	
    	Image image;
		try {
			image = new Image(new File(item.getPicture()).toURI().toURL().toExternalForm());
			picture.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
}
