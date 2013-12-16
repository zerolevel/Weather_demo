package com.android.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

import java.net.URI;

public class MainActivity extends ActionBarActivity {

    String cityName = null;

    /**
     * This class runs AsyncTask in background.
     * It executes HTTP Connection and parses JSON data received from the wunderGround API in background
     */
    private class AsyncTaskHttpConnExec extends AsyncTask<String,Integer,JSONParser> {

        //This textView handles additional Information on the main activity
        TextView textView_PU = (TextView) findViewById(R.id.textView_ProcessUpdates);
        @Override
        protected  void onPreExecute()
        {
            textView_PU.setBackgroundResource(R.drawable.rounded);
            textView_PU.setText("Loading Information...");
            textView_PU.setTextColor(Color.WHITE);
        }

        @Override
        protected JSONParser doInBackground(String... url) {
            // Creating JSON Parser instance
            HttpConn httpConn = new HttpConn(url[0]);
            JSONParser jsonParser = new JSONParser(httpConn.getJsonObject());
            return jsonParser;

        }

        @Override
        protected void onPostExecute(JSONParser jsonParser) {
            if(jsonParser.getStatus()==0){
                textView_PU.setText("Invalid Data...!!!");
                textView_PU.setTextColor(Color.WHITE);
            } else {
                if(jsonParser.getStatus()==1) {
                    sendData2ResultActivity(jsonParser.getJSONObj().toString());
                } else if(jsonParser.getStatus()==2) {
                    sendData2OptionActivity(jsonParser.getJSONObj().toString());
                }
                textView_PU.setBackgroundColor(Color.TRANSPARENT);
                textView_PU.setText("");
            }
        }
    }

    /**
     * This function receives the data from the AsyncTask runner and sends this to Result Activity
     */

    public void sendData2ResultActivity(String jsonString) {
        Intent intent = new Intent(getApplicationContext(), Result.class  );
        intent.putExtra("jsonString", jsonString);
        startActivity(intent);
        return;
    }

    /**
     * This function receives data from the AsyncTask runner and sends this to option activity
     */

    public void sendData2OptionActivity(String jsonString) {
        Intent intent = new Intent(getApplicationContext(), Option.class  );
        intent.putExtra("jsonString", jsonString);
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
        //Works only when the network is available.
        if (isNetworkAvailable()) {
            EditText editText = (EditText) findViewById(R.id.editText_cityName);
            cityName = editText.getText().toString();
            AsyncTaskHttpConnExec runner = new AsyncTaskHttpConnExec();
            if  (cityName.contains(" ")) {
                String newCityName = cityName.replace(" ", "%20");
                runner.execute("http://api.wunderground.com/api/d608ad713879b294/conditions/q/"+newCityName+".json");
            } else {
                runner.execute("http://api.wunderground.com/api/d608ad713879b294/conditions/q/"+cityName+".json");
            }
        } else {
            // Create the text view
            TextView textView = new TextView(this);
            textView.setTextSize(10);
            textView.setText("Network not Found");
            textView.setBackgroundColor(Color.GRAY);
            textView.setTextColor(Color.WHITE);
            // Set the text view as the activity layout
            setContentView(textView);
        }
        return;

    }
    //This function checks weather the network is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}