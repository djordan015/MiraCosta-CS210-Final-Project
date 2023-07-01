package cs210.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DrivingConditionsController {

    @FXML
    private Button back;

    @FXML
    public void switchToSevenDayForecastView(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SevenDayForecastView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 350);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}
