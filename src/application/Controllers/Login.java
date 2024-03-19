package application.Controllers;


import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import application.Main;
import application.MysqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {

    private Connection connect;
    private ResultSet result;

    @FXML
    private TextField txtname;

    @FXML
    private PasswordField txtpass;

    @FXML
    private Button btnok;

    @FXML
    void login(ActionEvent event) {

        String sql = "SELECT * FROM users WHERE first_name = ?";
        connect = MysqlConnection.getDBConnection();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setString(1, txtname.getText());
            result = preparedStatement.executeQuery();

            Alert alert;

            if (txtname.getText().isEmpty() || txtpass.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    // Retrieve the stored hashed password and salt from the database
                    String storedHashedPassword = result.getString("password");
                    //String storedSalt = result.getString("salt"); // Uncomment if using a salt

                    // Hash the entered password with the stored salt (if using a salt)
                    String enteredPassword = hashPassword(txtpass.getText() /*, storedSalt */);
                    System.out.println("Entered Password: " + enteredPassword);


                    // Compare the hashed passwords
                    if (storedHashedPassword.equals(enteredPassword)) {
                    	alert = new Alert(AlertType.INFORMATION);
                    	alert.setTitle("Information Message");
                    	alert.setHeaderText(null);
                    	alert.setContentText("Successfully Login!");

                    	Optional<ButtonType> btnresult = alert.showAndWait();
                    	if (btnresult.isPresent() && btnresult.get() == ButtonType.OK && result.getInt("id") == 1) {
                    		Main.changeScene("main.fxml", false);
                    	} else {
                    		Main.changeScene("kitchen.fxml", true);
                    	}
                    } else {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong Username/Password");
                        alert.showAndWait();
                    }
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                } 
            }

        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging purposes

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);

            if (e instanceof SQLException) {
                alert.setContentText("Database error. Please check your input and try again.");
            } else {
                alert.setContentText("An unexpected error occurred. Please try again later.");
            }

            alert.showAndWait();
        }

    }

    private String hashPassword(String password /*, String salt */) {
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
}