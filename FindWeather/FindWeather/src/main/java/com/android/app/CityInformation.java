package com.android.app;

/**
 * This class contains information regarding City.
 * This is needed to transfer the data for Multiple Instances of a City Name.
 */
public class CityInformation {
    private String cityName = null;
    private String state = null;
    private String country = null;
    private String zmw = null;

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName){
        this.cityName  = cityName;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state){
        this.state  = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country){
        this.country  = country;
    }

    public String getZmw() {
        return this.zmw;
    }

    public void setZmw(String zmw){
        this.zmw  = zmw;
    }

}
