package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.Parent;

public class Main extends Application {
    private static Stage primaryStage; // Static variable to hold the stage reference

    @Override
    public void start(Stage primaryStage) {
        try {
            Main.primaryStage = primaryStage; // Store the reference
            Parent root = FXMLLoader.load(getClass().getResource("layout/LoginScene.fxml"));
            Scene scene = new Scene(root, 1000, 700); 
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxmlFileName, boolean isFullScreen) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("layout/" + fxmlFileName));
            Scene scene = new Scene(root, 1000, 700);
            primaryStage.setScene(scene);
            
            if (isFullScreen) {
                primaryStage.setFullScreen(true);
                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        primaryStage.setFullScreen(false);
                    }
                });
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
