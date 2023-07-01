package cs210.javafxproject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.*;

public class Weather {

    public int currentTemp, maxTemp, minTemp, humidity, visibility, timezone;
    public Long sunrise, sunset, time;
    public double lon, lat;
    public String description, country;
    public Boolean valid = false;
    public Boolean set = false;

    public String getConditions(){
        return "temp";
    }

    public void generateWeather(String City) {
        String requestURL = "http://api.openweathermap.org/data/2.5/weather?q=" + City + "&APPID=122f2f224ddde5de85dda26b67656581";
        HttpResponse<String> response = invokeGET(requestURL);
        System.out.println(response.body());

        JSONObject obj = new JSONObject(response.body());
        if(response.statusCode() != 200){
            valid = false;
        }
        else{
            valid = true;
            set = true;
        }

        if(valid) {
            JSONArray JsonWeatherArray = obj.getJSONArray("weather");
            JSONObject tempObj = obj.getJSONObject("main");
            JSONObject tempObjSunRiseSet = obj.getJSONObject("sys"); //access the sunrise and sunset data
            JSONObject tempObjLonLat = obj.getJSONObject("coord"); //access the lon and lat data for forecast
//        System.out.println("Temp: " + tempObj.get("temp"));
//        System.out.println("Printing main array: \n" + obj.get("main") + "\n");
//        System.out.println("Printing weather array: \n" + JsonWeatherArray.toString() + "\n");
            JSONObject descriptionObject = JsonWeatherArray.getJSONObject(0);
            String description = descriptionObject.getString("description");
//            JSONObject timeObject = obj.getJSONObject("dt");
//            System.out.println(timeObject);
//        System.out.println("Description: " + description);

            this.description = description;
            this.currentTemp = KelvinToFahrenheit(tempObj.getDouble("temp"));
            this.maxTemp = KelvinToFahrenheit(tempObj.getDouble("temp_max"));
            this.minTemp = KelvinToFahrenheit(tempObj.getDouble("temp_min"));
            this.humidity = tempObj.getInt("humidity");
            this.visibility = (int) (obj.getDouble("visibility") / 1000);
            this.sunrise = tempObjSunRiseSet.getLong("sunrise");
            this.sunset = tempObjSunRiseSet.getLong("sunset");
            this.country = tempObjSunRiseSet.getString("country");
            this.timezone = obj.getInt("timezone");
            this.lon = tempObjLonLat.getDouble("lon");
            this.lat = tempObjLonLat.getDouble("lat");
            this.time = obj.getLong("dt");
        }
//        System.out.println("Current Temp: " + currentTemp);
//        System.out.println("Max Temp: " + maxTemp);
//        System.out.println("Min Temp: " + minTemp);
//        System.out.println("Hum: " + humidity);
//        System.out.println("vis: " + visibility);
    }

    public String ConvertSunRiseSunSet(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        time = time * 1000;
        time = time + (getTimezone() * 1000L);
        Date date = new Date(time);
        return sdf.format(date);
    }

    public String setDate(){
        SimpleDateFormat dateObj = new SimpleDateFormat("MMM dd\nhh:mm aa");
        dateObj.setTimeZone(TimeZone.getTimeZone("UTC"));

        time = getTime();
        time = time * 1000;
        time = time + (getTimezone() * 1000L);
        Date tempDate = new Date(time);
        String dateNow = dateObj.format(tempDate);
        return dateNow;
    }

    private int KelvinToFahrenheit(double kelvin){
        kelvin = kelvin - 273.15;
        kelvin = kelvin * 1.8;
        kelvin = kelvin + 32;

        int fahrenheit = (int) Math.round(kelvin);
        return fahrenheit;
    }

    public int getCurrentTemp(){ return currentTemp; }
    public int getMaxTemp() { return maxTemp; }
    public int getMinTemp() { return minTemp; }
    public String getDescription() { return description; }
    public int getHumidity() { return humidity; }
    public int getVisibility() { return visibility; }
    public boolean isValid(){ return valid; }
    public Long getSunRise() { return sunrise; }
    public Long getSunSet() { return sunset; }
    public int getTimezone() { return timezone; }
    public double getLon() { return lon; }
    public double getLat() { return lat; }
    public Long getTime() { return time; }
    public String getCountry() {return country;}

    public void setTime(Long time) { this.time = time; }
    public void setTimezone(int timezone) { this.timezone = timezone; }

    public String toString() {
     return "Current Temp: " + currentTemp + "\n" +
             "Min Temp: " + minTemp + "\n" +
             "Max Temp: " + maxTemp + "\n" +
             "Description: " + description + "\n" +
             "Humidity: " + humidity + "\n" +
             "Visibility: " + visibility + "\n";
    }

    public static HttpResponse<String> invokeGET(String requestURL) {

        // Build HttpClient for making web service calls
        HttpClient client = HttpClient.newBuilder()
                // configure the HttpClient so that it will follow any web redirects
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        // Create our web service request object
        HttpRequest request = HttpRequest.newBuilder()
                // set the URL using from the method input parameter
                .uri(URI.create(requestURL))
                // configure the request to call the GET method of the web service
                .GET()
                .build();

        HttpResponse<String> response = null;

        try {
            // Attempt to call the web service
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // If successful, the web service should return a "200 OK" status code
            if(response.statusCode() == 200) {
                System.out.println("Hey it worked!");

            }
        } catch(Exception e) {
            System.out.println("Encountered a problem calling web service");
            System.out.println(e.toString());
        }
        return response;
    }
}