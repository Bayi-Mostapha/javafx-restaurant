package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import application.MysqlConnection;
import application.Controllers.classes.StaffMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StaffC implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox;
    
    ObservableList<StaffMember> staff = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            contentVBox.setMinWidth(newValue.getWidth());
            contentVBox.setMinHeight(newValue.getHeight());
        });
        
        getAll();
        contentVBox.getChildren().clear();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/staff-1-row.fxml"));
        Parent StaffMemberNode;
		try {
			StaffMemberNode = loader.load();
			contentVBox.getChildren().add(StaffMemberNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        for (StaffMember StaffMember : staff) {
            try {
                loader = new FXMLLoader(getClass().getResource("/application/layout/staff-row.fxml"));
                StaffMemberNode = loader.load();

                StaffRowC StaffMemberController = loader.getController();
                StaffMemberController.setStaffMemberData(StaffMember, this);

                contentVBox.getChildren().add(StaffMemberNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAll() {
        staff.clear();
        try {
            Connection connection = MysqlConnection.getDBConnection();
            
            if (connection != null) {
                String sql = "SELECT * FROM `staff`"; 
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet results = ps.executeQuery();
                
                while (results.next()) {
                	int id = results.getInt("id");
                    String fname = results.getString("first_name");
                    String lname = results.getString("last_name");
                    String role = results.getString("role");
                    String phone_number = results.getString("phone_number");
                    String hire_date = results.getString("hire_date");
                    staff.add(new StaffMember(id, fname, lname, role, phone_number, hire_date));
                }
            } else {
                System.err.println("Failed to establish a database connection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void addNew() {
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/application/layout/updateStaffPU.fxml"));
			DialogPane popup = fxmlloader.load();
			
			UpdateStaffPU update = fxmlloader.getController();
			
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(popup);
			dialog.setTitle("create new employee");
			
			Optional<ButtonType> clickedBtn = dialog.showAndWait();
			
			if(clickedBtn.get() == ButtonType.OK) {
				//validate number
				try {
					Connection connection = MysqlConnection.getDBConnection();
					
					String sql = "INSERT INTO `staff`(`first_name`, `last_name`, `role`, `phone_number`, `hire_date`) VALUES(?,?,?,?,?)"; 
					PreparedStatement ps = connection.prepareStatement(sql);
					
					ps.setString(1, update.getFname());
					ps.setString(2, update.getLname());
					ps.setString(3, update.getRole());
					ps.setString(4, update.getPnumber());
					ps.setString(5, update.getHdate());
					
					ps.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			initialize(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
