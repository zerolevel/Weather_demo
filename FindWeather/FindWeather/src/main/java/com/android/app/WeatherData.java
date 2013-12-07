package com.android.app;

/**
 * Created by mohit on 12/7/13.
 */
public class WeatherData {
    private Double temp_f;
    private Double temp_c;
    private String weather;
    boolean status;

    public void setStatus(boolean value) {
        this.status = value;
    }
    public boolean getStatus() {
        return this.status;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getWeather() {
        return this.weather;
    }

    public void setTempC(Double temp_c) {
        this.temp_c = temp_c;
    }
    public Double getTempC() {
        return this.temp_c;
    }

    public void setTempF(Double temp_f) {
        this.temp_f = temp_f;
    }
    public Double getTempF() {
        return this.temp_f;
    }
}
