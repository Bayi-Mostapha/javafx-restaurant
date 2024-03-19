package application.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import application.MysqlConnection;
import application.Controllers.classes.Categorie;

public class UpdateCatsPU implements Initializable {
	int catid=0;
	
	@FXML 
	TextField cattxt;
	
	@FXML
	private ImageView picture;
	
	@FXML
	private VBox categoriesContainer;
	
	public String getName() {
		return cattxt.getText();
	}
	  
	public void setPic(String path) {
		Image image;
		try {
		    URL url = new URL(path);
		    image = new Image(url.toExternalForm());
		    picture.setImage(image);
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		}

	}
	
	@FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            picture.setImage(new Image(imagePath));
        }
    }
	
	public String savePic() {
		String localPath;
		localPath = savePictureLocally();
		moveUploadedPicture(localPath, picture.getImage().getUrl());
		return localPath;
	}

	private String savePictureLocally() {
        try {
            Image image = picture.getImage();
            
            LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        String uniqueId = now.format(formatter);
	        String fileName = "cat_img_" + uniqueId + ".png";
	        
            String localFilePath = "src/application/images/" + fileName;
            File directory = new File("src/application/images/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File localFile = new File(localFilePath);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", localFile);

            return localFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	  private void moveUploadedPicture(String localPath, String imagePath) {
	    try {
	        File sourceFile = new File(imagePath.replace("file:/", ""));
	        File destinationFile = new File(localPath);
	
	        Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	  }
	  
	  //***********************************************************************
		
		ObservableList<Categorie> categories = FXCollections.observableArrayList();

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			getAllCats();
			categoriesContainer.getChildren().clear();
			
		    for (Categorie categorie : categories) {
		        try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/catRow.fxml"));
		            Parent categorieNode = loader.load();
		
		            CatRow categorieController = loader.getController();
		            categorieController.setCategorieData(categorie, this);
		
		            categoriesContainer.getChildren().add(categorieNode);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}

		public void getAllCats() {
		    categories.clear();
		    try {
		        Connection connection = MysqlConnection.getDBConnection();
		        
		        if (connection != null) {
		            String sql = "SELECT * FROM `categories`"; 
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ResultSet results = ps.executeQuery();
		            
		            while (results.next()) {
		            	int id = results.getInt("id");
		                String name = results.getString("name");
		                String picture = results.getString("picture");
		                categories.add(new Categorie(id, name, picture));
		            }
		        } else {
		            System.err.println("Failed to establish a database connection!");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
}
