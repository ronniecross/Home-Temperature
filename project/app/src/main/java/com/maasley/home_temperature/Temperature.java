package com.maasley.home_temperature;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class Temperature {
    String urlString = "http://192.168.1.5/api/EthzAaSJ2U85HX5iD3fiQaLhew9xGqyNh8h5RlFa/sensors/11";

    private JSONObject getSensor() {

        SensorRetriever retriever = new SensorRetriever();
        JSONObject sensor = null;
        try {
            sensor = retriever.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sensor;
    }

    public String getTemp() {
        JSONObject sensor = getSensor();
        JSONObject state = getSensorState(sensor);
        String result = "";
        try {
            int tempInt = state.getInt("temperature");
            Double temp = Math.round((tempInt*0.01) * 100d) /100d;
            result = temp + "°C";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getLastUpdate() {
        JSONObject sensor = getSensor();
        JSONObject state = getSensorState(sensor);
        String result = "";
        try {
            String date = state.getString("lastupdated");
            String year = date.substring(0,4);
            String month = date.substring(5,7);
            String day = date.substring(8,10);
            String time = date.substring(11);
            result = "Last updated " + day + "/" + month + "/" + year + " at " + time;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private JSONObject getSensorState(JSONObject sensor) {
        JSONObject sensorState = null;
        try {
            sensorState = sensor.getJSONObject("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sensorState;
    }






    private class SensorRetriever extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject sensor = null;
            String result = "error";
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String jsonString = streamToString(in);
                    sensor = getJsonFromString(jsonString);
                } catch (Exception e) {
                    String s = e.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                String s = e.toString();
            }
            return sensor;
        }

        private String streamToString(InputStream in) {
            String result = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            result = builder.toString();
            return result;
        }

        private JSONObject getJsonFromString(String jsonString) {
            JSONObject json = null;
            try {
                json = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}