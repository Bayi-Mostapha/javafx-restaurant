package application.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class UpdateStaffPU {
	@FXML
	private TextField fnametxt;
	@FXML
	private TextField lnametxt;
	@FXML
	private TextField roletxt;
	@FXML
	private TextField pnumbertxt;
	@FXML
	private DatePicker hdatetxt;
	
	public String getFname() {
		return fnametxt.getText();
	}
	public void setFname(String fname) {
		fnametxt.setText(fname);
	}

	public String getLname() {
		return lnametxt.getText();
	}
	public void setLname(String lname) {
		lnametxt.setText(lname);
	}
	
	public String getRole() {
		return roletxt.getText();
	}
	public void setRole(String role) {
		roletxt.setText(role);
	}
	
	public String getPnumber() {
		return pnumbertxt.getText();
	}
	public void setPnumber(String pNumber) {
		pnumbertxt.setText(pNumber);
	}
	
	public String getHdate() {
		LocalDate selectedDate = hdatetxt.getValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = selectedDate.format(formatter);
		return formattedDate;
	}
	public void setHdate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDate = LocalDate.parse(date, formatter);
		hdatetxt.setValue(parsedDate);
	}
}
