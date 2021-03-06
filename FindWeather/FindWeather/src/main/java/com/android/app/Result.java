package com.android.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Result extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/

        Intent intent = getIntent();

        String jString = intent.getStringExtra("jsonString");

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jString);
        } catch (JSONException e) {
            Log.e("JSON Error", "Error getting Json Object" + e.toString());
            return;
        }

        final String DEGREE  = "\u00b0";
        JSONParser jsonParser = new JSONParser(jsonObject);
        WeatherData data = jsonParser.getWeatherData();

        Double tempC = data.getTempC();
        Double tempF = data.getTempF();

        View header = (View)getLayoutInflater().inflate(R.layout.fragment_result, null);

        // Places Temperature in result.
        TextView textView_temp = (TextView) findViewById(R.id.textView_temp);
        textView_temp.setText(tempC.toString() + " " + DEGREE + "C" + " / " + tempF.toString() + " " + DEGREE + "F");

        // Places city name in result.
        TextView textView_cityName = (TextView) findViewById(R.id.textView_cityName);
        textView_cityName.setText(data.getCityName());

        //Places Icon name in the result.
        ImageView imageView = (ImageView) findViewById(R.id.imageView_img);
        new DownloadImageTask(imageView).execute(data.getImageURL());

        // Places Weather Details in result.
        TextView textView_weather = (TextView) findViewById(R.id.textView_Weather);
        textView_weather.setText(data.getWeather());

        //setContentView(R.layout.fragment_result);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_result, container, false);
            return rootView;
        }
    }
    /**
     * This task loads image from
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
