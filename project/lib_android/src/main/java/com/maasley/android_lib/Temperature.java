package com.maasley.android_lib;

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

/**
 * A class who's intention is to retrieve the temperature from a Philips Hue temperature sensor
 */
public class Temperature {

    /**
     * The RESTful URL of the temperature sensor
     */
    String urlString = "";

    /**
     * Constructs a new Temperature object with the given URL
     * @param URL
     */
    public Temperature(String URL) {
        urlString = URL;
    }

    /**
     * @return a JSONObject containing the properties of a sensor
     */
    private JSONObject getSensor() {
        SensorRetriever retriever = new SensorRetriever();
        JSONObject sensor = null;
        try {
            sensor = retriever.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensor;
    }

    /**
     * @return The current temperature reported by the temperature sensor, as a string.
     */
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

    /**
     * @return The date the temperature was last recorded by the temperature sensor, as a string.
     */
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

    /**
     * @param sensor a JSONObject representing a temperature sensor
     * @return a JSONObject containing the state parameters of the sensor
     */
    private JSONObject getSensorState(JSONObject sensor) {
        JSONObject sensorState = null;
        try {
            sensorState = sensor.getJSONObject("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sensorState;
    }


    /**
     * A class that extends AsyncTask, intended for reading HTTP data to retrieve
     * a JSON representation of a temperature sensor from the philips Hue API
     */
    private class SensorRetriever extends AsyncTask<Void, Void, JSONObject> {

        /**
         * @param voids N/A
         * @return a JSONObject representing a temperature sensor
         */
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

        /**
         * @param in an InputStream object for the RESTful URL of the temperature sensor
         * @return a String representation of the temperature sensor
         */
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

        /**
         * Converts a String object to a JSON object
         * @param jsonString a string who's format represents JSON data
         * @return a JSONObject constructed from the jsonString
         */
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