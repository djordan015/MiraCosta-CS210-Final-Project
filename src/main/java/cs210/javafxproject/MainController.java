package cs210.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static int module = 0;
    private Weather weather0 = new Weather();
    private Weather weather1 = new Weather();
    private Weather weather2 = new Weather();

    private static String weather0City = "null", weather1City = "null", weather2City = "null";

    @FXML
    private Button addButton0, addButton1, addButton2;
    @FXML
    private TextField AddLocationTextField;
    @FXML
    private Button weekSunRiseSunSetButton0, weekSunRiseSunSetButton1, weekSunRiseSunSetButton2;
    @FXML
    private Button sevenDayForecastButton0, sevenDayForecastButton1, sevenDayForecastButton2;
    @FXML
    private Button clear0, clear1, clear2;
    @FXML
    private Label addLabel0, addLabel1, addLabel2;
    @FXML
    private ImageView weatherIcon0, weatherIcon1, weatherIcon2;
    @FXML
    private Label description0, description1, description2;
    @FXML
    private ImageView timeOfDayIcon0, timeOfDayIcon1, timeOfDayIcon2;
    @FXML
    private Label location0, location1, location2;
    @FXML
    private Label temp0, temp1, temp2;
    @FXML
    private Label humid0, humid1, humid2;
    @FXML
    private Label visibility0, visibility1, visibility2;
    @FXML
    private Label sunRise0, sunRise1, sunRise2;
    @FXML
    private Label sunSet0, sunSet1, sunSet2;
    @FXML
    private Label sunRiseIcon0, sunRiseIcon1, sunRiseIcon2;
    @FXML
    private Label sunSetIcon0, sunSetIcon1, sunSetIcon2;
    @FXML
    private Label date0, date1, date2;

    @FXML
    protected boolean onAddButton0Click() {
        Weather cool = new Weather();
        cool.generateWeather("Riverside,us");

        System.out.printf(cool.toString());
        return true;
    }

    @FXML
    private Button enter;


    @FXML
    public void switchToAddLocationView(ActionEvent event) throws IOException {
        Button tempButton = (Button) event.getSource();
//        System.out.println("You pressed: " + tempButton.getId());
        String id = tempButton.getId();
        if(tempButton.getId().equals("addButton0")){
            module = 0;
        }
        else if(tempButton.getId().equals("addButton1")){
            module = 1;
        }
        else if(tempButton.getId().equals("addButton2")){
            module = 2;
        }
        else{
            System.out.println("bug");
        }

        root = FXMLLoader.load(getClass().getResource("AddLocationView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 425, 275);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToSevenDayForecastView(ActionEvent event) throws IOException {

        Button tempButton = (Button) event.getSource();
//        System.out.println("You pressed: " + tempButton.getId());
//        String id = tempButton.getId();
        if(tempButton.getId().equals("sevenDayForecastButton0")){
            SevenDayForecastController.setForecast(weather0, weather0City);
        }
        else if(tempButton.getId().equals("sevenDayForecastButton1")){
            SevenDayForecastController.setForecast(weather1, weather1City);
        }
        else if(tempButton.getId().equals("sevenDayForecastButton2")){
            SevenDayForecastController.setForecast(weather2, weather2City);
        }
        else{
            System.out.println("bug");
        }

        root = FXMLLoader.load(getClass().getResource("SevenDayForecastView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 600, 350);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static int getModule(){ return module;}

    public static void setModule(int mod){ module = mod;}

    public static void setWeatherCity(String city){
        System.out.println("setting weather city to: " + city);
        if (module == 0){
            weather0City = city;
        }
        else if(module == 1){
            weather1City = city;
        }
        else if(module == 2){
            weather2City = city;

        }
    }

    private void getWeatherIcon(int panel, String description){

        if(description.contains("clear sky")){
            String file = "src/main/resources/cs210/javafxproject/pics/sun_125x125_mainmenu.png";
            setWeatherIcon(file, panel);
        }
        else if(description.contains("few clouds")){
            String file = "src/main/resources/cs210/javafxproject/pics/partCloudy_125x125_mainmenu.png";
            setWeatherIcon(file, panel);
        }
        else if(description.contains("overcast clouds") || description.contains("broken clouds")  || description.contains("scattered clouds") || description.contains("haze")){
            String file = "src/main/resources/cs210/javafxproject/pics/clouds_125x125_mainmenu.png";
            setWeatherIcon(file, panel);
        }
        else if(description.contains("rain")){
            String file = "src/main/resources/cs210/javafxproject/pics/rain_125x125_mainmenu.png";
            setWeatherIcon(file, panel);
        }

    }
    private void setWeatherIcon(String fileName, int panel){
        File file = new File(fileName);
        Image tempImage = new Image(file.toURI().toString());

        if (panel == 0){
            weatherIcon0.setImage(tempImage);
        }
        else if (panel == 1){
            weatherIcon1.setImage(tempImage);
        }
        else if (panel == 2){
            weatherIcon2.setImage(tempImage);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize");
        if(weather0City.compareTo("null") != 0){
            if(!weather0.set) {
//                System.out.println("Setting weather0");
                weather0.generateWeather(weather0City);
                addButton0.setVisible(false);
                temp0.setText(weather0.getCurrentTemp() + "˚F");

                humid0.setText(weather0.getHumidity() + "% Humidity");
                visibility0.setText(weather0.getVisibility() + "% Visibility");
                date0.setText(weather0.setDate());

                location0.setText(weather0City + ", " + weather0.getCountry());
                addLabel0.setVisible(false);
                clear0.setVisible(true);

                sunRise0.setText(weather0.ConvertSunRiseSunSet(weather0.getSunRise()));
                sunSet0.setText(weather0.ConvertSunRiseSunSet(weather0.getSunSet()));

//                weekSunRiseSunSetButton0.setVisible(true);
                sevenDayForecastButton0.setVisible(true);
                weatherIcon0.setVisible(true);
                timeOfDayIcon0.setVisible(true);
                sunRiseIcon0.setVisible(true);
                sunSetIcon0.setVisible(true);

                sunRiseIcon0.setText("⬆ \nSunrise");
                sunSetIcon0.setText("⬇ \nSunset");

                getWeatherIcon(0, weather0.getDescription());
                description0.setText(weather0.getDescription());
            }
        }
        else{
            System.out.println("Panel:0 no city set");
        }
        //save
        if(weather1City.compareTo("null") != 0){
//            System.out.println("Setting weather1");
            if(!weather1.set) {
//                System.out.println("Setting weather1");
                weather1.generateWeather(weather1City);
                addButton1.setVisible(false);
                temp1.setText(weather1.getCurrentTemp() + "˚F");

                humid1.setText(weather1.getHumidity() + "% Humidity");
                visibility1.setText(weather1.getVisibility() + "% Visibility");
                date1.setText(weather1.setDate());

                location1.setText(weather1City + ", " + weather1.getCountry());
                addLabel1.setVisible(false);
                clear1.setVisible(true);

                sunRise1.setText(weather1.ConvertSunRiseSunSet(weather1.getSunRise()));
                sunSet1.setText(weather1.ConvertSunRiseSunSet(weather1.getSunSet()));

//                weekSunRiseSunSetButton1.setVisible(true);
                sevenDayForecastButton1.setVisible(true);
                weatherIcon1.setVisible(true);
                timeOfDayIcon1.setVisible(true);
                sunRiseIcon1.setVisible(true);
                sunSetIcon1.setVisible(true);

                sunRiseIcon1.setText("⬆ \nSunrise");
                sunSetIcon1.setText("⬇ \nSunset");

                //set weather icon
                getWeatherIcon(1, weather1.getDescription());
                description1.setText(weather1.getDescription());
            }
        }
        else{
            System.out.println("Panel:1 no city set");
        }

        if(weather2City.compareTo("null") != 0){
//            System.out.println("Setting weather2");
            if(!weather2.set) {
//                System.out.println("Setting weather2");
                weather2.generateWeather(weather2City);
                addButton2.setVisible(false);
                temp2.setText(weather2.getCurrentTemp() + "˚F");

                humid2.setText(weather2.getHumidity() + "% Humidity");
                visibility2.setText(weather2.getVisibility() + "% Visibility");
                date2.setText(weather2.setDate());

                location2.setText(weather2City + ", " + weather2.getCountry());
                addLabel2.setVisible(false);
                clear2.setVisible(true);

                sunRise2.setText(weather2.ConvertSunRiseSunSet(weather2.getSunRise()));
                sunSet2.setText(weather2.ConvertSunRiseSunSet(weather2.getSunSet()));

//                weekSunRiseSunSetButton2.setVisible(true);
                sevenDayForecastButton2.setVisible(true);
                weatherIcon2.setVisible(true);
                timeOfDayIcon2.setVisible(true);
                sunRiseIcon2.setVisible(true);
                sunSetIcon2.setVisible(true);

                sunRiseIcon2.setText("⬆ \nSunrise");
                sunSetIcon2.setText("⬇ \nSunset");

                getWeatherIcon(2, weather2.getDescription());
                description2.setText(weather2.getDescription());
            }
        }
        else{
            System.out.println("Panel:2 no city set");
        }
    }
}