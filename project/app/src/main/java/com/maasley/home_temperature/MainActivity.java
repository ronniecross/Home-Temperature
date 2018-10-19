package com.maasley.home_temperature;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setText(getTempString());

        // Enables Always-on
        setAmbientEnabled();
    }

    public void onTap(View view) {
        mTextView.setText(getTempString());
    }

    protected String getTempString() {
        Temperature temperature = new Temperature();
        temperature.getData();
        return "0.00";
    }
}
