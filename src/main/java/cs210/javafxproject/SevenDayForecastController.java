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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SevenDayForecastController implements Initializable {

    private static String[] currentStr = new String[40];
    private static String locationStr, forecastCountry;
    private static long timeOfDay1, timeOfDay2, timeOfDay3, timeOfDay4, timeOfDay5;
    private static String[] day1, day2, day3, day4, day5;
    private static String dateConvert1, dateConvert2, dateConvert3, dateConvert4, dateConvert5;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    private Button back;
    @FXML
    private Label high1, high2, high3, high4, high5;
    @FXML
    private Label low1, low2, low3, low4, low5;
    @FXML
    private Label weather1, weather2, weather3, weather4, weather5;
    @FXML
    private Label date1, date2, date3, date4, date5;
    @FXML
    private ImageView weatherForecast1, weatherForecast2, weatherForecast3, weatherForecast4, weatherForecast5;
    @FXML
    private Label lowAverage, highAverage;
    @FXML
    private Label location;

    public static void setForecast(Weather weatherObj, String weatherCity){
//        System.out.println("Testing weather Obj: " + weatherObj.getDescription());
//        Forecast currentForecast = new Forecast(weatherObj);
        Forecast currentForecast = Forecast.getInstance();
        currentForecast.setLat(weatherObj.getLat());
        currentForecast.setLon(weatherObj.getLon());
        currentForecast.setTimeZone(weatherObj.getTimezone());
        String[] resultStr;

        currentForecast.generateForecast();
        resultStr = currentForecast.getResultStr();
        currentStr = resultStr;

        //API provides data for 5 days every 3 hours, 24/3=8 set high/low for each day. 7,15,23,31,39
        day1 = currentStr[7].split(" ");
        day2 = currentStr[15].split(" ");
        day3 = currentStr[23].split(" ");
        day4 = currentStr[31].split(" ");
        day5 = currentStr[39].split(" ");

        locationStr = weatherCity;
        forecastCountry = weatherObj.getCountry();
        //calculate dates
        timeOfDay1 = Long.parseLong(day1[1]);
        dateConvert1 = currentForecast.ConvertTime(timeOfDay1, weatherObj.getTimezone());
        timeOfDay2 = Long.parseLong(day2[1]);
        dateConvert2 = currentForecast.ConvertTime(timeOfDay2, weatherObj.getTimezone());
        timeOfDay3 = Long.parseLong(day3[1]);
        dateConvert3 = currentForecast.ConvertTime(timeOfDay3, weatherObj.getTimezone());
        timeOfDay4 = Long.parseLong(day4[1]);
        dateConvert4 = currentForecast.ConvertTime(timeOfDay4, weatherObj.getTimezone());
        timeOfDay5 = Long.parseLong(day5[1]);
        dateConvert5 = currentForecast.ConvertTime(timeOfDay5, weatherObj.getTimezone());
    }

    private double setHighTemps(int start, int end, Label highLabel){
        String[] findHigh;
        double temp;
        double highest = 0;

        for (int i = start; i < end; i++){
            findHigh = currentStr[i].split(" ");
            temp = Double.parseDouble(findHigh[3]);
            if (temp > highest){
                highest = temp;
            }
        }
        highLabel.setText(highest + " °F");
        return highest;
    }
    private double setLowTemps(int start, int end, Label lowLabel){
        String[] findLow;
        double temp;
        double lowest = 999;

        for (int i = start; i < end; i++){
            findLow = currentStr[i].split(" ");
            temp = Double.parseDouble(findLow[4]);
            if (temp < lowest){
                lowest = temp;
            }
        }
        lowLabel.setText(lowest + " °F");
        return lowest;
    }

    private void getForecastWeatherIcon(int day, String description){

        if(description.contains("Clear")){
            String file = "src/main/resources/cs210/javafxproject/pics/sun_75x60_5ForecastView.jpg";
            setForecastWeatherIcon(file, day);
        }
        else if(description.contains("few clouds")){
            String file = "src/main/resources/cs210/javafxproject/pics/partCloudy_75x60_5ForecastView.png";
            setForecastWeatherIcon(file, day);
        }
        else if(description.contains("Clouds")  || description.contains("Overcast Clouds") || description.contains("Broken Clouds")  || description.contains("Ccattered Clouds") || description.contains("haze")){
            String file = "src/main/resources/cs210/javafxproject/pics/clouds_75x60_5ForecastView.png";
            setForecastWeatherIcon(file, day);
        }
        else if(description.contains("Rain")){
            String file = "src/main/resources/cs210/javafxproject/pics/rain_75x60_5ForecastView.png";
            setForecastWeatherIcon(file, day);
        }
    }
    private void setForecastWeatherIcon(String fileName, int day){

        File file = new File(fileName);
        Image tempImage = new Image(file.toURI().toString());

        if (day == 1){
            weatherForecast1.setImage(tempImage);
        }
        else if (day == 2){
            weatherForecast2.setImage(tempImage);
        }
        else if (day == 3){
            weatherForecast3.setImage(tempImage);
        }
        else if (day == 4){
            weatherForecast4.setImage(tempImage);
        }
        else if (day == 5){
            weatherForecast5.setImage(tempImage);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Forecast initialize: " + Arrays.toString(currentStr));

        double averageHigh = 0;
        double averageLow = 0;

        averageHigh = averageHigh + setHighTemps(0, 7, high1);
        averageLow = averageLow + setLowTemps(0, 7, low1);

        averageHigh = averageHigh + setHighTemps(7, 15, high2);
        averageLow = averageLow + setLowTemps(7, 15, low2);

        averageHigh = averageHigh + setHighTemps(15, 23, high3);
        averageLow = averageLow + setLowTemps(15, 23, low3);

        averageHigh = averageHigh + setHighTemps(23, 31, high4);
        averageLow = averageLow + setLowTemps(23, 31, low4);

        averageHigh = averageHigh + setHighTemps(31, 39, high5);
        averageLow = averageLow + setLowTemps(31, 39, low5);

        averageHigh = averageHigh / 5;
        averageLow = averageLow / 5;

        highAverage.setText(df.format(averageHigh) + " °F");
        lowAverage.setText(df.format(averageLow) + " °F");

        //set location
        location.setText(locationStr + ", " + forecastCountry);

        //set weatherForecastIcons
        getForecastWeatherIcon(1, day1[2]);
        getForecastWeatherIcon(2, day2[2]);
        getForecastWeatherIcon(3, day3[2]);
        getForecastWeatherIcon(4, day4[2]);
        getForecastWeatherIcon(5, day5[2]);

        //set dates
        date1.setText(dateConvert1);
        date2.setText(dateConvert2);
        date3.setText(dateConvert3);
        date4.setText(dateConvert4);
        date5.setText(dateConvert5);
        //set weather description labels
        weather1.setText(day1[2]);
        weather2.setText(day2[2]);
        weather3.setText(day3[2]);
        weather4.setText(day4[2]);
        weather5.setText(day5[2]);
    }

    @FXML
    public void switchToMainView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToDrivingConditionsView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DrivingConditionsView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 350);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}