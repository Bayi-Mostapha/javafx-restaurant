package application.Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.lang.Double;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UAItemPU {
	@FXML
	private TextField nametxt;
	@FXML
	private TextField pricetxt;
	@FXML
	private ChoiceBox<String> selectCat;
	
	@FXML
	private ImageView picture;
	  
	@FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
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
            Image profileImage = picture.getImage();
            
            LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        String uniqueId = now.format(formatter);
	        String fileName = "item_img_" + uniqueId + ".png";
	        
            String localFilePath = "src/application/images/" + fileName;
            File directory = new File("src/application/images/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File localFile = new File(localFilePath);
            ImageIO.write(SwingFXUtils.fromFXImage(profileImage, null), "png", localFile);

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
	public void setPic(String path) {
		File file = new File(path.replace("file:/", ""));
	    Image image = new Image(file.toURI().toString());
	    picture.setImage(image);
	}
}
