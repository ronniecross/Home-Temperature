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

public class Temperature {
    String urlString = "http://192.168.1.5/api/EthzAaSJ2U85HX5iD3fiQaLhew9xGqyNh8h5RlFa/sensors/11";

    public void getData() {
        GetTemp gt = new GetTemp();
        gt.execute();
    }

    private class GetTemp extends AsyncTask<Void, Void, Double> {
        @Override
        protected Double doInBackground(Void... voids) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    String random = "test";
                } catch (Exception e) {
                    String s = e.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                String s = e.toString();
            }
            return 0.00;
        }
    }
}