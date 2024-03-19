package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MysqlConnection;
import application.Controllers.classes.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DashboardC implements Initializable {
	@FXML 
	private Label trevenue;
    @FXML
    private Label norders;
    @FXML
    private Label nmenu;
    @FXML
    private Label nstaff;
    @FXML
    private HBox itemContainer;
    @FXML
    private LineChart<?, ?> totalrevchart;

    @FXML
    private BarChart<?, ?> orderchart;
 
    ObservableList<Item> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
        try {
            Connection connection = MysqlConnection.getDBConnection();

            String sql = "SELECT SUM(quantity * price) AS total FROM orderitems JOIN items ON items.id = orderitems.itemId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                double totalRevenue = results.getDouble("total");
                trevenue.setText(String.valueOf(totalRevenue));
            }

            int numberOfOrders = 0;
            sql = "SELECT COUNT(*) AS n FROM orders";
            ps = connection.prepareStatement(sql);
            results = ps.executeQuery();
            if (results.next()) {
                numberOfOrders = results.getInt("n");
            }
            norders.setText(String.valueOf(numberOfOrders));

            int numberOfItems = 0;
            sql = "SELECT COUNT(*) AS n FROM items";
            ps = connection.prepareStatement(sql);
            results = ps.executeQuery();
            if (results.next()) {
                numberOfItems = results.getInt("n");
            }
            nmenu.setText(String.valueOf(numberOfItems));

            int numberOfStaff = 0;
            sql = "SELECT COUNT(*) AS n FROM staff";
            ps = connection.prepareStatement(sql);
            results = ps.executeQuery();
            if (results.next()) {
                numberOfStaff = results.getInt("n");
            }
            nstaff.setText(String.valueOf(numberOfStaff));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        getAll();
    	itemContainer.getChildren().clear();
    	
        for (Item item : items) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/layout/p-item.fxml"));
                Parent orderNode = loader.load();

                PItemC dashboardItemController = loader.getController();
                dashboardItemController.setData(item);
                
                itemContainer.getChildren().add(orderNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dashboardChart1();
        dashboardChart2();
    }
    
    public void getAll() {
    	items.clear();
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql = 
			"SELECT name, price, picture "
			+ "FROM orders JOIN orderitems ON orders.id = orderitems.orderId "
			+ "JOIN items ON orderitems.itemId = items.id "
			+ "GROUP BY items.id "
			+ "Order by COUNT(orderitems.itemId) LIMIT 3"; 
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				String name = results.getString("name");
				Double price = results.getDouble("price");
				String picture = results.getString("picture");
				
				items.add(new Item(name,price, picture));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void dashboardChart1() {
        totalrevchart.getData().clear();

        String sql = "SELECT MONTHNAME(order_date) AS MonthName, SUM(total) AS TotalPaid "
                + "FROM orders  "
                + "GROUP BY MonthName ORDER BY MONTH(order_date) ASC";

        Connection connection = MysqlConnection.getDBConnection();
        PreparedStatement prepare = null;
        ResultSet results = null;

        XYChart.Series chart = new XYChart.Series();

        try {
            prepare = connection.prepareStatement(sql);
            results = prepare.executeQuery();

            double runningTotal = 0;

            while (results.next()) {
                runningTotal += results.getDouble("TotalPaid");
                String monthName = results.getString("MonthName");
                chart.getData().add(new XYChart.Data(monthName, runningTotal));
            }

            totalrevchart.getData().add(chart);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dashboardChart2() {
        orderchart.getData().clear();

        String sql = "SELECT COUNT(id) AS TotalOrders, MONTHNAME(order_date) AS MonthName "
                + "FROM orders  "
                + "GROUP BY MonthName ORDER BY MONTH(order_date) ASC";

        Connection connection = MysqlConnection.getDBConnection();
        PreparedStatement prepare = null;
        ResultSet results = null;

        XYChart.Series chart = new XYChart.Series();

        try {
            prepare = connection.prepareStatement(sql);
            results = prepare.executeQuery();

            while (results.next()) {
                int totalOrders = results.getInt("TotalOrders");
                String monthName = results.getString("MonthName");
                chart.getData().add(new XYChart.Data(monthName, totalOrders));
            }

            orderchart.getData().add(chart);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}