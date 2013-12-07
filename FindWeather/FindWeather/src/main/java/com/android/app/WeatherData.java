package com.android.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class has all the WeatherData information.
 * All the variables have been declared private, thus to set, or access them we need separate functions.
 */
public class WeatherData {
    private Boolean status;
    private String weather;
    private Double temp_c;
    private Double temp_f;
    private String cityNameFull;

    //access and set Status
    public void setStatus(Boolean value) { this.status = value; }
    public Boolean getStatus() { return this.status; }

    //access and set Weather
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getWeather() {
        return this.weather;
    }

    //access and set TempC
    public void setTempC(Double temp_c) {
        this.temp_c = temp_c;
    }
    public Double getTempC() {
        return this.temp_c;
    }

    //access and set TempF
    public void setTempF(Double temp_f) {
        this.temp_f = temp_f;
    }
    public Double getTempF() {
        return this.temp_f;
    }

    //access and set cityName.
    public void setCityName(String cityName) { this.cityNameFull = cityName; }
    public String getCityName() { return this.cityNameFull; }

}