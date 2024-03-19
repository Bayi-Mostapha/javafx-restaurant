package application.Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;



public class Settings {

    @FXML
    private ImageView profilePicture;

    @FXML
    private Button uploadimg;

    @FXML
    private Button remove;

    @FXML
    private TextField curpass;

    @FXML
    private TextField newpass;

    @FXML
    private TextField conpass;

    @FXML
    private Button savebtn;

    @FXML
    private TextField firstNameLabel;

    @FXML
    private TextField lastNameLabel;

    private static final String DB_NAME = "restaurant";
    private static final String DB_IP = "127.0.0.1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // User ID placeholder, replace with actual user ID
    private int userId = 1;
    private boolean flag = false ;

    @FXML
    private void initialize() {
        // Load data from the database when the Settings controller is initialized
        loadDataFromDatabase();
    }

    @FXML
    private void loadDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String imagePath = resultSet.getString("picture");

                        firstNameLabel.setText(firstName != null ? firstName : "");
                        lastNameLabel.setText(lastName != null ? lastName : "");

                        if (imagePath != null && !imagePath.isEmpty()) {
                            // Use the local path stored in the database
                            try {
                                Image image = new Image(new File(imagePath).toURI().toURL().toExternalForm());
                                profilePicture.setImage(image);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   private void updateDataInDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE users SET first_name = ?, last_name = ?, picture = ? WHERE id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, firstNameLabel.getText());
                updateStatement.setString(2, lastNameLabel.getText());
               
                // Get the local path from the image URL
                String imagePath = profilePicture.getImage().getUrl();
                String localPath = saveProfilePictureLocally();

                // Move the uploaded picture to the application/images folder
                moveUploadedPicture(localPath, imagePath);

                updateStatement.setString(3, localPath);
                updateStatement.setInt(4, userId);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
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
            // Set the image path to the selected file
            profilePicture.setImage(new Image(imagePath));
            flag = true;
        }
    }

    @FXML
   private void saveChanges() {
        // Check if the password fields are not empty
        String currentPassword = curpass.getText();
        String newPassword = newpass.getText();
        String confirmPassword = conpass.getText();

        if (!currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()|| flag) {
            // Hash the current password before comparing
            String hashedCurrentPassword = hashPassword(currentPassword);

            // Check if the current password is correct
            if (isCurrentPasswordCorrect(hashedCurrentPassword) || flag) {
                // Check if the new password matches the confirm password
                if (newPassword.equals(confirmPassword) || flag) {
                
                    // Hash the new password before storing it
                    String hashedNewPassword = hashPassword(newPassword);

                    // Save image locally and update image path in the database
                    String imagePath = saveProfilePictureLocally();

                    // Update password and image path in the database
                    updatePasswordAndImageInDatabase(hashedNewPassword, imagePath);

                    showAlert("Password and Image Updated", "Password and image updated successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Password Mismatch", "New password and confirm password do not match.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Incorrect Password", "Incorrect current password.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Empty Fields", "Please fill in all password fields.", Alert.AlertType.ERROR);
        }

        // Update other data in the database
        updateDataInDatabase();
    }

  private String saveProfilePictureLocally() {
        try {
            // Get the image from the ImageView
            Image profileImage = profilePicture.getImage();

            // Generate a unique filename for the image
            String fileName = "profile_image_.png";

            // Define the local file path
            String localFilePath = "src/application/images/" + fileName;

            // Create the directory if it doesn't exist
            File directory = new File("src/application/images/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the image to the local file system
            File localFile = new File(localFilePath);
            ImageIO.write(SwingFXUtils.fromFXImage(profileImage, null), "png", localFile);

            // Return the local file path
            return localFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

  private void moveUploadedPicture(String localPath, String imagePath) {
	    try {
	        // Get the source file and destination file
	        File sourceFile = new File(imagePath.replace("file:/", ""));
	        File destinationFile = new File(localPath);

	        // Move the file to the destination folder
	        Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
  }

    
    private void updatePasswordAndImageInDatabase(String newPassword, String imagePath) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE users SET password = ?, picture = ? WHERE id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, imagePath);
                updateStatement.setInt(3, userId);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password ) {
        try {
            

            // Comment the following line if using a salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert bytes to hexadecimal representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private boolean isCurrentPasswordCorrect(String currentPassword) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE id = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setString(2, currentPassword);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Returns true if a matching row is found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

   
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}