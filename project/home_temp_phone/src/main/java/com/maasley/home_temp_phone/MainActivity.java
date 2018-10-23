package com.maasley.home_temp_phone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.maasley.android_lib.Temperature;

public class MainActivity extends AppCompatActivity {

    private TextView temp;
    private TextView dateText;
    private Temperature t = new Temperature("http://192.168.1.5/api/EthzAaSJ2U85HX5iD3fiQaLhew9xGqyNh8h5RlFa/sensors/11");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        dateText = findViewById(R.id.dateText);

        temp.setText(t.getTemp());
        dateText.setText(t.getLastUpdate());
    }
}
