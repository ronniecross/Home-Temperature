package com.maasley.home_temperature;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private TextView dateText;
    private Temperature t = new Temperature();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.temp);
        mTextView.setText(t.getTemp());

        dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(t.getLastUpdate());

        // Enables Always-on
        setAmbientEnabled();
    }
}
