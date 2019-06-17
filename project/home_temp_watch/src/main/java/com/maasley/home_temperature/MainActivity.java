package com.maasley.home_temperature;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.maasley.android_lib.Temperature;

public class MainActivity extends WearableActivity {

    private TextView temp;
    private TextView dateText;
    private Temperature t = new Temperature("http://192.168.1.5/api/[REMOVED-REPLACE-WITH-API-KEY]/sensors/11");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = (TextView) findViewById(R.id.temp);
        temp.setText(t.getTemp());

        dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(t.getLastUpdate());

        // Enables Always-on
        setAmbientEnabled();
    }
}
