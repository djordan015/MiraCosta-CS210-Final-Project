package cs210.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddLocationController {

    @FXML
    private Button enter;
    @FXML
    private Label cityPrompt;
    @FXML
    private TextField cityField;

    @FXML
    public void switchToMainView(ActionEvent event) throws IOException {

        Weather currentWeather =  new Weather();

        currentWeather.generateWeather(cityField.getText());

        if(currentWeather.isValid()) {
            System.out.println("Module: " + MainController.getModule());
            MainController.setWeatherCity(cityField.getText());
            Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        }
        else{
            System.out.println("ERROR");
            cityPrompt.setText("ERROR: Enter a valid city");
        }
    }

    public void enterButtonPressed(){

    }
}