module TableViewSQL2023 {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application.Controllers to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml,javafx.base;
	exports application.Controllers;
}
