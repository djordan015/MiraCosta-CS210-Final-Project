/*
@Josh Clemens
Last Edited: 5/24/23
 */

import cs210.javafxproject.Forecast;
import cs210.javafxproject.Weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ForecastConvertTimeTest {

    Forecast currentForecast = new Forecast();

    @BeforeEach
    public void fillTime(){
        currentForecast.setTimeZone(-25200);
        currentForecast.setTime(1684918800);
    }

    @Test
    @DisplayName("ForecastConvertTimeTest")
    void ConvertTime(){
        String result = "";
        result = currentForecast.ConvertTime(currentForecast.getTime(), currentForecast.getTimeZone());
        System.out.println("DEBUG: ConvertTime result: " + result);
        assertEquals("May 24", result);
    }
}
