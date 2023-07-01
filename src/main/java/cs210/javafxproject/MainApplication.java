package cs210.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
//        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApplication.class.getResource("AddLocationScene.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Weather App");
        stage.setScene(scene);
        stage.setResizable(false); //disable Resizable window
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("[OUTPUT USED FOR DEBUGGING PURPOSES]");
        launch();
    }
}
