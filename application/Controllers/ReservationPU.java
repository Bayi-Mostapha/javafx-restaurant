package application.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReservationPU {
	@FXML
	private TextField cintxt;
	@FXML
	private ChoiceBox<String> periodCB;
	
	public String getCIN() {
		return cintxt.getText();
	}
	public void setCIN(String cin) {
		cintxt.setText(cin);
	}
	
	public String getPeriod() {
		return periodCB.getValue();
	}
	public void setPeriod(String period) {
		periodCB.setValue(period);
	}
	public void setPeriods(String... periods) {
		for (String period : periods) {
			periodCB.getItems().add(period);
        }
	}
}
