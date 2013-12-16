
package com.android.app;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This Activity handles the Multiple Instances of a City.
 */

public class Option extends ActionBarActivity implements View.OnClickListener {

    //This variable contains cityInformation of all the instances the input Cities with ZMW number.
    CityInformation [] cityInformation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_option);

        LinearLayout ll = (LinearLayout) findViewById(R.id.OptionLinearLayout);

        // Gets the intent from the Main Activity
        Intent intent = getIntent();
        JSONParser jsonParser = null;
        try {
            // in the Intent Json String has been passed this block of code gets the City Information
            // from the Json String
            String temp = intent.getStringExtra("jsonString");
            JSONObject jObj = new JSONObject(temp);
            jsonParser  = new  JSONParser(jObj);
            cityInformation = jsonParser.getCities();

        } catch (JSONException e) {
            Log.e("Intent Error", "Error getting Intent data " + e.toString());
        }
        for (int i = 0 ; i< cityInformation.length; i++) {
            String message = null;
            TextView textView = new TextView(this);
            String temp = cityInformation[i].getCityName();
            if(temp!=null) {
                message = temp;
            }
            temp = cityInformation[i].getState();
            if(temp!="") {
                message += ", " + temp;
            }
            temp = cityInformation[i].getCountry();
            if(temp!="") {
                message += ", " + temp;
            }
            textView.setId(i);
            textView.setText(message);
            textView.setTextSize(20);
            textView.setPadding(15,5,5,5);
            textView.setHeight(100);
            textView.setBackgroundResource(R.drawable.back);
            textView.setOnClickListener(this);
            ll.addView(textView);

        }
    }

    //This function handles OnClick Version for the various instances of the city Name.
    //Each city had different zmw number corresponding to it, Which is required to get the current
    // .. condition.
    @Override
    public void onClick(View view) {
        String url = "http://api.wunderground.com/api/d608ad713879b294/conditions/q/zmw:";
        for (int i = 0 ; i < cityInformation.length; i++) {
            if(view.getId() == i) {
                url+=cityInformation[i].getZmw()+".json";
                break;
            }
        }
        //Transfers the URL to the AsyncTask Runner.
        AsyncTaskHttpConnExec runner= new AsyncTaskHttpConnExec();
        runner.execute(url);
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_option, container, false);
            return rootView;
        }
    }

    private class AsyncTaskHttpConnExec extends AsyncTask<String,Integer,JSONParser> {
        @Override
        protected  void onPreExecute() {

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
            if(jsonParser.getStatus()==1){
                sendData2ResultActivity(jsonParser.getJSONObj().toString());
            } else {
                showError();
            }
            return;
        }
    }

    public void showError() {
        TextView tv = new TextView(this);
        tv.setBackgroundResource(R.drawable.rounded);
        tv.setText("Could not Load Data !!");
        setContentView(tv);
    }

    /**
     * This function receives the data from the AsyncTask runner and sends this to Result Activity
     */

    public void sendData2ResultActivity(String jsonString) {
        Intent intent = new Intent(getApplicationContext(), Result.class );
        intent.putExtra("jsonString", jsonString);
        startActivity(intent);
        return;
    }

}
