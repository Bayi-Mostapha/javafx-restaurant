package application.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import application.MysqlConnection;
import application.Controllers.classes.StaffMember;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class StaffRowC {
	int id;
	String fname;
	String lname;
	@FXML
    private Label sName;
    @FXML
    private Label role;
    @FXML
    private Label pNumber;
    @FXML
    private Label hDate;
    
    private StaffC staffC;
    

    public void setStaffMemberData(@SuppressWarnings("exports") StaffMember staffMember, StaffC staffC) {
    	this.id = staffMember.getId();
    	this.fname = staffMember.getFname();
    	this.lname = staffMember.getLname();
    	
    	sName.setText(staffMember.getFname() + " " +staffMember.getLname());
        role.setText(staffMember.getRole());
        pNumber.setText(String.valueOf(staffMember.getPhone_number()));
        hDate.setText(staffMember.getHire_date());
        
        this.staffC = staffC;
    }
    
    @FXML
    public void delete() {
    	try {
            Connection connection = MysqlConnection.getDBConnection();
            
            if (connection != null) {
                String sql = "DELETE FROM `staff` WHERE id = ?"; 
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, this.id);
                ps.execute();
            } else {
                System.err.println("Failed to establish a database connection!");
            }
            staffC.initialize(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void update() {
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/application/layout/updateStaffPU.fxml"));
			DialogPane popup = fxmlloader.load();
			
			UpdateStaffPU update = fxmlloader.getController();
			
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(popup);
			dialog.setTitle("update" + " " + sName.getText());
			
			update.setFname(fname);
			update.setLname(lname);
			update.setRole(role.getText());
			update.setPnumber(pNumber.getText());
			update.setHdate(hDate.getText());
			
			Optional<ButtonType> clickedBtn = dialog.showAndWait();
			
			if(clickedBtn.get() == ButtonType.OK) {
				try {
					Connection connection = MysqlConnection.getDBConnection();
					
					String sql = "UPDATE `staff` SET first_name = ?, last_name = ?, role = ?, phone_number = ?, hire_date = ? WHERE id = ?"; 
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, update.getFname());
					ps.setString(2, update.getLname());
					ps.setString(3, update.getRole());
					ps.setString(4, update.getPnumber());
					ps.setString(5, update.getHdate());
					ps.setInt(6, id);
					
					ps.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			staffC.initialize(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
