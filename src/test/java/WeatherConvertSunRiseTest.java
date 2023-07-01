/*
@Josh Clemens
Last Edited: 5/24/23
 */

import cs210.javafxproject.Weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherConvertSunRiseTest {

    Weather currentWeather = new Weather();
    @BeforeEach
    public void fillTime(){
        currentWeather.setTime(1684918800L);
        currentWeather.setTimezone(7200);
    }

    @Test
    @DisplayName("WeatherConvertSunRiseTest")
    void ConvertTime(){
        String result = "";
        result = currentWeather.ConvertSunRiseSunSet(currentWeather.getTime());
        System.out.println("DEBUG: ConvertTime result: " + result);
        assertEquals("11:00 AM", result);
    }
}
