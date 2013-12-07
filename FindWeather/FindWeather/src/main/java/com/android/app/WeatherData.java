package com.android.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohit on 12/7/13.
 */
public class WeatherData {
    private Boolean status;
    private String weather;
    private Double temp_c;
    private Double temp_f;
    private String cityNameFull;

    public void setStatus(Boolean value) { this.status = value; }
    public Boolean getStatus() { return this.status; }

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

    public void setCityName(String cityName) { this.cityNameFull = cityName; }
    public String getCityName() { return this.cityNameFull; }

}