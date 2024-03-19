package application.Controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.MysqlConnection;
import application.Controllers.classes.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CatRow {
	int id;
	
	@FXML
	private Label catname;
	
	@FXML
	private ImageView picture;
	
	private UpdateCatsPU pu;
	
	public void setCategorieData(@SuppressWarnings("exports") Categorie categorie, UpdateCatsPU pu) {
		id = categorie.getId();
		
		catname.setText(categorie.getName());
		
		Image image;
		try {
			image = new Image(new File(categorie.getPicture()).toURI().toURL().toExternalForm());
			picture.setImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		this.pu = pu;
	}

	
	@FXML
	public void delete(){
		Connection connection = MysqlConnection.getDBConnection();
		String sql = "DELETE FROM `categories` WHERE id = ?"; 
        PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			pu.initialize(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void update(){
		pu.cattxt.setText(catname.getText());
		pu.setPic(picture.getImage().getUrl());
		pu.catid=id;
	}
}
