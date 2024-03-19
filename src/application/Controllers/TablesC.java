package application.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

import application.MysqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;

public class TablesC implements Initializable {
	@FXML
	private ChoiceBox<String> periodFilterCB;
	@FXML
	private Button T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12;
	@FXML
	private ImageView t1p, t2p, t3p, t4p, t5p, t6p, t7p, t8p, t9p, t10p, t11p, t12p;
	
	String currP;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ZoneId zoneId = ZoneId.of("Etc/UTC");
		LocalTime currentTime = LocalTime.now(zoneId);
        
        int hour = currentTime.getHour();
        
        String period = "10:00-13:00";
        if(hour<12) {
        	periodFilterCB.getItems().addAll("10:00-13:00","13:00-16:00","16:00-19:00","19:00-22:00","22:00-00:00");
        	periodFilterCB.setValue("10:00-13:00");
        }
        else if(hour<15) {
        	periodFilterCB.getItems().addAll("13:00-16:00","16:00-19:00","19:00-22:00","22:00-00:00");
        	periodFilterCB.setValue("13:00-16:00");
        	period = "13:00-16:00";
        }
        else if(hour<18) {
        	periodFilterCB.getItems().addAll("16:00-19:00","19:00-22:00","22:00-00:00");
        	periodFilterCB.setValue("16:00-19:00");
        	period = "16:00-19:00";
        }
        else if(hour<21) {
        	periodFilterCB.getItems().addAll("19:00-22:00","22:00-00:00");
        	periodFilterCB.setValue("19:00-22:00");
        	period = "19:00-22:00";
        }
        else if(hour<23) {
        	periodFilterCB.getItems().addAll("22:00-00:00");
        	periodFilterCB.setValue("22:00-00:00");
        	period = "22:00-00:00";
        	
        }
        getTables(period);
        
        periodFilterCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getTables(newValue);
                currP=newValue;
            }
        });
	}
	
	public void getTables(String period) {
		try {
	        Connection connection = MysqlConnection.getDBConnection();
	        
	        if (connection != null) {
	        	String[] splitTimes = period.split("-");
	        	String startTime = splitTimes[0];
	        	String sql = "SELECT table_name FROM reservations WHERE date = CURDATE() AND start_time = ?";
	        	PreparedStatement ps = connection.prepareStatement(sql);
	            ps.setString(1, startTime + ":00");
	            ResultSet results = ps.executeQuery();
	            
	            String imagePath = "file:src/application/icons/free.png";
	            String imagePath2 = "file:src/application/icons/reserved.png";
            	
	            t1p.setImage(new Image(imagePath));
	            t2p.setImage(new Image(imagePath));
	            t3p.setImage(new Image(imagePath));
	            t4p.setImage(new Image(imagePath));
	            t5p.setImage(new Image(imagePath));
	            t6p.setImage(new Image(imagePath));
	            t7p.setImage(new Image(imagePath));
	            t8p.setImage(new Image(imagePath));
	            t9p.setImage(new Image(imagePath));
	            t10p.setImage(new Image(imagePath));
	            t11p.setImage(new Image(imagePath));
	            t12p.setImage(new Image(imagePath));
	            
	            while (results.next()) {
	            	String tableName = results.getString("table_name");
	            	if (tableName.equals("T1")) {
	                    t1p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T2")) {
	                    t2p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T3")) {
	                    t3p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T4")) {
	                    t4p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T5")) {
	                    t5p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T6")) {
	                    t6p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T7")) {
	                    t7p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T8")) {
	                    t8p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T9")) {
	                    t9p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T10")) {
	                    t10p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T11")) {
	                    t11p.setImage(new Image(imagePath2));
	                } else if (tableName.equals("T12")) {
	                    t12p.setImage(new Image(imagePath2));
	                }

	            }
	        } else {
	            System.err.println("Failed to establish a database connection!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	public void handleClick(@SuppressWarnings("exports") ActionEvent event) {
	    Button clickedBtn = (Button) event.getSource();

	    if (clickedBtn == T1) {
	        addReservation("T1");
	    } else if (clickedBtn == T2) {
	        addReservation("T2");
	    } else if (clickedBtn == T3) {
	        addReservation("T3");
	    } else if (clickedBtn == T4) {
	        addReservation("T4");
	    } else if (clickedBtn == T5) {
	        addReservation("T5");
	    } else if (clickedBtn == T6) {
	        addReservation("T6");
	    } else if (clickedBtn == T7) {
	        addReservation("T7");
	    } else if (clickedBtn == T8) {
	        addReservation("T8");
	    } else if (clickedBtn == T9) {
	        addReservation("T9");
	    } else if (clickedBtn == T10) {
	        addReservation("T10");
	    } else if (clickedBtn == T11) {
	        addReservation("T11");
	    } else if (clickedBtn == T12) {
	        addReservation("T12");
	    }
	}

    public void addReservation(String table) {
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/application/layout/ReservationPU.fxml"));
			DialogPane popup = fxmlloader.load();
			
			ReservationPU ua = fxmlloader.getController();
			
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(popup);
			dialog.setTitle(table);
			
			ZoneId zoneId = ZoneId.of("Etc/UTC");
			LocalTime currentTime = LocalTime.now(zoneId);
	        
	        int hour = currentTime.getHour();
	        
	        if(hour<12) {
	        	ua.setPeriods("10:00-13:00","13:00-16:00","16:00-19:00","19:00-22:00","22:00-00:00");
				ua.setPeriod("10:00-13:00");
	        }
	        else if(hour<15) {
	        	ua.setPeriods("13:00-16:00","16:00-19:00","19:00-22:00","22:00-00:00");
				ua.setPeriod("13:00-16:00");
	        }
	        else if(hour<18) {
	        	ua.setPeriods("16:00-19:00","19:00-22:00","22:00-00:00");
				ua.setPeriod("16:00-19:00");
	        }
	        else if(hour<21) {
	        	ua.setPeriods("19:00-22:00","22:00-00:00");
				ua.setPeriod("19:00-22:00");
	        }
	        else if(hour<23) {
	        	ua.setPeriods("22:00-00:00");
				ua.setPeriod("22:00-00:00");
	        }
	        ua.setPeriod(currP);
			
			Optional<ButtonType> clickedBtn = dialog.showAndWait();
			
			if(clickedBtn.get() == ButtonType.OK) {
				try {
					Connection connection = MysqlConnection.getDBConnection();
					
					String sql = "INSERT INTO `reservations` (`start_time`, `end_time`, `date`, `clientCIN`,  `table_name`) VALUES(?, ?, CURDATE(), ?, ?)"; 
					PreparedStatement ps = connection.prepareStatement(sql);
					
					String[] times = ua.getPeriod().split("-");
					ps.setString(1, times[0]);
					ps.setString(2, times[1]);
					ps.setString(3, ua.getCIN());
					ps.setString(4, table);
					try {
						ps.execute();
					} catch (SQLIntegrityConstraintViolationException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Reservation failed");
						alert.setHeaderText("Table reserved");
						alert.setContentText("Table " + table + " is already reserved, choose another table!!");
						
						alert.showAndWait();
						return;
					}
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Reserved successfully");
					alert.setHeaderText("Table " + table + " is reserved today at " + ua.getPeriod());
					
					alert.showAndWait();
					getTables(times[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
