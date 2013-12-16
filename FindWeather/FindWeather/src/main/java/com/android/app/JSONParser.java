package com.android.app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * This class is used to parse JSON data from JSONObject and related activities..
 *
 * Currently this has implementation to parse Weather Data from the JSONObject, City Information for
 * Multiple Instances of Cities and Get the Status.
 */
public class JSONParser {

    private JSONObject jObj = null;

    // constructor
    public JSONParser(JSONObject jObj) {
        this.jObj = jObj;
    }

    /**
     * This Function parses the JSONObject to get the Weather Information.
     * @return WeatherData contains all the weather Information parsed from the JSON Object,
     */

    public WeatherData getWeatherData() {

         // return JSON String
        WeatherData data = new WeatherData();
        data.setStatus(true);

        JSONObject currentObservation;
        try {
            currentObservation = this.jObj.getJSONObject("current_observation");
        } catch (JSONException e) {
            Log.e("JSON Error", "Error getting String weather" + e.toString());
            data.setStatus(false);
            return data;
        }

        try {
            //Get the following details.
            //To add more details please refer to the WunderGround Web API doc.
            data.setWeather(currentObservation.getString("weather"));
            data.setTempC(currentObservation.getDouble("temp_c"));
            data.setTempF(currentObservation.getDouble("temp_f"));
            data.setImageURL(currentObservation.getString("icon_url"));
            data.setCityName(currentObservation.getJSONObject("display_location").getString("full"));
        } catch (JSONException e) {
            Log.e("JSON Error", "Error getting String weather" + e.toString());
        }

        return data;

    }

    /**
     * This function returns the status of the JSON Object.
     * @return The status of the JASON Object,
     */

    public Integer getStatus() {
        //"results" occurs when multiple instance of a city is in the API database.
        Integer status = 0;
        if (this.jObj.has("current_observation")) {
            status = 1;
        }else {
            try {
                JSONObject jsonObject = jObj.getJSONObject("response");
                if (jsonObject.has("results")) {
                    status = 2;
                } else status = 0;
            } catch (JSONException e) {
                Log.e("JSON Error", "Error getting key 'response'" + e.toString());
            }
        }
        return status;
    }

    /**
     * This Function returns the JASON object associated with the Parser.
     *
     */
    public JSONObject getJSONObj() {
        return this.jObj;
    }

    /**
     *
     * @return cityInformation associated with the JASON Object.
     */

    public CityInformation [] getCities() {
        CityInformation [] cityInformation = null;
        try {
            JSONArray results = this.jObj.getJSONObject("response").getJSONArray("results");
            cityInformation = new CityInformation[results.length()];
            for (int i=0; i<results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                CityInformation temp = new CityInformation();
                temp.setCityName(jsonObject.getString("city"));
                temp.setState(jsonObject.getString("state"));
                temp.setCountry(jsonObject.getString("country"));
                temp.setZmw(jsonObject.getString("zmw"));
                cityInformation[i] = temp;
            }
        } catch (JSONException e) {
            Log.e("JSON Error", "Error getting Array results" + e.toString());

        }
        return cityInformation;
    }

}