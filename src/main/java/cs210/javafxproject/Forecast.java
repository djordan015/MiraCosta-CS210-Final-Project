package cs210.javafxproject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Forecast {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public double lat, lon;
    public int timeZone;
    public long time;
    public String LIST = "list";
    public String WEATHER = "weather";
    public String MAX = "temp_max";
    public String MIN = "temp_min";
    public String DESCRIPTION = "main";
    private final String[] resultStr = new String[40];

    private static Forecast uniqueInstance;
    public static Forecast getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new Forecast();
        }
        return uniqueInstance;
    }
    public Forecast(Weather weatherObj){
        this.lat = weatherObj.getLat();
        this.lon = weatherObj.getLon();
        this.timeZone = weatherObj.getTimezone();
    }

    //getters
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public int getTimeZone() { return timeZone; }
    public long getTime() { return time; }
    //setters
    public void setLat(double lat) { this.lat = lat; }
    public void setLon(double lon) { this.lon = lon; }
    public void setTimeZone(int timeZone) { this.timeZone = timeZone; }
    public void setTime(long time) { this.time = time; }
    public Forecast(){}

    public void generateForecast(){
        String requestURL = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + "11d4c41f6afa050bb9100272c9455711";
        HttpResponse<String> response = invokeGET(requestURL);
//        System.out.println(response.body());

        JSONObject obj = new JSONObject(response.body());

        double resultMax = 0;
        double resultMin = 0;
        JSONArray weatherArray = obj.getJSONArray(LIST);

        for(int i = 0; i < weatherArray.length(); i++) {

            //get each day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            JSONObject weatherObject = dayForecast.getJSONArray(WEATHER).getJSONObject(0);
            String description = weatherObject.getString(DESCRIPTION);

            JSONObject temperatureObject = dayForecast.getJSONObject(DESCRIPTION);

            this.time = (int) dayForecast.get("dt");

            double high = KelvinToFahrenheit(temperatureObject.getDouble(MAX));
            double low = KelvinToFahrenheit(temperatureObject.getDouble(MIN));

            resultMax = resultMax + high;
            resultMin = resultMin + low;

            this.resultStr[i] = i + " " + time + " "  + description + " " + df.format(high) + " " + df.format(low) + " " + df.format(resultMax)  + " " + df.format(resultMin);
        }
    }

    public String ConvertTime(long time, int timeZone){
        time = time * 1000;
        time = time + timeZone * 1000L;
        Date tempDate = new Date(time);
        //date format = May 24
        SimpleDateFormat dateObj = new SimpleDateFormat("MMM dd");
        dateObj.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateObj.format(tempDate);
    }
    private double KelvinToFahrenheit(double kelvin){
        kelvin = kelvin - 273.15;
        kelvin = kelvin * 1.8;
        kelvin = kelvin + 32;
        return kelvin;
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
                System.out.println("Forecast: Hey it worked!");
            }
        } catch(Exception e) {
            System.out.println("Encountered a problem calling web service");
            System.out.println(e.toString());
        }
        return response;
    }
    public String[] getResultStr() { return resultStr; }
}