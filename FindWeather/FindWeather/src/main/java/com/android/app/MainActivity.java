package com.android.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

import java.net.URI;

public class MainActivity extends ActionBarActivity {


    private class AsyncTaskHttpConnExec extends AsyncTask<String,Integer,WeatherData> {

        TextView textView = (TextView) findViewById(R.id.textView_ProcessUpdates);
        @Override
        protected  void onPreExecute()
        {
            textView.setText("Fetching Data...");
            textView.setTextColor(Color.RED);
        }

        @Override
        protected WeatherData doInBackground(String... url) {
            // Creating JSON Parser instance
            JSONParser jParser = new JSONParser();

            // getting JSON string from URL
            WeatherData data = jParser.getDataFromUrl(url[0]);
            String weather = data.getWeather();
            return data;
        }

        @Override
        protected void onPostExecute(WeatherData data) {
            textView.setText("");
            sendData(data);
            return;
        }

    }
    public void sendData(WeatherData data){
        Intent intent = new Intent(getApplicationContext(), Result.class  );
        if(data.getStatus()) {
            intent.putExtra("TempC", data.getTempC());
            intent.putExtra("TempF", data.getTempF());
            intent.putExtra("Weather",data.getWeather());
        }
        //intent.putExtra("This", "Weather");
        startActivity(intent);
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void findWeather(View view) {
        // Creating JSON Parser instance
        if (isNetworkAvailable()) {
            AsyncTaskHttpConnExec runner = new AsyncTaskHttpConnExec();
            runner.execute("http://api.wunderground.com/api/d608ad713879b294/conditions/q/Jaipur.json");
        }
        return;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}